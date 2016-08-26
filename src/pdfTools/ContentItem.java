package pdfTools;

import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;


public class ContentItem {
	protected int ageNumber;
	protected String content;
	/**
	 * 最上层目录的层次为1，孩子一次递加
	 */
	protected int level;
	private PDOutlineItem outline;
	public ContentItem(int ageNumber, String content) {
		super();
		this.ageNumber = ageNumber-1;
		this.content = content;
		this.level = 1;
	}

	public PDOutlineItem getOutline() {
		return outline;
	}

	public void setOutline(PDOutlineItem outline) {
		this.outline = outline;
	}

	public ContentItem() {
		this.level = 1;
	}

	public int getPageNumber() {
		return ageNumber;
	}

	public void setAgeNumber(int ageNumber) {
		this.ageNumber = ageNumber-1;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "{content:" + this.content + ",ageNumber:" + this.ageNumber+",level:"+this.level
				+ "}";
	}
}
