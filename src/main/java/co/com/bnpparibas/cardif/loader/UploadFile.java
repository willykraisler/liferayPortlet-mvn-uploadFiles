package co.com.bnpparibas.cardif.loader;

import java.io.File;
import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.bnpparibas.cardif.model.BogotaRow;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import co.com.bnpparibas.cardif.example.ProcessExcel;

public class UploadFile extends MVCPortlet {

	private final static int ONE_GB = 1073741824;

	private final static String baseDir = "/opt/temporal/liferay_portal/";

	private final static String fileInputName = "fileupload";

	private final static String ADMIN_USER = "loadfiles@cardif.com.co";
	
	final static Logger logger = LoggerFactory.getLogger(UploadFile.class);

	public void upload(ActionRequest request, ActionResponse response)
			throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(request);

		long sizeInBytes = uploadRequest.getSize(fileInputName);

		if (uploadRequest.getSize(fileInputName) == 0) {
			throw new Exception("Received file is 0 bytes!");
		}

		// Get the uploaded file as a file.
		File uploadedFile = uploadRequest.getFile(fileInputName);

		String sourceFileName = uploadRequest.getFileName(fileInputName);

		// Where should we store this file?
		File folder = null;
		try{
			folder = new File(baseDir);
		}catch(NullPointerException e){
			logger.error("La ruta para almacenar el archivo no existe");
			throw e;
		}
		
		Integer a = new Integer(0);

		// Check minimum 1GB storage space to save new files...

		if (folder.getUsableSpace() < ONE_GB) {
			throw new Exception("Out of disk space!");
		}

		// This is our final file path.
		File filePath = new File(folder.getAbsolutePath() + File.separator
				+ sourceFileName);

		// Move the existing temporary file to new location.
		FileUtils.copyFile(uploadedFile, filePath);

		ProcessExcel process = new ProcessExcel();

		Integer rejects = Integer.valueOf(0);
		process.loadExcel(rejects);
	}

	@Override
	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		ProcessExcel process = new ProcessExcel();
		JSONArray bogJSONArray = JSONFactoryUtil.createJSONArray();
		JSONArray chunkArray = null;

		for (BogotaRow bog : process.consultRows()) {
			chunkArray = JSONFactoryUtil.createJSONArray();
			chunkArray.put(bog.getCertNumber().toString());
			chunkArray.put(bog.getProdId().toString());
			chunkArray.put(bog.getName().toString());
			chunkArray.put(bog.getInitDate().toString());
			chunkArray.put(bog.getFinalDate().toString());
			chunkArray.put(bog.getId().toString());
			bogJSONArray.put(chunkArray);
		}

		JSONObject jContext = JSONFactoryUtil.createJSONObject();		
		try {
			jContext.put("isAdmin", ((User) request.getAttribute(WebKeys.USER))
				.getEmailAddress().equals(ADMIN_USER));	
			jContext.put("table", bogJSONArray);
		}catch(NullPointerException e){
			logger.error("Un usuario no autenticado trató de ingresar al portal");
		}catch(Exception e){
			logger.error("Un usuario no autenticado trató de ingresar al portal");
		}
		
		response.getWriter().write(jContext.toString());

		super.serveResource(request, response);

	}

}
