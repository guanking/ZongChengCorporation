package pdfTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �����ļ���д
 * 
 * @author Administrator
 *
 */
public class FileHelper {
	/**
	 * read context from file specified by path
	 * 
	 * @param path
	 *            file location
	 * @return context of file
	 * @throws Exception
	 */
	public static String readContextFromFile(String path) throws Exception {
		File file = new File(path);
		return readContentFromFile(file);
	}

	/**
	 * read context from file
	 * 
	 * @param file
	 * @return context of file
	 * @throws Exception
	 */
	public static String readContentFromFile(File file) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(file.getAbsolutePath() + "�ļ�������");
		}
		int len = 0;
		char[] buf = new char[1024];
		while ((len = reader.read(buf, 0, 1024)) != -1) {
			sb.append(buf, 0, len);
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * write context to file located by path
	 * 
	 * @param context
	 * @param path
	 * @throws Exception
	 */
	public static void write2File(String context, String path) throws Exception {
		FileHelper.validateFile(path);
		File file = new File(path);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(context);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new Exception("�޷����ļ���" + file.getAbsolutePath()
					+ "������д�룬�볢��ʹ�������ļ���");
		}
	}

	/**
	 * 
	 * @param path
	 *            location of excel
	 * @param cols
	 *            key,name of each column
	 * @param datas
	 *            context
	 * @throws Exception
	 */
	public static void write2Excel(String path, HashMap<String, String> cols,
			JSONArray datas) throws Exception {
		FileHelper.validateFile(path);
		WritableWorkbook workBook = Workbook.createWorkbook(new File(path));
		WritableSheet sheet = workBook.createSheet("Sheet1", 0);
		int colCount = 0;
		int rowCount = 0;
		Label cell = null;
		Set<String> keys = cols.keySet();
		for (String key : keys) {
			cell = new Label(colCount++, rowCount, cols.get(key));
			sheet.addCell(cell);
		}
		rowCount++;
		Iterator<JSONObject> p = datas.iterator();
		JSONObject json = null;
		while (p.hasNext()) {
			json = p.next();
			colCount = 0;
			for (String key : keys) {
				cell = new Label(colCount++, rowCount, json.getString(key));
				sheet.addCell(cell);
			}
			rowCount++;
		}
		workBook.write();
		workBook.close();
	}

	/**
	 * create file located path if doesn't exist
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static void validateFile(String path) throws Exception {
		File file = new File(path);
		try {
			if (file != null || !file.isFile()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("�����ļ���" + file.getAbsolutePath() + "��ʧ��");
		} finally {
			file = null;
		}
	}

//	public static void main(String[] args) throws Exception {
//		HashMap<String, String> cols = new HashMap<String, String>();
//		cols.put("id", "���");
//		cols.put("name", "����");
//		JSONArray datas = new JSONArray();
//		String str = "����Ϳ���������˺ŷ����������������ͻ����������˷��������ͻ�����������ɶ���͸���ɳ³�˺�������";
//		for (int i = 0; i < 30; i++) {
//			JSONObject json = new JSONObject();
//			json.put("id", i);
//			json.put("name", str.charAt(i));
//			datas.add(json);
//		}
//		FileHelper.write2Excel("files/firstExcel.xls", cols, datas);
//		System.out.println("finish");
//	}
}
