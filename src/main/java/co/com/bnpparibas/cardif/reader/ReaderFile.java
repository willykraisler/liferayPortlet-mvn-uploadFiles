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

public class ReaderFile {

	public final static int ID_CERT = 0;
	public final static int PROD_ID = 1;
	public final static int NAMES = 2;
	public final static int INITDATE = 3;
	public final static int FINALDATE = 4;
	public final static int PERID = 5;

	public static List<BogotaRow> readXLSXFile(List<BogotaRow> bogotaRows,
			Integer rejects) throws IOException {

		InputStream ExcelFileToRead = new FileInputStream(
				"/tmp/uploaded/Test.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook();
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		BogotaRow br = null;
		Date today = new Date();
		Double d = null;
		Iterator rows = sheet.rowIterator();
		rows.next();

		while (rows.hasNext()) {
			br = new BogotaRow();
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();

				switch (cell.getColumnIndex()) {

				case ID_CERT:
					br.setCertNumber(ReaderFile.readAlphanumeric(cell));
					break;

				case PROD_ID:
					br.setProdId(ReaderFile.readAlphanumeric(cell));
					break;

				case NAMES:
					br.setName(cell.getStringCellValue());
					break;

				case INITDATE:
					br.setInitDate(cell.getDateCellValue());
					break;

				case FINALDATE:
					br.setFinalDate(cell.getDateCellValue());
					break;

				case PERID:
					d = cell.getNumericCellValue();
					br.setId(d.intValue());
					break;
				default:
					break;
				}

			}
			br.setSaveDate(today);
			bogotaRows.add(br);
		}
		return bogotaRows;

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
