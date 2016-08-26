package interfaces;

/**
 * 为对话框提供回调的接口
 * 
 * @author Administrator
 *
 */
public interface DialogDealer {
	void onSuccess(String type, String fileName);

	void onCalcle();

	void onExit();
}
