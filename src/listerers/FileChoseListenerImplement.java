package listerers;

import interfaces.ModeDealer;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.plaf.FileChooserUI;

import pdfTools.infoSta.Properties;

public class FileChoseListenerImplement implements ActionListener {
	/**
	 * 文件选择框所选择的文件
	 */
	private File file;
	private File[] files;
	private Component parent;
	private JList<File> list;
	/**
	 * 是否是选择单个文件
	 */
	private boolean isFile = false;
	private ModeDealer modeDealer;

	public FileChoseListenerImplement(Component parent) {
		super();
		this.parent = parent;
	}

	public void setList(JList<File> list) {
		this.list = list;
	}

	public File getFile() {
		return file;
	}

	public File[] getFiles() {
		return files;
	}

	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem menu = (JMenuItem) e.getSource();
			this.isFile = menu.getText().equals("打开文件");
		}
		Properties pro = new Properties(Properties.CONFIG_PATH);
		try {
			pro.open();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		String openDir = pro.getProperty(Properties.OPEN_DIR);
		JFileChooser jfc = null;
		if (openDir == null) {
			jfc = new JFileChooser();
		} else {
			jfc = new JFileChooser(openDir);
		}
		jfc.setFileSelectionMode(this.isFile ? JFileChooser.FILES_ONLY
				: JFileChooser.DIRECTORIES_ONLY);
		jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return FileChoseListenerImplement.this.isFile ? "PDF" : "文件夹";
			}

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return FileChoseListenerImplement.this.isFile ? f.getName()
						.endsWith(".pdf") || f.isDirectory() : f.isDirectory();
			}
		});
		jfc.showDialog(this.parent, "选择");
		this.file = jfc.getSelectedFile();
		if (this.file == null) {
			this.files = null;
			return;
		}
		if (file.isDirectory()) {
			this.files = this.file.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					return pathname.getName().endsWith(".pdf");
				}
			});
		} else if (this.file.isFile()) {
			this.files = null;
		} else {
			System.out.println("文件:" + file.getAbsolutePath());
		}
		this.initList();
		if (this.modeDealer != null) {
			this.modeDealer.dealMode(this.file.getAbsolutePath(),
					(JComponent) e.getSource());
		}
	}

	private void initList() {
		DefaultListModel<File> listModel = new DefaultListModel<File>();
		this.list.setSize(100, 0);
		if (this.file.isDirectory()) {
			for (File ele : this.files) {
				listModel.addElement(ele);
			}
		} else {
			listModel.addElement(this.file);
		}
		this.list.setModel(listModel);
	}

	public void setModeDealer(ModeDealer modeDealer) {
		this.modeDealer = modeDealer;
	}
}
