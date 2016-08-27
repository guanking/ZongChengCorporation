package pdfTools;

public class Test {
	public static final String Dir = "files/";
	public static void main(String[] args) throws Exception {
//		r();
//		tTrim();
//		readFile("F:/play/projectTemp/ZongChengEleCor/test/978-7-5682-0373-9.txt");
		testExitContent("F:/play/projectTemp/ZongChengEleCor/test/978-7-300-21721-5.pdf","F:/play/projectTemp/ZongChengEleCor/test/978-7-300-21721-5.txt");
		System.out.println("finish!");

	}
	public static void test(){
		String str="£± £² £²";
		str=str.replaceAll(" ", "");
		for(char ele : str.toCharArray()){
			System.out.println((int)ele);
		}
		System.out.println(Integer.parseInt(str));
	}
	public static void r() throws Exception{
//		String str="    \n\n\n    ";
//		System.out.println("A"+str.trim()+"B");
		String filePath=Test.Dir+"test.pdf";
		int begin=1;
		int end=3;
		PdfContent content=new PdfContent(filePath, begin, end);
		content.setDelta(5);
		content.setSep("¡­");
		content.extrat();
		System.out.println("extrat content finish...");
		content.setDes(Test.Dir+"finish.pdf");
		content.setDelta(8);
		content.addContentDirectory();
		System.out.println("add content finish...");
	}
	public static void testCopy() throws Exception{
		int begin=1;
		int end=3;
		PdfContent content=new PdfContent(Test.Dir+"have.pdf", begin, end);
		content.setDes(Test.Dir+"dest_hello_World.pdf");
		content.addContentDirectory();
	}
	public static void readFile(String path) throws Exception{
		String content=FileHelper.readContextFromFile(path);
		System.out.println(content);
	}
	public static void testExitContent(String pdfPath, String contentPath) throws Exception{
		ExitContent content=new ExitContent(pdfPath, contentPath);
		content.extrat();
		content.setDes(Test.Dir+"dest_hello_World.pdf");
		content.addContentDirectory();
		System.out.println(content.toString());
	}
	public static void tTrim(){
		String a="    fdslkfds    ";
		System.out.println(a.trim());
		System.out.println(a);
	}
}
