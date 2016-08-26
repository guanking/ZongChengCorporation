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
		 * ���ݽ�����table����Ŀ¼
		 */
		if (this.fillerListener.getContentFiller() != null) {
			this.fillerListener.getContentFiller().refreshContent(
					this.pdfContent);
		}
		try {
			this.pdfContent.addContentDirectory();
			JOptionPane.showMessageDialog(null,
					"��ӳɹ����ļ������\n\t" + newFile.getAbsolutePath(), "�ɹ�",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "������ݴ�������ϸ��������ݣ�ҳ��ȣ��Ƿ���ȷ",
					"������ʾ", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	}

}
