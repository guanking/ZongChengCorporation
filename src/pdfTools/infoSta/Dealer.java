package pdfTools.infoSta;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JOptionPane;

import pdfTools.FileHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import interfaces.ProgressDealer;

public class Dealer implements Runnable {
	public static final String NMAE = "name";
	public static final String COPY = "copy";
	public static final String BUG_NUM = "bug_num";
	public static final String FORMULAR_NUM = "formular_num";
	public static final String IMAGE_NUM = "image_num";
	public static final String PAGE_NUM = "page_num";
	public static final String NOTE = "note";
	private ProgressDealer pro;
	private File files[];
	private JSONArray items;
	/**
	 * ��ʽ��
	 */
	private String[] formulars;
	/**
	 * �����
	 */
	private String[] bugCodes;

	/**
	 * ���ù�ʽ��
	 * 
	 * @param path
	 */
	public void setFormulars(String path) {
		try {
			String str = FileHelper.readContextFromFile(path);
			this.formulars = str.split("\n");
			int len = this.formulars.length;
			for (int i = 0; i < len; i++) {
				this.formulars[i] = TextGetter.scaling(this.formulars[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�ļ�����" + path + "�����ڻ����Ѿ��𻵣�",
					"��ʽ����", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * ���������
	 * 
	 * @param path
	 */
	public void setBugs(String path) {
		try {
			String str = FileHelper.readContextFromFile(path);
			this.bugCodes = str.split("\n");
			int len = this.bugCodes.length;
			for (int i = 0; i < len; i++) {
				this.bugCodes[i] = TextGetter.scaling(this.bugCodes[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�ļ�����" + path + "�����ڻ����Ѿ��𻵣�",
					"��ʽ����", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Dealer(String path) {
		super();
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			JOptionPane.showMessageDialog(null, " ����ȷѡ���ļ���", "��ʽ����",
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
			JOptionPane.showMessageDialog(null, "���ļ�����û��pdf�ļ�", "��ʽ����",
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
		this.pro.setMaxValue(this.files.length+1);
		TextGetter text = null;
		ImageCounter imageCounter = null;
		Detecter detect = null;
		this.pro.setValue(1);
		for (int i = 0; i < this.files.length; i++) {
			JSONObject json = new JSONObject();
			if (i == 0) {
				try {
					text = new TextGetter(files[i].getAbsolutePath());
					imageCounter = new ImageCounter(files[i].getAbsolutePath());
					detect = new Detecter(this.bugCodes);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				text.setPath(files[i].getAbsolutePath());
				imageCounter.setPath(files[i].getAbsolutePath());
				detect.setItems(this.bugCodes);
			}
			json.put(Dealer.NMAE, files[i].getName());// pdf�ļ���
			try {
				json.put(Dealer.COPY, text.getText().length() == 0 ? 0 : 1);// �Ƿ�ɿ���
				detect.setContext(text.getText());
				detect.setItems(this.bugCodes);
				json.put(Dealer.BUG_NUM, detect.getCount());// ������
				detect.setItems(this.formulars);
				json.put(Dealer.FORMULAR_NUM, detect.getCount());// ��ʽ��
			} catch (Exception e2) {
				this.addNote(json, "�Ƿ�ɿ�������;����������;��ʽ������");
				System.out.println("come here");
				JOptionPane.showMessageDialog(null, e2.getMessage(), "����",
						JOptionPane.ERROR_MESSAGE);
				e2.printStackTrace();
			}
			try {
				int imageCount = imageCounter.getCount();
				json.put(Dealer.IMAGE_NUM, imageCount);// ͼƬ��
			} catch (IOException e1) {
				this.addNote(json, "ͼƬ������");
				e1.printStackTrace();
			}
			int numPage = text.getPageNumber();
			json.put(Dealer.PAGE_NUM, numPage);// pdfҳ��
			if(!json.containsKey(Dealer.NOTE)){
				json.put(Dealer.NOTE, "�ɹ�");
			}
			this.items.add(json);// �¼�һ����¼
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
//		Dealer dealer = new Dealer("files");
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
