package pdfTools.infoSta;

public class Detecter {
	private String[] items;
	private int count;
	private String context;

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.count = -1;
		this.items = items;
	}

	public int getCount() throws Exception {
		if (this.count < 0) {
			this.deal();
		}
		return count;
	}

	public void setContext(String context) {
		this.context = context;
	}
	public String getContext(){
		return this.context;
	}
	public Detecter(String[] items) {
		super();
		this.items = items;
	}

	public void deal() throws Exception {
		if (this.context == null) {
			throw new Exception("文件无内容");
		}
		this.count = 0;
		int begin = 0;
		for (String ele : this.items) {
			begin = 0;
			while (begin != -1) {
				begin = this.context.indexOf(ele, begin);
				if (begin != -1) {
					this.count++;
					begin += ele.length();
				}
			}
			System.out.println("\""+ele+"\":"+this.count);
		}
	}

	public static void main(String[] args) throws Exception {
		Detecter det = new Detecter(new String[] { "index.html","\n","\n" });
		TextGetter t = new TextGetter("files/english.pdf");
		det.setContext(t.getText());
		det.deal();
		System.out.println(det.getCount());
	}

}
