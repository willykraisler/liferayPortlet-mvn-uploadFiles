package co.com.bnpparibas.cardif.reader;

import co.com.bnpparibas.cardif.model.BogotaRow;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderFile {

	public final static int ID_CERT = 0;
	public final static int PROD_ID = 1;
	public final static int INITDATE = 2;
	public final static int FINALDATE = 3;
	public final static int PERID = 4;
	public final static int NAMES = 5;
	
	final static Logger logger = LoggerFactory.getLogger(ReaderFile.class);

	public static List<BogotaRow> readXLSXFile(List<BogotaRow> bogotaRows,
			Integer rejects) throws IOException {

		InputStream ExcelFileToRead = null;
		try{
			 ExcelFileToRead = new FileInputStream("/opt/temporal/liferay_portal/BASEBOGOTA.xlsx");		
		}catch(IOException e){
			logger.error("Error al abrir el archivo o no tiene permisos");
			throw e;
		}
		
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook();
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		BogotaRow br = null;
		Date today = new Date();
		Double d = null;
		int mark = 0; 
		Iterator rows = sheet.rowIterator();
		rows.next();

		while (rows.hasNext()) {
			br = new BogotaRow();
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			try {
				while (cells.hasNext()) {
					
					cell = (XSSFCell) cells.next();

					switch (cell.getColumnIndex()) {

					case ID_CERT:
						mark = ID_CERT; 
						br.setCertNumber(ReaderFile.readAlphanumeric(cell));						
						break;

					case PROD_ID:
						mark = PROD_ID; 
						br.setProdId(ReaderFile.readAlphanumeric(cell));
						break;

					case NAMES:
						mark = NAMES; 
						br.setName(cell.getStringCellValue());
						break;

					case INITDATE:
						mark = INITDATE;
						br.setInitDate(cell.getDateCellValue());
						break;

					case FINALDATE:
						mark = FINALDATE;
						br.setFinalDate(cell.getDateCellValue());
						break;

					case PERID:
						mark = PERID;
						d = cell.getNumericCellValue();
						br.setId(d.intValue());
						break;
					default:
						break;
					}

				}
			} catch (Exception e) {
				String ms = "Error procesando un campo:";
				logger.error(ms + br + "-" + e.getMessage() );
				setDefaultValue(mark,br);
			}
			br.setSaveDate(today);
			bogotaRows.add(br);
		}
		return bogotaRows;

	}

	private static void setDefaultValue(int mark, BogotaRow br) {
		
		switch (mark) {

		case ID_CERT:		
			br.setCertNumber("CAMPO_ERRONEO");						
			break;

		case PROD_ID:
			br.setProdId("CAMPO_ERRONEO");
			break;

		case NAMES:
			br.setName("CAMPO_ERRONEO");
			break;

		case INITDATE:
			br.setInitDate(new Date());
			break;

		case FINALDATE:
			br.setFinalDate(new Date());
			break;

		case PERID:
			br.setId(-1);
			break;
		default:
			break;
		}
		
	}

	public static String readAlphanumeric(XSSFCell cell) {

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			Double d = cell.getNumericCellValue();
			return String.valueOf(d.longValue());

		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().toString();

		default:
			return "";

		}

	}

}
