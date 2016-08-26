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
			throw new Exception("�ļ������ڣ�" + this.path);
		}
		if (!contentFile.exists()) {
			throw new Exception("�ļ������ڣ�" + this.contentPath);
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
			throw new Exception("PDF�ļ�����TXT�ļ�����һ�£�PDF�ļ�����" + pdfFile.getName()
					+ ";TXT�ļ�����" + contentFile.getName());
		}
	}

	/**
	 * ����contentPath��ȷ����Ŀ¼�ļ���λ�ã���ȡĿ¼
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
			if (first) {//ʹ�õ�һ����Ϊdelta
				first = false;
				try {
					this.setDelta(Integer.parseInt(ele.trim()));
					continue;
				} catch (NumberFormatException e) {
				}
			}
			count = 0;// ��¼ÿ�п�ͷ�м���tab
			do {
				ch = ele.charAt(count);
				if (ch == '\t') {
					count++;
				} else {
					break;
				}
			} while (count < len);
			ContentItem contentItem = new ContentItem();
			contentItem.setLevel((short) (count + 1));// ����Ŀ¼��levelΪ1
			temp = ele.substring(count);
			String contentEles[] = temp.split("\t");
			if (contentEles.length != 2) {
				throw new Exception("���С�" + ele + "������ʽ������ע��ո��tab�����������������");
			}
			contentItem.setContent(contentEles[0]);
			try {
				int pageNum = Integer.parseInt(contentEles[1].trim());
				contentItem.setAgeNumber(pageNum);
			} catch (NumberFormatException e) {
				throw new Exception("���С�" + ele + "����ҳ���ʽ����");
			}
			this.items.addLast(contentItem);
			/**
			 * ���ñ���λ��
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
