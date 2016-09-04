package pdfTools.infoSta;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import pdfTools.FileHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import interfaces.ProgressDealer;

public class Dealer implements Runnable {
	/**
	 * pdf文件名字
	 */
	public static final String NMAE = "name";
	/**
	 * 复制
	 */
	public static final String COPY = "copy";
	/**
	 * 乱码数
	 */
	public static final String BUG_NUM = "bug_num";
	/**
	 * 公式数
	 */
	public static final String FORMULAR_NUM = "formular_num";
	/**
	 * 图片数
	 */
	public static final String IMAGE_NUM = "image_num";
	/**
	 * pdf文件页数
	 */
	public static final String PAGE_NUM = "page_num";
	/**
	 * 是否有乱码
	 */
	public static final String HAVA_BUG = "have_bug";
	/**
	 * 注释数
	 */
	public static final String ANNOTAION_NUM = "annotAion_num";
	/**
	 * 备注
	 */
	public static final String NOTE = "note";
	private LinkedList<String> staItems;
	private ProgressDealer pro;
	private File files[];
	private JSONArray items;
	/**
	 * 公式库
	 */
	private String[] formulars;
	/**
	 * 乱码库
	 */
	private String[] bugCodes;
	/**
	 * annotation library
	 */
	private String[] annotations;

