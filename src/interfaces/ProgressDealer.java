package interfaces;
/**
 * �������Ĵ���ӿ�
 * @author Administrator
 *
 */
public interface ProgressDealer {
	/**
	 * information statistic
	 */
	String INFORMATION="information";
	/**
	 * extract information of pdfFiles
	 */
	String EXTRACT_INFORMATION="extract_information";
	void reset();

	void setValue(int value);

	void finish();

	void setMaxValue(int value);

	void callback(String type, Object result);
}
