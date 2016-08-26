package listerers;

import interfaces.TableDealer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import pdfTools.ContentItem;
import pdfTools.PdfContent;
import pdfTools.Test;
import views.PdfContentMem;

public class ContentFiller implements ActionListener {
	private JTextField tFilePath, tSep, tDelta, tBegin, tEnd;
	private PdfContent pdfContent;
	private JProgressBar bar;
	private TableDealer contentFiller;

	public ContentFiller(JTextField tFilePath, JTextField tSep,
			JTextField tDelta, JTextField tBegin, JTextField tEnd) {
		super();
		this.tFilePath = tFilePath;
		this.tSep = tSep;
		this.tDelta = tDelta;
		this.tBegin = tBegin;
		this.tEnd = tEnd;
	}

	public PdfContent getPdfContent() {
		return pdfContent;
	}

	public void setPdfContent(PdfContent pdfContent) {
		this.pdfContent = pdfContent;
	}

	public JProgressBar getBar() {
		return bar;
	}

	public void setBar(JProgressBar bar) {
		this.bar = bar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			String filePath = this.tFilePath.getText().trim();
			int begin = Integer.parseInt(this.tBegin.getText().trim());
			int end = Integer.parseInt(this.tEnd.getText().trim());
			this.pdfContent = new PdfContent(filePath, begin, end);
			this.pdfContent.setDelta(Integer.parseInt(this.tDelta.getText()
					.trim()));
			PdfContentMem.sep = this.tSep.getText().trim();
			this.pdfContent.setSep(PdfContentMem.sep);
			this.pdfContent.extrat();
			System.out.println(this.pdfContent);
			if (this.contentFiller != null) {
				this.contentFiller.fillTable(this.pdfContent.getItems());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this.tBegin, "请检查各属性值得设置\n" + "详情：\n"
					+ e1.getMessage(), "属性错误", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(this.tBegin, "生成目录完成", "标题",
				JOptionPane.WARNING_MESSAGE);
	}

	public TableDealer getContentFiller() {
		return contentFiller;
	}

	public void setContentFiller(TableDealer contentFiller) {
		this.contentFiller = contentFiller;
	}
}
