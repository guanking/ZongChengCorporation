package pdfTools.infoSta;

import interfaces.ProgressDealer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ExtractToppage implements Runnable {
	/**
	 * 存放在当前目录显得这个文件夹内
	 */
	public static String OUTPUT_DIR = "toppages";
	/**
	 * pdf文件所在的目录
	 */
	private String dir;
	/**
	 * 进度回调
	 */
	private ProgressDealer pro;
	private File[] files;
	/**
	 * 图片类型[png,jpg,jpeg]
	 */
	private String type = "png";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ExtractToppage(String dir, ProgressDealer pro) {
		super();
		this.dir = dir;
		this.pro = pro;
	}

	private void getFiles() throws Exception {
		File file = new File(this.dir);
		if (!file.exists() || !file.isDirectory()) {
			throw new Exception("该文件夹“" + this.dir + "”不存在");
		}
		this.files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".pdf");
			}
		});
		if (this.files.length == 0) {
			throw new Exception("该文件夹“" + this.dir + "”下没有pdf文件");
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
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
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
		PDDocument doc;
		for (File ele : this.files) {
			process++;
			try {
				doc = PDDocument.load(ele);
				if (doc.getNumberOfPages() >= 1) {
					PDFRenderer render = new PDFRenderer(doc);
					BufferedImage image = render.renderImage(0);
					ImageIO.write(image, this.type, new File(file, ele
							.getName().replaceFirst("\\.pdf", "." + this.type)));
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (JOptionPane.showConfirmDialog(null, "文件读取失败，是否跳过该文件进行下一个？",
						"警告", JOptionPane.OK_CANCEL_OPTION,
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
				"提取成功，文件保存在“" + file.getAbsolutePath() + "”文件夹中", "成功",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
}
