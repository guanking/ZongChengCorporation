package pdfTools.setting;

import java.util.LinkedList;

import net.sf.json.JSONObject;
import pdfTools.infoSta.Dealer;
import pdfTools.infoSta.Properties;

/**
 * The method finish() must be called after using
 * 
 * @author Administrator
 *
 */
public class Setter {
	/**
	 * legal width of image when statistic
	 */
	public static final String WIDTH = "width";
	/**
	 * legal height of image when statistic
	 */
	public static final String HEIGHT = "height";
	/**
	 * image type for extracting the first page
	 */
	public static final String IMG_TYPE = "img_type";
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
	private boolean needAnnotaionNum;
	private JSONObject json = null;

	public Setter() throws Exception {
		Properties pro = new Properties(Properties.CONFIG_PATH);
		pro.open();
		Object obj = pro.getProperty(Properties.SETTING);
		pro.close();
		if (obj == null || obj instanceof JSONObject) {
			this.createSetting();
		} else {
			this.json = JSONObject.fromObject(obj);
		}
		this.initParams();
	}

	/**
	 * initiate the parameters with the value extracted from jsonObject
	 */
	private void initParams() {
		// TODO Auto-generated method stub
		this.needPageNum = this.json.getBoolean(Dealer.PAGE_NUM);
		this.needAnnotaionNum = this.json.getBoolean(Dealer.ANNOTAION_NUM);
		this.needCopy = this.json.getBoolean(Dealer.COPY);
		this.needFormularNum = this.json.getBoolean(Dealer.FORMULAR_NUM);
		this.needHaveBug = this.json.getBoolean(Dealer.HAVA_BUG);
		this.needImageNum = this.json.getBoolean(Dealer.IMAGE_NUM);
		this.needBugNum = this.json.getBoolean(Dealer.BUG_NUM);
	}

	/**
	 * initiate the setting option at first time
	 */
	private void createSetting() {
		// TODO Auto-generated method stub
		if (this.json == null) {
			this.json = new JSONObject();
		}
		this.json.put(Dealer.PAGE_NUM, true);
		this.json.put(Dealer.ANNOTAION_NUM, true);
		this.json.put(Dealer.COPY, true);
		this.json.put(Dealer.FORMULAR_NUM, true);
		this.json.put(Dealer.HAVA_BUG, true);
		this.json.put(Dealer.IMAGE_NUM, true);
		this.json.put(Dealer.BUG_NUM, true);
		this.json.put(Setter.HEIGHT, 0);
		this.json.put(Setter.WIDTH, 0);
		this.json.put(Setter.IMG_TYPE, "JPEG");
	}

	public boolean isNeedCopy() {
		return needCopy;
	}

	public void setNeedCopy(boolean needCopy) {
		this.needCopy = needCopy;
	}

	public boolean isNeedBugNum() {
		return needBugNum;
	}

	public void setNeedBugNum(boolean needBugNum) {
		this.needBugNum = needBugNum;
	}

	public boolean isNeedFormularNum() {
		return needFormularNum;
	}

	public void setNeedFormularNum(boolean needFormularNum) {
		this.needFormularNum = needFormularNum;
	}

	public boolean isNeedImageNum() {
		return needImageNum;
	}

	public void setNeedImageNum(boolean needImageNum) {
		this.needImageNum = needImageNum;
	}

	public boolean isNeedPageNum() {
		return needPageNum;
	}

	public void setNeedPageNum(boolean needPageNum) {
		this.needPageNum = needPageNum;
	}

	public boolean isNeedHaveBug() {
		return needHaveBug;
	}

	public void setNeedHaveBug(boolean needHaveBug) {
		this.needHaveBug = needHaveBug;
	}

	public boolean isNeedAnnotaionNum() {
		return needAnnotaionNum;
	}

	public void setNeedAnnotaionNum(boolean needAnnotAionNum) {
		this.needAnnotaionNum = needAnnotAionNum;
	}

	public int getLegalHeight() {
		return this.json.getInt(Setter.HEIGHT);
	}

	public int getLegalWidth() {
		return this.json.getInt(Setter.WIDTH);
	}

	public void setLegalHeight(int height) {
		this.json.put(Setter.HEIGHT, height);
	}

	public void setLegalWidth(int width) {
		this.json.put(Setter.WIDTH, width);
	}

	public void setImageType(String type) {
		this.json.put(Setter.IMG_TYPE, type.toUpperCase());
	}

	public String getImageType() {
		return this.json.getString(Setter.IMG_TYPE);
	}

	/**
	 * rewrite the setting configure to configFile
	 * 
	 * @throws Exception
	 */
	public void finish() throws Exception {
		this.json.put(Dealer.PAGE_NUM, this.needPageNum);
		this.json.put(Dealer.ANNOTAION_NUM, this.needAnnotaionNum);
		this.json.put(Dealer.COPY, this.needCopy);
		this.json.put(Dealer.FORMULAR_NUM, this.needFormularNum);
		this.json.put(Dealer.HAVA_BUG, this.needHaveBug);
		this.json.put(Dealer.IMAGE_NUM, this.needImageNum);
		this.json.put(Dealer.BUG_NUM, this.needBugNum);
		Properties pro = new Properties(Properties.CONFIG_PATH);
		pro.open();
		pro.setProperty(Properties.SETTING, this.json.toString());
		pro.close();
	}

	/**
	 * get items for statistic
	 * 
	 * @return
	 */
	public LinkedList<String> getStatisticItems() {
		LinkedList<String> items = new LinkedList<String>();
		items.addLast(Dealer.NMAE);
		if (this.needAnnotaionNum) {
			items.addLast(Dealer.ANNOTAION_NUM);
		}
		if (this.needBugNum) {
			items.addLast(Dealer.BUG_NUM);
		}
		if (this.needCopy) {
			items.addLast(Dealer.COPY);
		}
		if (this.needHaveBug) {
			items.addLast(Dealer.HAVA_BUG);
		}
		if (this.needImageNum) {
			items.addLast(Dealer.IMAGE_NUM);
		}
		if (this.needPageNum) {
			items.addLast(Dealer.PAGE_NUM);
		}
		if (this.needFormularNum) {
			items.addLast(Dealer.FORMULAR_NUM);
		}
		items.addLast(Dealer.NOTE);
		return items;
	}
}
