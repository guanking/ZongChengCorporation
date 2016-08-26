package renders;
import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class ContentItemListRender extends JButton implements
		ListCellRenderer<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, // the list
			Object value, // value to display
			int index, // cell index
			boolean isSelected, // is the cell selected
			boolean cellHasFocus) // does the cell have focus
	{
		File file = (File) value;
		setText(file.getName());
		setBackground(list.getBackground());
		if (isSelected) {
			setForeground(Color.BLUE);
		} else {
			setForeground(Color.GRAY);
		}
		this.setHorizontalAlignment(SwingConstants.LEFT);
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}
}
