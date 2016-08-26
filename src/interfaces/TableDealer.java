package interfaces;

import java.util.LinkedList;

import pdfTools.ContentItem;
import pdfTools.PdfContent;

public interface TableDealer {
	/**
	 * ���ݴ�pdf����ȡ�ĵõ���Ŀ¼���table
	 */
	void fillTable(LinkedList<ContentItem> contents);

	/**
	 * ȷ�����Ŀ¼ʱ����Ŀ¼
	 */
	void refreshContent(PdfContent content);
}
