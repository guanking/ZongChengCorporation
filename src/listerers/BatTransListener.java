package listerers;

import interfaces.ProgressDealer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pdfTools.ExitContent;
import pdfTools.Test;
import views.Hello;

public class BatTransListener implements ActionListener {
	private JTextField path, contentPath, delta;
	private JRadioButton mode;
	private JTextArea msg;
	private ProgressDealer dealer;

	public BatTransListener(JTextField path, JTextField contentPath,
			JTextField delta, JRadioButton mode, JTextArea msg,
			ProgressDealer dealer) {
		super();
		this.path = path;
		this.contentPath = contentPath;
		this.delta = delta;
		this.mode = mode;
		this.msg = msg;
		this.dealer = dealer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String path = this.path.getText().trim();
		String contentPath = this.contentPath.getText().trim();
		String delta = this.delta.getText().trim();
		this.msg.setText("");// 清空消息显示
		if (this.mode.isSelected()) {
			this.dealSignal(path, contentPath, delta);
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					BatTransListener.this.dealBatch(BatTransListener.this.path
							.getText().trim(),
							BatTransListener.this.contentPath.getText().trim(),
							BatTransListener.this.delta.getText().trim());
				}
			}).start();

		}
	}

	/**
	 * 处理单个pdf文件
	 * 
	 * @param path2
	 * @param contentPath2
	 * @param delta2
	 */
	private void dealSignal(String path2, String contentPath2, String delta2) {
		// TODO Auto-generated method stub
		ExitContent content;
		try {
			this.addMsg("开始处理文件：" + path2);
			content = new ExitContent(path2, contentPath2);
			this.addMsg("开始从" + contentPath2 + "中提取目录");
			int delta = 0;
			try {
				delta = Integer.parseInt(delta2);
			} catch (NumberFormatException e) {
				this.addMsg("“间隔”属性设置失败，使用默认值：0");
				this.msg.setText("0");
				delta = 0;
			}
			content.setDelta(delta);
			content.extrat();
			this.addMsg("添加目录生成新文件：" + content.getDes());
			content.addContentDirectory();
			content = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.msg.append(e.getMessage() + '\n');
			e.printStackTrace();
		}
	}

	private void addMsg(String string) {
		// TODO Auto-generated method stub
		this.msg.append(string + '\n');
	}

	/**
	 * 批量处理：处理文件夹下的pdf文件
	 * 
	 * @param path2
	 *            pdf文件坐在的文件夹
	 * @param contentPath2
	 *            目录所在的文件夹
	 * @param delta2
	 */
	private void dealBatch(String path2, String contentPath2, String delta2) {
		// TODO Auto-generated method stub
		File contentDir = new File(contentPath2);
		File pdfDir = new File(path2);
		if (contentDir.exists() && !contentDir.isDirectory()) {
			this.addMsg(contentPath2 + "不存在或者不是目录文件，自动转换为处理单个pdf文件");
			this.dealSignal(path2, contentPath2, delta2);
			return;
		} else if (pdfDir.exists() && !pdfDir.isDirectory()) {
			this.addMsg(path2 + "不存在或者不是目录文件，自动转换为处理单个pdf文件");
			this.dealSignal(path2, contentPath2, delta2);
			return;
		}
		String pdfs[] = pdfDir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".pdf");
			}
		});
		String contents[] = contentDir.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".txt");
			}
		});
		if (pdfs.length == 0) {
			this.addMsg(path2 + "文件夹下不存在pdf文件");
			return;
		} else if (contents.length == 0) {
			this.addMsg(contentPath2 + "文件夹下不存在txt文件");
			return;
		}
		int success = 0;
		int fail = 0;
		this.dealer.setMaxValue(pdfs.length);
		this.dealer.reset();
		int countPro = 1;
		NEXT: for (String pdf : pdfs) {
			try {
				this.dealer.setValue(countPro++);
				Thread.sleep(20);
				for (String content : contents) {
					if (pdf.substring(0, pdf.length() - 4).equals(
							content.substring(0, content.length() - 4))) {
						this.dealSignal(path2 + '/' + pdf, contentPath2 + "/"
								+ content, "0");
						success++;
						continue NEXT;
					}
				}
				this.addMsg("未能找到" + pdf + "对应的目录描述文件");
				fail++;
				this.moveTFailed(pdfs, contents, path2, contentPath2, pdf);
			} catch (Exception e) {
				this.moveTFailed(pdfs, contents, path2, contentPath2, pdf);
				fail++;
			}
		}
		this.addMsg("批量处理完成，处理总数：" + (success + fail) + "个；成功：" + success
				+ "个；失败：" + fail + "个");
		this.dealer.finish();
	}

	/**
	 * 将处理失败的pdf文件添加到当前文件夹下的PDFContentAddFailure文件夹下
	 * 
	 * @param pdfs
	 *            all name of pdfFiles
	 * @param contents
	 *            all name of txtFiles
	 * @param pdfDir
	 *            location of directory of pdfFiles
	 * @param contentDir
	 *            location of directory of txtFiles
	 * @param pdf
	 *            name of pdfFile dealed failed
	 */
	private void moveTFailed(String pdfs[], String contents[], String pdfDir,
			String contentDir, String pdf) {
		File file = new File(pdfDir, "PDFContentAddFailure");
		if (file == null || !file.isDirectory()) {
			file.getParentFile().mkdirs();
			file.mkdir();
		}
		try {
			Runtime.getRuntime().exec(
					"cmd /c copy \""
							+ (new File(pdfDir, pdf).getAbsolutePath())
							+ "\" \"" + file.getAbsolutePath() + "\"");
			this.addMsg("移动文件“" + (new File(pdfDir, pdf).getAbsolutePath())
					+ "”到文件夹“" + file.getAbsolutePath() + "”下");
			this.addMsg("copy " + (new File(pdfDir, pdf).getAbsolutePath())
					+ " " + file.getAbsolutePath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		for (String content : contents) {
			if (pdf.substring(0, pdf.length() - 4).equals(
					content.substring(0, content.length() - 4))) {
				try {
					Runtime.getRuntime().exec(
							"cmd /c copy \""
									+ (new File(contentDir, content)
											.getAbsolutePath()) + "\" \""
									+ file.getAbsolutePath() + "\"");
					this.addMsg("移动文件“"
							+ (new File(contentDir, content).getAbsolutePath())
							+ "”到文件夹“" + file.getAbsolutePath() + "”下");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

}
