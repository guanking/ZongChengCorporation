package listerers;
import java.io.File;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class JListSelectListenerImplement implements ListSelectionListener {
	private JTextField textField;
	/**
	 * 是否响应单个文件选择事件
	 */
	private boolean useful=true;
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(!(obj instanceof JList)||!this.useful){
			return;
		}
		JList<File> list=(JList) obj;
		String path=((File)list.getSelectedValue()).getAbsolutePath();
		this.textField.setText(path);
	}
	public boolean isUseful() {
		return useful;
	}
	public void setUseful(boolean useful) {
		this.useful = useful;
	}
}
