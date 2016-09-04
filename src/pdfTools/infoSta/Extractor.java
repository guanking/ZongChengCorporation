package pdfTools.infoSta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pdfTools.FileHelper;
import interfaces.ProgressDealer;
/**
 * extract data to excel file
 * @author Administrator
 *
 */
public class Extractor implements Runnable {
	/**
	 * excelFile location
	 */
	private String path;
	/**
	 * columns
	 */
	private HashMap<String, String> cols;
	/**
	 * context
	 */
	private JSONArray datas;
	private ProgressDealer pro;

	public Extractor(String path, HashMap<String, String> cols,
			JSONArray datas, ProgressDealer pro) {
		super();
		this.path = path;
		this.cols = cols;
		this.datas = datas;
		this.pro = pro;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (this.pro != null) {
			this.pro.reset();
			this.sleep(10);
		}
		try {
			FileHelper.validateFile(this.path);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "权限错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		WritableWorkbook workBook = null;
		try {
			workBook = Workbook.createWorkbook(new File(path));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "文件写入异常", "读写错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		WritableSheet sheet = workBook.createSheet("Sheet1", 0);
		int colCount = 0;
		int rowCount = 0;
		Label cell = null;
		if (this.pro != null) {
			this.pro.setMaxValue(this.datas.size() + 1);
		}
		Set<String> keys = cols.keySet();
		for (String key : keys) {
			cell = new Label(colCount++, rowCount, cols.get(key));
			try {
				sheet.addCell(cell);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		rowCount++;
		if (this.pro != null) {
			this.pro.setValue(rowCount);
			this.sleep(10);
		}
		Iterator<JSONObject> p = datas.iterator();
		JSONObject json = null;
		while (p.hasNext()) {
			json = p.next();
			colCount = 0;
			for (String key : keys) {
				cell = new Label(colCount++, rowCount, json.getString(key));
				try {
					sheet.addCell(cell);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
			rowCount++;
			if (this.pro != null) {
				this.pro.setValue(rowCount);
				this.sleep(10);
			}
		}
		try {
			workBook.write();
			workBook.close();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.pro != null) {
			this.pro.finish();
			this.pro.callback(ProgressDealer.EXTRACT_INFORMATION,"Excel文件提取成功，保存在：\n"+new File(this.path).getAbsoluteFile());
			this.sleep(10);
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time < 0 ? time : 20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
