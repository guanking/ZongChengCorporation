package pdfTools.setting;

import net.sf.json.JSONObject;
import pdfTools.infoSta.Properties;

public class Setter {
	/**
	 * 是否统计可拷贝
	 */
	private boolean needCopy;
	/**
	 * 是否统计乱码数
	 */
	private boolean needBugNum;
	/**
	 * 是否统计公式数
	 */
	private boolean needFormularNum;
	/**
	 * 是否统计图片数
	 */
	private boolean needImageNum;
	/**
	 * 是否统计页数
	 */
	private boolean needPageNum;
	/**
	 * 是否统计有没有乱码
	 */
	private boolean needHaveBug;
	/**
	 * 是否统计注释数
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
