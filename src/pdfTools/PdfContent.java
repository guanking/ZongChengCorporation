package pdfTools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.Region;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import pdfTools.infoSta.TextGetter;

public class PdfContent {
	/**
	 * the pdf file location
	 */
	protected String path;
	/**
	 * content begin page number(included)
	 */
	private int begin;
	/**
	 * content end page number(unincluded)
	 */
	private int end;
	/**
	 * the number of pages unincluding pageNumber from begin
	 */
	private int delta = 0;
	protected LinkedList<ContentItem> items;
	private String sep = "...";
	/**
	 * 重新生成的pdf文件的保存路径
	 */
	private String des;

	public PdfContent(String path, int begin, int end) {
		this.path = path;
		this.begin = begin - 1;
		this.end = end - 1;
		this.items = new LinkedList<ContentItem>();
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin - 1;
	}

	public int getEnd() {
		return end - 1;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	/**
	 * 设置分割线
	 * 
	 * @return
	 */
	public void setSep(String sep) {
		this.sep = TextGetter.scaling(sep);
	}

	public LinkedList<ContentItem> getItems() {
		return items;
	}

	public void setItems(LinkedList<ContentItem> items) {
		this.items = items;
	}

	/**
	 * 提取目录
	 * 
	 * @throws Exception
	 */
	public void extrat() throws Exception {
		PDDocument doc = PDDocument.load(new File(this.path));
		int number = doc.getNumberOfPages();
		if (this.begin < 0 || this.begin > this.end
				|| this.end + this.delta > number) {
			throw new Exception("目录开始页数大于结束页数或者结束页数大于总页数");
		}
		PDDocument tempDoc = new PDDocument();
		for (int i = begin + this.delta; i <= end + this.delta; i++) {
			tempDoc.addPage(doc.getPage(i));
		}
		String content = new TempTextGetter(tempDoc).getText();
		doc.close();
		content = content.replaceAll("[ |\u3000| ]", "");
		// System.out.println(content);
		int begin = 0;
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		LinkedList<String> eles = new LinkedList<String>();
		while (matcher.find(begin)) {
			eles.add(content.substring(begin, matcher.end()));
			begin = matcher.end();
			// System.out.println(eles.getLast());
		}
		for (String str : eles)
			System.out.println(str);
		/**
		 * 0表示未开始处理 <br>
		 * 1表示处理了文字 <br>
		 */
		String tStr, front = "";
		int index, pageNum = 0;
		for (String str : eles) {
			tStr = str.trim();
			if (tStr.length() == 0) {
				front = "";
				// System.out.println(str);
				continue;
			}
			// System.out.println("A"+tStr+"B");
			index = tStr.indexOf(this.sep);
			if (index == -1) {
				front += tStr;
				continue;
			}
			front += tStr.substring(0, index);
			matcher.reset(tStr);
			if (matcher.find(index)) {
				try {
					pageNum = Integer.parseInt(matcher.group());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				items.add(new ContentItem(pageNum, front));
				front = "";
			}
		}// end for
			// System.out.println(front+""+this.items.size());
	}

	/**
	 * 为文件添加目录
	 * 
	 * @throws Exception
	 * 
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void addContentDirectory() throws Exception {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(this.path));
			if (document.isEncrypted()) {
				throw new Exception("文件“" + this.path + "”已被加密，无权限处理");
			}
			PDDocumentOutline root = new PDDocumentOutline();
			document.getDocumentCatalog().setDocumentOutline(root);
			PDOutlineItem outline = null, parent = null;
			PDPageFitWidthDestination dest = null;
			int index = 0;
			for (ContentItem item : this.items) {
				dest = new PDPageFitWidthDestination();
				try {
					dest.setPage(document.getPage(item.getPageNumber()
							+ this.delta - 1));
				} catch (Exception e) {
					// e.printStackTrace();
					throw new Exception("页数错误，错误页数："
							+ (item.getPageNumber() + 1));
				}
				outline = new PDOutlineItem();
				outline.setDestination(dest);
				outline.setTitle(item.getContent());
				outline.closeNode();
				parent = this.getParent(index);
				if (parent == null) {
					root.addLast(outline);
				} else {
					parent.addLast(outline);
				}
			}
			document.save(this.des);
		} catch (Exception e) {
			throw e;
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	/**
	 * 寻找当前子目录的父目录
	 * 
	 * @param index
	 * @return 顶级目录返回null，其他返回父目录
	 */
	private PDOutlineItem getParent(int index) {
		ContentItem p = this.items.get(index);
		int level = p.getLevel();
		while (--index >= 0) {
			p = this.items.get(index);
			if (level - p.getLevel() == 1) {
				return p.getOutline();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "{path:" + this.path + ",begin:" + this.begin + ",end:"
				+ this.end + ",delta:" + this.delta + ",items:" + this.items
				+ ",sep:" + this.sep + "}";
	}

}

class TempTextGetter extends TextGetter {
	private PDDocument document;

	public TempTextGetter(PDDocument document) throws IOException {
		super(null);
		this.document = document;
	}

	public TempTextGetter(String path) throws IOException {
		super(path);
	}

	@Override
	public void deal() throws IOException {
		this.sb = new StringBuffer();
		this.setSortByPosition(true);
		this.setStartPage(0);
		this.pageNumber = document.getNumberOfPages();
		this.setEndPage(this.pageNumber);
		Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
		this.writeText(document, dummy);
		/**
		 * 紧致并替换空格
		 */
		this.text = TextGetter.scaling(this.sb.toString());
		if (document != null) {
			document.close();
		}
	}
}
