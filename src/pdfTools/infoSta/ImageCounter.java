package pdfTools.infoSta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

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

import pdfTools.setting.Setter;

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
	/**
	 * legal width
	 */
	private int width;
	/**
	 * legal height
	 */
	private int height;
	private int countDetails[];

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
		Setter setter;
		try {
			setter = new Setter();
			this.height = setter.getLegalHeight();
			this.width = setter.getLegalWidth();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "配置文件异常，图片有效长宽使用默认值（0）详细原因：\n"
					+ e.getMessage(), "错误", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			this.width = this.height = 0;
		}
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
		// System.out.println(operation);
		if ("Do".equals(operation)) {
			COSName objectName = (COSName) operands.get(0);
			PDXObject xobject = getResources().getXObject(objectName);
			if (xobject instanceof PDImageXObject) {
				PDImageXObject img = (PDImageXObject) xobject;
				if (img.getWidth() >= this.width
						|| img.getHeight() >= this.height) {
					this.count++;// 图片个数加一
				}
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
		int front = 0;
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(this.path));
			this.countDetails = new int[document.getNumberOfPages()];
			int pageNum = 0;
			for (PDPage page : document.getPages()) {
				// System.out.println("Processing page: " + pageNum);
				this.processPage(page);
				this.countDetails[pageNum++] = this.count - front;
				front = this.count;
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ImageCounter counter = new ImageCounter("files/test.pdf");
		counter.deal();
		System.out.println(counter);
	}

	public int getCount() throws IOException {
		if (this.count < 0) {
			this.deal();
		}
		return this.count;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.countDetails.length; i++) {
			sb.append(i + " : " + this.countDetails[i] + "\n");
		}
		sb.append("total:" + this.count+"\n");
		return sb.toString();

	}
}
