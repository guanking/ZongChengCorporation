package interfaces;

/**
 * Ϊ�Ի����ṩ�ص��Ľӿ�
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