	public static HashMap<String, String> getProperityMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Dealer.NMAE, "文件名称");
		map.put(Dealer.PAGE_NUM, "页数");
		map.put(Dealer.IMAGE_NUM, "图片数");
		map.put(Dealer.ANNOTAION_NUM, "注释数");
		map.put(Dealer.BUG_NUM, "乱码数");
		map.put(Dealer.FORMULAR_NUM, "公式数");
		map.put(Dealer.HAVA_BUG, "是否有乱码");
		map.put(Dealer.COPY, "是否可拷贝");
		map.put(Dealer.NOTE, "备注");
		return map;
	}

	/**
	 * 设置公式库
	 * 
	 * @param path
	 */

	public void setFormulars(String path) {
		try {
			String str = FileHelper.readContextFromFile(path);
			this.formulars = str.split("\n");
			// int len = this.formulars.length;
			// for (int i = 0; i < len; i++) {
			// this.formulars[i] = TextGetter.scaling(this.formulars[i]);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "文件：“" + path + "”存在或者已经损坏！",
					"格式错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	public LinkedList<String> getStaItems() {
		return staItems;
	}

	public void setStaItems(LinkedList<String> staItems) {
		this.staItems = staItems;
	}

	/**
	 * 设置乱码库
	 * 
	 * @param path
	 */
	public void setBugs(String path) {
		try {
			String str = FileHelper.readContextFromFile(path);
			this.bugCodes = str.split("\n");
			// int len = this.bugCodes.length;
			// for (int i = 0; i < len; i++) {
			// this.bugCodes[i] = TextGetter.scaling(this.bugCodes[i]);
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "文件：“" + path + "”存在或者已经损坏！",
					"格式错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 设置注释库
	 * 
	 * @param path
	 */
	public void setAnnotations(String path) {
		try {
			String str = FileHelper.readContextFromFile(path);
			this.annotations = str.split("\n");
			// int len = this.annotations.length;
			// for (int i = 0; i < len; i++) {
			// this.annotations[i] = TextGetter.scaling(this.bugCodes[i]);
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "文件：“" + path + "”存在或者已经损坏！",
					"格式错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Dealer(String path) {
		super();
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			JOptionPane.showMessageDialog(null, " 请正确选择文件夹", "格式错误",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.files = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".pdf");
			}
		});
		if (this.files.length == 0) {
			JOptionPane.showMessageDialog(null, "该文件夹下没有pdf文件", "格式错误",
					JOptionPane.ERROR_MESSAGE);
		}
		this.items = new JSONArray();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (this.pro != null) {
			this.pro.reset();
		}
		this.pro.setMaxValue(this.files.length + 1);
		TextGetter text = null;
		ImageCounter imageCounter = null;
		Detecter detect = null;
		this.pro.setValue(1);
		for (int i = 0; i < this.files.length; i++) {
			JSONObject json = new JSONObject();
			if (i == 0) {
				try {
					if (this.staItems.contains(Dealer.COPY)
							|| this.staItems.contains(Dealer.ANNOTAION_NUM)
							|| this.staItems.contains(Dealer.BUG_NUM)
							|| this.staItems.contains(Dealer.HAVA_BUG)
							|| this.staItems.contains(Dealer.FORMULAR_NUM)) {
						text = new TextGetter(files[i].getAbsolutePath());
					}
					if (this.staItems.contains(Dealer.IMAGE_NUM)) {
						imageCounter = new ImageCounter(
								files[i].getAbsolutePath());
					}
					if (this.staItems.contains(Dealer.ANNOTAION_NUM)
							|| this.staItems.contains(Dealer.BUG_NUM)
							|| this.staItems.contains(Dealer.FORMULAR_NUM)
							|| this.staItems.contains(Dealer.HAVA_BUG)) {
						detect = new Detecter(this.bugCodes);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (text != null) {
					text.setPath(files[i].getAbsolutePath());
				}
				if (imageCounter != null) {
					imageCounter.setPath(files[i].getAbsolutePath());
				}
				if (detect != null) {
					detect.setItems(this.bugCodes);
				}
			}
			json.put(Dealer.NMAE, files[i].getName()); // pdf文件名
			try {
				if (this.items.contains(Dealer.COPY)) {
					json.put(Dealer.COPY, text.getText().length() == 0 ? "否"
							: "是"); // 是否可拷贝
				}
				if (detect != null) {
					detect.setContext(text.getText());
				}
				if (this.staItems.contains(Dealer.BUG_NUM)) {
					detect.setItems(this.bugCodes);
					json.put(Dealer.BUG_NUM, detect.getCount()); // 乱码数
				}
				if (this.staItems.contains(Dealer.HAVA_BUG)) {
					detect.setItems(this.bugCodes);
					json.put(Dealer.HAVA_BUG, detect.getCount() == 0 ? "是"
							: "否"); // 是否有乱码
				}
				if (this.staItems.contains(Dealer.FORMULAR_NUM)) {
					detect.setItems(this.formulars);
					json.put(Dealer.FORMULAR_NUM, detect.getCount()); // 公式数
				}
				if (this.staItems.contains(Dealer.ANNOTAION_NUM)) {
					detect.setItems(this.annotations);
					json.put(Dealer.ANNOTAION_NUM, detect.getCount()); // 注释数
				}
			} catch (Exception e2) {
				this.addNote(json, "是否可拷贝出错;乱码数出错;公式数出错");
				System.out.println("come here");
				JOptionPane.showMessageDialog(null, e2.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
			try {
				if (imageCounter != null) {
					int imageCount = imageCounter.getCount();
					json.put(Dealer.IMAGE_NUM, imageCount); // 图片数
				}
			} catch (IOException e1) {
				this.addNote(json, "图片数出错");
				e1.printStackTrace();
			}
			if (this.staItems.contains(Dealer.PAGE_NUM)) {
				int numPage = text.getPageNumber();
				json.put(Dealer.PAGE_NUM, numPage);// pdf页数
			}
			if (!json.containsKey(Dealer.NOTE)) {
				json.put(Dealer.NOTE, "成功");
			}
			this.items.add(json);// 新加一条记录
			if (this.pro != null)
				this.pro.setValue(i + 2);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (this.pro != null) {
			this.pro.finish();
			this.pro.callback(ProgressDealer.INFORMATION, this.items);
		}
	}

	public void setPro(ProgressDealer pro) {
		this.pro = pro;
	}

	private void addNote(JSONObject json, String msg) {
		try {
			msg += ";" + json.getString(Dealer.NOTE);
		} catch (Exception e) {
			json.put(Dealer.NOTE, msg);
		}
	}

	public static void main(String[] args) {
		Dealer dealer = new Dealer(
				"F:\\play\\projectTemp\\ZongChengEleCor\\test");
		// Dealer dealer = new Dealer("files");
		dealer.setPro(new ProgressDealer() {

			@Override
			public void setValue(int value) {
				// TODO Auto-generated method stub
				System.out.println("setValue: " + value);
			}

			@Override
			public void setMaxValue(int value) {
				// TODO Auto-generated method stub
				System.out.println("maxValue: " + value);
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				System.out.println("reset");
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				System.out.println("finish");
			}

			@Override
			public void callback(String type, Object result) {
				// TODO Auto-generated method stub
				System.out.println("type:" + type + ",result:" + result);
			}
		});
		dealer.setFormulars("formulars/Apache.txt");
		dealer.setBugs("randoms/Apache.txt");
		dealer.run();
	}
}
