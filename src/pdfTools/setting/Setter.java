package pdfTools.setting;

import net.sf.json.JSONObject;
import pdfTools.infoSta.Properties;

public class Setter {
	/**
	 * �Ƿ�ͳ�ƿɿ���
	 */
	private boolean needCopy;
	/**
	 * �Ƿ�ͳ��������
	 */
	private boolean needBugNum;
	/**
	 * �Ƿ�ͳ�ƹ�ʽ��
	 */
	private boolean needFormularNum;
	/**
	 * �Ƿ�ͳ��ͼƬ��
	 */
	private boolean needImageNum;
	/**
	 * �Ƿ�ͳ��ҳ��
	 */
	private boolean needPageNum;
	/**
	 * �Ƿ�ͳ����û������
	 */
	private boolean needHaveBug;
	/**
	 * �Ƿ�ͳ��ע����
	 */
	private boolean needAnnotAionNum;
	private JSONObject json=null;
	public Setter() throws Exception{
		Properties pro=new Properties(Properties.CONFIG_PATH);
		pro.open();
		Object obj=pro.getProperty(Properties.SETTING);
		if(obj==null||obj instanceof JSONObject){
			this.json=new JSONObject();
		}
		this.json=(JSONObject) obj;
	}
}
