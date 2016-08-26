package views;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pdfTools.ContentItem;

public class PdfContentMem {
	public static String sep = ".......";
	private ContentItem content;
	private JTextField text, num;

	public PdfContentMem() {
		// TODO Auto-generated constructor stub
	}

	public ContentItem getContent() {
		return content;
	}

	public static String getSep() {
		return sep;
	}

	public static void setSep(String sep) {
		PdfContentMem.sep = sep;
	}

	public JTextField getText() {
		return text;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	public JTextField getNum() {
		return num;
	}

	public void setNum(JTextField num) {
		this.num = num;
	}

	public void setContent(ContentItem content) {
		this.content = content;
		this.text = new JTextField(this.content.getContent());
		this.text.setHorizontalAlignment(JTextField.LEFT);
		this.num = new JTextField(this.content.getPageNumber() + "", 3);
		this.num.setHorizontalAlignment(JTextField.CENTER);
		this.text.getDocument()
				.addDocumentListener(new OwnDocumentEvent("num"));
		this.text.getDocument().addDocumentListener(
				new OwnDocumentEvent("text")); 	
		this.text.setName("content");
		this.num.setName("num");
		
	}

	private class OwnDocumentEvent implements DocumentListener {
		private String name;

		private void deal() {
			if (name.equals("num")) {
				try {
					int num = Integer.parseInt(PdfContentMem.this.num
							.getText().trim());
					PdfContentMem.this.content.setAgeNumber(num);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} else {
				PdfContentMem.this.content
						.setContent(PdfContentMem.this.text.getText().trim());
				;
			}
		}

		public OwnDocumentEvent(String name) {
			// TODO Auto-generated constructor stub
			this.name=name;
			this.deal();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			this.deal();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			this.deal();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
