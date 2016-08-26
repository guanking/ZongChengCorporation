package interfaces;

import java.util.LinkedList;

import pdfTools.ContentItem;
import pdfTools.PdfContent;

public interface TableDealer {
	/**
	 * 根据从pdf中提取的得到的目录填充table
	 */
	void fillTable(LinkedList<ContentItem> contents);

	/**
	 * 确定添加目录时更新目录
	 */
	void refreshContent(PdfContent content);
}
