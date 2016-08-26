package renders;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import pdfTools.ContentItem;
import views.PdfContentMem;

public class ContentItemRender extends JPanel implements
		ListCellRenderer<Object> {
	private JTextField text, num;
	private JLabel spe;

	public ContentItemRender() {
		this.text = new JTextField();
		this.text.setHorizontalAlignment(JTextField.LEFT);
		this.num = new JTextField(3);
		this.num.setHorizontalAlignment(JTextField.CENTER);
		this.text.setName("content");
		this.num.setName("num");
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(this.text);
		this.add(this.spe = (new JLabel("бн бн бн бн")));
		this.add(this.num);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		ContentItem item = (ContentItem) value;
		this.text.setText(item.getContent());
		this.num.setText(item.getPageNumber() + "");
		if (isSelected) {
			this.text.setForeground(Color.BLUE);
			this.spe.setForeground(Color.BLUE);
			this.num.setForeground(Color.BLUE);
		} else {
			this.text.setForeground(Color.BLACK);
			this.spe.setForeground(Color.BLACK);
			this.num.setForeground(Color.BLACK);
		}
		return this;
	}

}
