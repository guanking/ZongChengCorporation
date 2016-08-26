package pdfTools.infoSta;

import interfaces.ProgressDealer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;

public class ExtractToppage implements Runnable {
	/**
	 * ����ڵ�ǰĿ¼�Ե�����ļ�����
	 */
	public static String OUTPUT_DIR = "toppages";
	/**
	 * pdf�ļ����ڵ�Ŀ¼
	 */
	private String dir;
	/**
	 * ���Ȼص�
	 */
	private ProgressDealer pro;
	private File[] files;

	public ExtractToppage(String dir, ProgressDealer pro) {
		super();
		this.dir = dir;
		this.pro = pro;
	}

	private void getFiles() throws Exception {
		File file = new File(this.dir);
		if (!file.exists() || !file.isDirectory()) {
			throw new Exception("���ļ��С�" + this.dir + "��������");
		}
		this.files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".pdf");
			}
		});
		if (this.files.length == 0) {
			throw new Exception("���ļ��С�" + this.dir + "����û��pdf�ļ�");
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (this.pro != null) {
			this.pro.reset();
		}
		try {
			this.getFiles();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		int process = 1;
		if (this.pro != null) {
			this.pro.setMaxValue(this.files.length + 1);
			this.pro.setValue(process);
		}
		File file = new File(this.dir, ExtractToppage.OUTPUT_DIR);
		if (file == null || !file.isDirectory()) {
			file.mkdirs();
		}
		PDDocument doc, outDoc;
		for (File ele : this.files) {
			process++;
			try {
				doc = PDDocument.load(ele);

				outDoc = new PDDocument();
				if (doc.getNumberOfPages() >= 1) {
					outDoc.addPage(doc.getPage(0));
					outDoc.save(new File(file, ele.getName().replaceFirst(
							"\\.pdf", "����.pdf")));
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (JOptionPane.showConfirmDialog(null, "�ļ���ȡʧ�ܣ��Ƿ��������ļ�������һ����",
						"����", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
					continue;
				} else {
					return;
				}
			}
			if (this.pro != null) {
				this.pro.setValue(process);
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showConfirmDialog(null,
				"��ȡ�ɹ����ļ������ڡ�" + file.getAbsolutePath() + "���ļ�����", "�ɹ�",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
}