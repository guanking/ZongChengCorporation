package pdfTools.infoSta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

/**
 * Hello world!
 *
 */
public class ImageCounter extends PDFStreamEngine {
	/**
	 * pdf文件位置
	 */
	private String path;
	private int count;

	public ImageCounter(String path) {
		super();
		this.path = path;
		this.init();
	}

	public ImageCounter() {
		super();
		this.init();
	}

	protected void init() {
		addOperator(new Concatenate());
		addOperator(new DrawObject());
		addOperator(new SetGraphicsStateParameters());
		addOperator(new Save());
		addOperator(new Restore());
		addOperator(new SetMatrix());
		this.count = -1;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.count = -1;
		this.path = path;
	}

	/**
	 * This is used to handle an operation.
	 */
	@Override
	protected void processOperator(Operator operator, List<COSBase> operands)
			throws IOException {
		String operation = operator.getName();
		System.out.println(operation);
		if ("Do".equals(operation)) {
			COSName objectName = (COSName) operands.get(0);
			PDXObject xobject = getResources().getXObject(objectName);
			if (xobject instanceof PDImageXObject) {
				this.count++;// 图片个数加一
			} else if (xobject instanceof PDFormXObject) {
				PDFormXObject form = (PDFormXObject) xobject;
				showForm(form);
			}
		} else {
			super.processOperator(operator, operands);
		}
	}

	/**
	 * 开始处理
	 * 
	 * @throws IOException
	 */
	public void deal() throws IOException {
		this.count = 0;
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(this.path));
			// int pageNum = 0;
			for (PDPage page : document.getPages()) {
				// pageNum++;
				// System.out.println("Processing page: " + pageNum);
				this.processPage(page);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ImageCounter counter = new ImageCounter("files/imgError.pdf");
		counter.deal();
		System.out.println("total:" + counter.getCount());
	}

	public int getCount() throws IOException {
		if (this.count < 0) {
			this.deal();
		}
		return this.count;
	}
}
