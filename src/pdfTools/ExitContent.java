package pdfTools;

import java.io.File;

public class ExitContent extends PdfContent {
	private String contentPath;

	public ExitContent(String path, int begin, int end) {
		super(path, begin, end);
		// TODO Auto-generated constructor stub
	}

	public ExitContent(String pdfPath, String contentPath) throws Exception {
		super(pdfPath, 0, 0);
		this.contentPath = contentPath;
		File pdfFile = new File(this.path);
		File contentFile = new File(this.contentPath);
		if (!pdfFile.exists()) {
			throw new Exception("文件不存在：" + this.path);
		}
		if (!contentFile.exists()) {
			throw new Exception("文件不存在：" + this.contentPath);
		}
		String pdfName = pdfFile.getName();
		String contentName = contentFile.getName();
		if (pdfName.lastIndexOf(".") != -1) {
			pdfName = pdfName.substring(0, pdfName.indexOf("."));
		}
		if (contentName.lastIndexOf(".") != -1) {
			contentName = contentName.substring(0, contentName.indexOf("."));
		}
		if (!pdfName.equals(contentName)) {
			throw new Exception("PDF文件名和TXT文件名不一致：PDF文件名：" + pdfFile.getName()
					+ ";TXT文件名：" + contentFile.getName());
		}
	}

	/**
	 * 根据contentPath所确定的目录文件的位置，提取目录
	 */
	@Override
	public void extrat() throws Exception {
		String context = FileHelper.readContextFromFile(this.contentPath);
		String[] eles = context.split("\n");
		int count, len;
		String temp;
		char ch;
		boolean first = true;
		for (String ele : eles) {
			len = ele.length();
			if (len == 0) {
				continue;
			}
			if (first) {//使用第一行作为delta
				first = false;
				try {
					this.setDelta(Integer.parseInt(ele.trim()));
					continue;
				} catch (NumberFormatException e) {
				}
			}
			count = 0;// 记录每行开头有几个tab
			do {
				ch = ele.charAt(count);
				if (ch == '\t') {
					count++;
				} else {
					break;
				}
			} while (count < len);
			ContentItem contentItem = new ContentItem();
			contentItem.setLevel((short) (count + 1));// 顶级目录的level为1
			temp = ele.substring(count);
			String contentEles[] = temp.split("\t");
			if (contentEles.length != 2) {
				throw new Exception("此行“" + ele + "”：格式错误，请注意空格和tab的区别或者其他错误");
			}
			contentItem.setContent(contentEles[0]);
			try {
				int pageNum = Integer.parseInt(contentEles[1].trim());
				contentItem.setAgeNumber(pageNum);
			} catch (NumberFormatException e) {
				throw new Exception("此行“" + ele + "”：页码格式错误");
			}
			this.items.addLast(contentItem);
			/**
			 * 设置保存位置
			 */
			File file = new File(new File(this.path).getParent(),
					"PDFContentAdder");
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			file = new File(file, new File(this.path).getName());
			this.setDes(file.getAbsolutePath());
		}
	}

}
