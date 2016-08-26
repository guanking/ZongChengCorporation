package listerers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pdfTools.PdfContent;

public class ContentGenerater implements ActionListener {
	ContentFiller fillerListener;
	private JTextField delta;
	private PdfContent pdfContent;

	public ContentGenerater() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContentGenerater(ContentFiller fillerListener) {
		// TODO Auto-generated constructor stub
		this.fillerListener = fillerListener;
	}

	public JTextField getDelta() {
		return delta;
	}

	public void setDelta(JTextField delta) {
		this.delta = delta;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			this.pdfContent = this.fillerListener.getPdfContent();
			int d = Integer.parseInt(this.delta.getText().trim());
			this.pdfContent.setDelta(d);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		File newFile = null;
		try {
			File file = new File(this.pdfContent.getPath());
			newFile = new File(file.getParent(), "PDFContentAdder");
			newFile.mkdirs();
			newFile = new File(newFile, file.getName());
			if (!newFile.exists() || newFile.isDirectory()) {
				newFile.createNewFile();
			}
			this.pdfContent.setDes(newFile.getAbsolutePath());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/**
		 * 根据界面中table更新目录
		 */
		if (this.fillerListener.getContentFiller() != null) {
			this.fillerListener.getContentFiller().refreshContent(
					this.pdfContent);
		}
		try {
			this.pdfContent.addContentDirectory();
			JOptionPane.showMessageDialog(null,
					"添加成功，文件请见：\n\t" + newFile.getAbsolutePath(), "成功",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "表格内容错误，请仔细检查表格内容（页码等）是否正确",
					"错误提示", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

}
