package interfaces;

import javax.swing.JComponent;

/**
 * 
 * 选择文件之后的回调接口，根据不同的模式进行处理
 * 
 * @author Administrator
 *
 */
public interface ModeDealer {
	void dealMode(String path, JComponent component);
}
