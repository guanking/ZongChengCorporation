package pdfTools.infoSta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.sf.json.JSONObject;

public class Properties {
	/**
	 * 属性配置文件的路径
	 */
	public static final String CONFIG_PATH = "config/config.fig";
	public static final String OPEN_DIR = "open_dir";
	/**
	 * 设置属性
	 */
	public static final String SETTING="setting";
	private JSONObject json;
	/**
	 * file path
	 */
	private String path;

	public Properties(String path) {
		this.path = path;
	}

	public void open() throws Exception {
		ObjectInputStream read = null;
		File file = new File(this.path);
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception("您对当前文件“" + file.getAbsolutePath()
						+ "”的使用权限不足");
			}
		}
		try {
			read = new ObjectInputStream(new FileInputStream(this.path));
			this.json = JSONObject.fromObject(read.readObject());
		} catch (Exception e) {
			this.json = new JSONObject();
		}
		if (read != null) {
			read.close();
		}
	}

	public void setProperty(String key, String value) {
		json.put(key, value);
	}

	public String getProperty(String key) {
		try {
			return json.getString(key);
		} catch (Exception e) {
			return null;
		}
	}

	public void close() throws IOException {
		File file = new File(this.path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		ObjectOutputStream writer = new ObjectOutputStream(
				new FileOutputStream(file));
		writer.writeObject(this.json.toString());
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		// Properties pro=new Properties("conf/config");
		// pro.open();
		// // pro.setProperty("path", "caoguanjie");
		//
		// System.out.println("finish锛?+pro.getProperty("path") );
		// pro.close();
	}
}
