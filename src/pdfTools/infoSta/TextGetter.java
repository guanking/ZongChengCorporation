package pdfTools.infoSta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class TextGetter extends PDFTextStripper {
	/**
	 * pdf�ļ���λ��
	 */
	protected String path;
	protected StringBuffer sb;
	protected int pageNumber = -1;
	/**
	 * ��pdf�ļ��л�ȡ����������
	 */
	protected String text;

	public TextGetter(String path) throws IOException {
		super();
		// TODO Auto-generated constructor stub
		this.setPath(path);
	}

	public void setPath(String path) {
		this.sb = null;
		this.path = path;
	}

	public String getText() throws IOException {
		if (this.sb == null) {
			this.deal();
		}
		return this.text;
	}

	/**
	 * ��ȡ����
	 * 
	 * @throws IOException
	 */
	public void deal() throws IOException {
		this.sb = new StringBuffer();
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(this.path));
			this.setSortByPosition(true);
			this.setStartPage(0);
			this.pageNumber = document.getNumberOfPages();
			this.setEndPage(this.pageNumber);
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			this.writeText(document, dummy);
//			/**
//			 * ���²��滻�ո�
//			 */
//			this.text = TextGetter.scaling(this.sb.toString());
			this.text=this.sb.toString();
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * Override the default functionality of PDFTextStripper.
	 */
	@Override
	protected void writeString(String string, List<TextPosition> textPositions)
			throws IOException {
		for (TextPosition text : textPositions) {
			this.sb.append(text.getUnicode());
		}
	}

	public static void textToFile(String text, String path) throws IOException {
		FileWriter writer = new FileWriter(path);
		writer.write(text);
		writer.flush();
		writer.close();
	}

//	/**
//	 * ��ȫ��ת��Ϊ��ǣ���ȥ���ո�
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static String scaling(String str) {
//		char[] chs = str.toString().toCharArray();
//		for (int i = 0; i < chs.length; i++) {
//			if (chs[i] == 12288) { // ����ǰ�ǿո�ֱ����ȫ�ǿո����
//				chs[i] = ' ';
//			} else if (chs[i] >= 65281 && chs[i] <= 65374) { // �ַ���!��~֮��Ŀɼ��ַ�
//				chs[i] -= 65248;
//			}
//		}
//		String temp = new String(chs);
//		temp.replaceAll("\\s", "");
//		return temp;
//	}

	public String getPath() {
		return this.path;
	}

	public int getPageNumber() throws IOException {
		if (this.pageNumber < 0) {
			document = PDDocument.load(new File(this.path));
			this.pageNumber = document.getNumberOfPages();
		}
		return this.pageNumber;
	}

	public static void main(String[] args) throws IOException {
		// t0();
		TextGetter text = new TextGetter("files/imgError.pdf");
		text.deal();
		TextGetter.textToFile(text.getText(), "files/test.txt");
		System.out.println(text.getText());
	}
	// public static void t0(){
	// String str="��������";
	// char chs[]=str.toCharArray();
	// for(int i=0;i<chs.length;i++){
	// System.out.println(chs[i]+" : "+(char)(chs[i]-65248));
	// }
	// System.out.print(chs);
	// // for(int i=33;i<=126;i++){
	// // System.out.println((char)(i+65248));
	// // }
	// }

}
