package interfaces;

/**
 * 为对话框提供回调的接口
 * 
 * @author Administrator
 *
 */
public interface DialogDealer {
	public static final String SETTING="setting";
	void onSuccess(String type, String fileName);

	void onCalcle();

	void onExit();
}
