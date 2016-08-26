package views;

import interfaces.DialogDealer;
import interfaces.ModeDealer;
import interfaces.ProgressDealer;
import interfaces.TableDealer;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

import listerers.BatTransListener;
import listerers.ContentFiller;
import listerers.ContentGenerater;
import listerers.FileChoseListenerImplement;
import listerers.JListSelectListenerImplement;
import pdfTools.ContentItem;
import pdfTools.PdfContent;
import pdfTools.infoSta.Dealer;
import pdfTools.infoSta.ExtractToppage;
import pdfTools.infoSta.Extractor;
import pdfTools.infoSta.Properties;
import renders.ContentItemListRender;
import renders.ContentItemRender;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.JToggleButton;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import java.awt.Dimension;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JTable;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.Frame;

public class Hello implements ModeDealer, TableDealer, ProgressDealer,
		DialogDealer {
	/**
	 * 0:单个生成<br>
	 * 1:目录批量生成<br>
	 * 2:信息批量处理<br>
	 * 3:<br>
	 */
	private short mode = 0;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private FileChoseListenerImplement fileChoseListener;
	private ContentFiller fillerListener;
	private ContentGenerater generatorListener;
	private JPanel panel_10;
	private JSplitPane splitPane;
	private JProgressBar progressBar;
	private JTextField batDelta;
	private JTextField batPath;
	private JTextArea txtMsg;
	private JRadioButton batMode;
	private JTextField batContentPath;
	private JMenuItem openFileMenu;
	private JMenuItem openDirMenu;
	private JTable table;
	private JRadioButton cascadeDeal;
	private JTabbedPane tabbedPane;
	private JListSelectListenerImplement fileListListener;
	private JButton colAdd;
	private JButton colDel;
	private Stack<Object[]> doStack, undoStack;
	private JMenu edit;
	/**
	 * pdf信息统计表格
	 */
	private JTable infoTable;
	private JTextField pdfInfo;
	private JTextField formularLibs;
	private JTextField randomsLibs;
	/**
	 * 导出excel
	 */
	private JButton extractInfo;
	private JMenuItem extractMenu;
	/**
	 * 提取pdf首页
	 */
	private JMenuItem extractToppageMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hello window = new Hello();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Hello() {
		this.doStack = new Stack<Object[]>();
		this.undoStack = new Stack<Object[]>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setTitle("\u676D\u5DDE\u7EB5\u8BDA\u6570\u5B57\u79D1\u6280\u6709\u9650\u516C\u53F8");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"images/logo.png"));
		frame.setBounds(100, 100, 805, 558);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u6587\u4EF6");
		menuBar.add(menu);

		openFileMenu = new JMenuItem("\u6253\u5F00\u6587\u4EF6");
		openFileMenu.setIcon(new ImageIcon("images/resource_persp.png"));
		menu.add(openFileMenu);
		this.fileChoseListener = new FileChoseListenerImplement(frame);
		this.fileChoseListener.setModeDealer(this);
		openFileMenu.addActionListener(this.fileChoseListener);
		openDirMenu = new JMenuItem("\u6253\u5F00\u6587\u4EF6\u5939");
		openDirMenu.setIcon(new ImageIcon("images/fldr_obj.png"));
		openDirMenu.addActionListener(this.fileChoseListener);
		menu.add(openDirMenu);

		JMenuItem menuItem_1 = new JMenuItem("\u9000\u51FA");
		menuItem_1.setIcon(new ImageIcon("images/error_tsk.png"));
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		/**
		 * 选择库
		 */
		JMenuItem menuItem_9 = new JMenuItem("\u9009\u62E9\u5E93");
		menuItem_9.setIcon(new ImageIcon("images/lib.png"));
		menuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoDialog dia = new InfoDialog(Hello.this);
				dia.setVisible(true);
				Hello.this.frame.setEnabled(false);
			}
		});
		menu.add(menuItem_9);
		menu.add(menuItem_1);

		edit = new JMenu("\u7F16\u8F91");
		menuBar.add(edit);

		JMenuItem menuItem_2 = new JMenuItem("\u540E\u9000");
		menuItem_2.addActionListener(new ActionListener() {// 后退
					public void actionPerformed(ActionEvent e) {
						if (!Hello.this.undoStack.empty()) {
							DefaultTableModel model = (DefaultTableModel) Hello.this.table
									.getModel();
							Object[] obj = Hello.this.undoStack.pop();
							int row = (int) obj[1];
							if (obj[0].equals("add")) {
								obj = new Object[] { "del", row,
										model.getValueAt(row, 0),
										model.getValueAt(row, 1),
										model.getValueAt(row, 2) };
								model.removeRow(row);
							} else {
								obj[0] = "add";
								if (obj.length == 5) {
									model.insertRow(row, new Object[] { obj[2],
											obj[3], obj[4] });
								} else {
									model.insertRow(row, new Object[] { null,
											null, null });
								}
							}
							Hello.this.doStack.push(obj);
						}
						Hello.this.table.validate();
					}
				});
		menuItem_2.setIcon(new ImageIcon("images/backward_nav.png"));
		edit.add(menuItem_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u524D\u8FDB");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// 前进
				if (!Hello.this.doStack.empty()) {
					DefaultTableModel model = (DefaultTableModel) Hello.this.table
							.getModel();
					Object[] obj = Hello.this.doStack.pop();
					int row = (int) obj[1];
					if (obj[0].equals("add")) {
						obj[0] = "del";
						obj = new Object[] { "del", row,
								model.getValueAt(row, 0),
								model.getValueAt(row, 1),
								model.getValueAt(row, 2) };
						model.removeRow(row);
					} else {
						obj[0] = "add";
						if (obj.length == 5) {
							model.insertRow(row, new Object[] { obj[2], obj[3],
									obj[4] });
						} else {
							model.insertRow(row, new Object[] { null, null,
									null });
						}
					}
					Hello.this.undoStack.push(obj);
				}
				Hello.this.table.validate();
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon("images/forward_nav.png"));
		edit.add(mntmNewMenuItem);

		JMenuItem menuItem = new JMenuItem("\u6DFB\u52A0");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.colAdd.doClick();
			}
		});
		menuItem.setIcon(new ImageIcon("images/monitorexpression_tsk.png"));
		edit.add(menuItem);

		JMenuItem menuItem_6 = new JMenuItem("\u5220\u9664");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.colDel.doClick();
			}
		});
		menuItem_6.setIcon(new ImageIcon("images/delete_config.png"));
		edit.add(menuItem_6);

		JMenu menu_2 = new JMenu("\u5DE5\u5177");
		menuBar.add(menu_2);

		JMenuItem menuItem_3 = new JMenuItem("\u4FE1\u606F\u7EDF\u8BA1");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.tabbedPane.setSelectedIndex(2);
			}
		});
		menuItem_3.setIcon(new ImageIcon("images/info.png"));
		menu_2.add(menuItem_3);
		/**
		 * 导出excel
		 */
		extractMenu = new JMenuItem("\u5BFC\u51FAexcel");
		extractMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.extractInfo.doClick();
			}
		});
		extractMenu.setIcon(new ImageIcon("images/defaults_ps.png"));
		menu_2.add(extractMenu);

		JMenuItem menuItem_7 = new JMenuItem(
				"\u76EE\u5F55\u6279\u91CF\u751F\u6210");
		menuItem_7.setIcon(new ImageIcon("images/copy_edit_co.png"));
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.tabbedPane.setSelectedIndex(1);
			}
		});
		/**
		 * 首页提取
		 */
		extractToppageMenu = new JMenuItem("\u9996\u9875\u63D0\u53D6");
		extractToppageMenu.setIcon(new ImageIcon("images/extract.png"));
		extractToppageMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Hello.this.pdfInfo.getText().trim();
				if (path == null || path.length() == 0) {
					JOptionPane.showMessageDialog(null, "请选择文件夹", "错误",
							JOptionPane.ERROR_MESSAGE);
					Hello.this.extractToppageMenu.setEnabled(false);
					return;
				} else {
					new Thread(new ExtractToppage(path, Hello.this)).start();
				}
			}
		});
		menu_2.add(extractToppageMenu);
		menu_2.add(menuItem_7);

		JMenuItem menuItem_8 = new JMenuItem(
				"\u76EE\u5F55\u5355\u4E2A\u751F\u6210");
		menuItem_8.setIcon(new ImageIcon("images/file_obj.png"));
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hello.this.tabbedPane.setSelectedIndex(0);
			}
		});
		menu_2.add(menuItem_8);

		JMenu menu_3 = new JMenu("\u5E2E\u52A9");
		menuBar.add(menu_3);

		JMenuItem menuItem_4 = new JMenuItem("\u4F7F\u7528\u8BF4\u660E");
		menuItem_4.setIcon(new ImageIcon("images/help_contents.png"));
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Runtime runtime = Runtime.getRuntime();
						try {
							runtime.exec("cmd /c start index.html");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		menu_3.add(menuItem_4);

		JMenuItem menuItem_5 = new JMenuItem("\u5173\u4E8E");
		menuItem_5.setIcon(new ImageIcon("images/about.png"));
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Hello.this.frame,
						"公司：杭州纵诚数字科技有限公司\n版本：1.0\n时间：2016-8-20", "关于",
						JOptionPane.PLAIN_MESSAGE, new ImageIcon(Toolkit
								.getDefaultToolkit()
								.getImage("images/icon.png")));
			}
		});
		menu_3.add(menuItem_5);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		panel_10 = new JPanel();

		this.splitPane = new JSplitPane();
		splitPane.setDividerSize(4);

		JList<File> list = new JList<File>();
		list.setSelectedIndex(1);
		list.setPreferredSize(new Dimension(192, 137));
		list.setMaximumSize(new Dimension(192, 137));
		list.setSize(new Dimension(192, 137));
		this.fileChoseListener.setList(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ContentItemListRender());
		fileListListener = new JListSelectListenerImplement();
		list.addListSelectionListener(fileListListener);
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setSize(new Dimension(192, 173));
		jScrollPane1.setPreferredSize(new java.awt.Dimension(192, 173));
		jScrollPane1.setViewportView(list);
		splitPane.setLeftComponent(jScrollPane1);

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(3, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel label = new JLabel("\u9009\u62E9\u6587\u4EF6\uFF1A");
		panel_2.add(label);

		textField = new JTextField();
		fileListListener.setTextField(textField);
		textField.setEditable(false);
		panel_2.add(textField);
		textField.setColumns(40);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);

		JLabel lblDelta = new JLabel("\u95F4\u9694\uFF1A");
		panel_3.add(lblDelta);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("0");
		panel_3.add(textField_1);
		textField_1.setColumns(5);

		JLabel label_2 = new JLabel("\u76EE\u5F55\u8D77\u59CB\u9875\uFF1A");
		panel_3.add(label_2);

		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(textField_2);
		textField_2.setColumns(5);

		JLabel label_3 = new JLabel("\u76EE\u5F55\u7ED3\u675F\u9875\uFF1A");
		panel_3.add(label_3);

		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(textField_3);
		textField_3.setColumns(5);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		JLabel label_4 = new JLabel(
				"\u76EE\u5F55\u5206\u9694\u7EBF(\u6700\u5C0F\u5FAA\u73AF)\uFF1A");
		panel_4.add(label_4);

		textField_4 = new JTextField();
		textField_4.setColumns(30);
		panel_4.add(textField_4);

		JLabel label_5 = new JLabel("*\u5FC5\u586B");
		label_5.setFont(new Font("宋体", Font.BOLD, 12));
		label_5.setForeground(Color.RED);
		panel_4.add(label_5);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.EAST);
		panel_5.setLayout(new GridLayout(5, 1, 0, 0));

		JPanel panel_12 = new JPanel();
		panel_5.add(panel_12);
		panel_12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

		JButton button_1 = new JButton("\u751F\u6210\u76EE\u5F55");
		this.fillerListener = new ContentFiller(textField, textField_4,
				textField_1, textField_2, textField_3);
		this.fillerListener.setContentFiller(this);
		button_1.addActionListener(this.fillerListener);
		button_1.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_12.add(button_1);

		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9);
		panel_9.setLayout(new GridLayout(2, 1, 0, 0));

		cascadeDeal = new JRadioButton("\u7EA7\u8FDE\u5C42\u7EA7");
		panel_9.add(cascadeDeal);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(
				"\u5355\u4E2A\u5904\u7406");
		rdbtnNewRadioButton_1.setSelected(true);
		ButtonGroup bs = new ButtonGroup();
		bs.add(cascadeDeal);
		bs.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setName("tableEditType");
		panel_9.add(rdbtnNewRadioButton_1);

		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		colDel = new JButton("\u5220\u9664");
		colDel.setIconTextGap(8);
		colDel.setMargin(new Insets(2, 8, 2, 14));
		colDel.setInheritsPopupMenu(true);
		colDel.setIcon(new ImageIcon("images/delete.png"));
		/**
		 * 从table中删除一条记录
		 */
		colDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = Hello.this.table.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) Hello.this.table
						.getModel();
				index = index != -1 ? index : model.getRowCount() - 1;
				if (index >= 0) {
					Hello.this.undoStack.push(new Object[] { "del", index,
							model.getValueAt(index, 0),
							model.getValueAt(index, 1),
							model.getValueAt(index, 2) });
					model.removeRow(index);
				}
			}
		});
		panel_8.add(colDel);

		JPanel panel_11 = new JPanel();
		panel_6.add(panel_11);
		panel_11.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		colAdd = new JButton("\u6DFB\u52A0");
		colAdd.setMargin(new Insets(2, 8, 2, 14));
		colAdd.setIconTextGap(8);
		colAdd.setIcon(new ImageIcon("images/add.png"));
		/**
		 * 向table中添加一行新的记录
		 */
		colAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) Hello.this.table
						.getModel();
				int index = Hello.this.table.getSelectedRow();
				if (index == -1) {
					model.addRow(new Object[] { null, null });
					Hello.this.undoStack.push(new Object[] { "add",
							model.getRowCount() - 1 });
				} else {
					model.insertRow(index, new Object[] { null, null });
					Hello.this.undoStack.push(new Object[] { "add", index });
				}
				Hello.this.table.validate();
			}
		});
		panel_11.add(colAdd);
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		FlowLayout fl_panel_7 = new FlowLayout(FlowLayout.CENTER, 5, 20);
		panel_7.setLayout(fl_panel_7);

		JButton btnNewButton_1 = new JButton("\u786E\u5B9A");
		this.generatorListener = new ContentGenerater(this.fillerListener);
		this.generatorListener.setDelta(textField_1);
		btnNewButton_1.addActionListener(this.generatorListener);
		btnNewButton_1.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_7.add(btnNewButton_1);
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setPreferredSize(new java.awt.Dimension(192, 173));
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.BLUE);
		progressBar.setPreferredSize(new Dimension(146, 20));
		frame.getContentPane().add(progressBar, BorderLayout.SOUTH);
		panel.add(jScrollPane2, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionBackground(new Color(173, 255, 47));
		table.setRowHeight(25);
		table.setFont(new Font("宋体", Font.PLAIN, 14));
		table.setIgnoreRepaint(true);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null }, { null, null, null }, }, new String[] {
				"\u540D\u79F0", "\u9875\u6570",
				"\u5C42\u7EA7\uFF08\u9876\u7EA7\u76EE\u5F55\u4E3A1\uFF09" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class,
					Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(315);
		table.getColumnModel().getColumn(1).setPreferredWidth(74);
		table.getColumnModel().getColumn(1).setMaxWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(131);
		table.getColumnModel().getColumn(2).setMaxWidth(150);
		table.setCellSelectionEnabled(true);
		this.table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if (!Hello.this.cascadeDeal.isSelected()) {
					return;
				}
				int row = e.getFirstRow();
				int col = e.getColumn();
				System.out.println("row:" + row + ";col:" + col);
				if (row == TableModelEvent.HEADER_ROW) {
					return;
				}
				if (col != 2) {
					return;
				}
				DefaultTableModel model = (DefaultTableModel) Hello.this.table
						.getModel();
				int rows = model.getRowCount();
				Object value = model.getValueAt(row, col);
				while (++row < rows) {
					model.setValueAt(value, row, col);
				}
				Hello.this.table.validate();
			}
		});
		DefaultTableCellRenderer tableRender = new DefaultTableCellRenderer();
		tableRender.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Integer.class, tableRender);
		jScrollPane2.setViewportView(table);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		/**
		 * 模式切换
		 */
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JTabbedPane s = (JTabbedPane) e.getSource();
				switch (s.getSelectedIndex()) {
				case 0:// 单个pdf处理
					Hello.this.mode = 0;
					Hello.this.openDirMenu.setEnabled(true);
					Hello.this.openFileMenu.setEnabled(true);
					Hello.this.fileListListener.setUseful(true);
					Hello.this.fileListListener
							.setTextField(Hello.this.textField);
					Hello.this.edit.setEnabled(true);
					Hello.this.extractMenu.setEnabled(false);
					Hello.this.extractToppageMenu.setEnabled(false);
					Hello.this.extractMenu.setEnabled(false);
					break;
				case 1:// 批量pdf处理
					Hello.this.mode = 1;
					Hello.this.openDirMenu.setEnabled(!Hello.this.batMode
							.isSelected());
					Hello.this.openFileMenu.setEnabled(Hello.this.batMode
							.isSelected());
					Hello.this.fileListListener.setUseful(!Hello.this.batMode
							.isSelected());
					Hello.this.fileListListener
							.setTextField(Hello.this.batPath);
					Hello.this.edit.setEnabled(false);
					Hello.this.extractMenu.setEnabled(false);
					Hello.this.extractToppageMenu.setEnabled(false);
					Hello.this.extractMenu.setEnabled(false);
					break;
				case 2:// 信息统计
					Hello.this.mode = 2;
					Hello.this.edit.setEnabled(false);
					Hello.this.fileListListener.setUseful(false);
					Hello.this.openFileMenu.setEnabled(false);// 选择单个文件不可用
					Hello.this.extractMenu.setEnabled(true);
					Hello.this.extractInfo.setEnabled(false);
					Hello.this.extractMenu.setEnabled(false);
				}
			}
		});
		tabbedPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tabbedPane.setForeground(Color.BLACK);
		splitPane.setRightComponent(tabbedPane);
		tabbedPane.addTab("单文件目录生成", new ImageIcon("images/file_obj.png"),
				panel, null);

		JPanel panel_14 = new JPanel();
		tabbedPane.addTab("目录批量生成", new ImageIcon("images/copy_edit_co.png"),
				panel_14, null);
		panel_14.setLayout(new BorderLayout(0, 0));

		JPanel panel_13 = new JPanel();
		panel_14.add(panel_13, BorderLayout.NORTH);
		panel_13.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_15 = new JPanel();
		panel_13.add(panel_15);

		JLabel lblNewLabel = new JLabel("PDF\u6587\u4EF6\uFF1A");
		panel_15.add(lblNewLabel);

		this.batPath = new JTextField();
		this.batPath.setEditable(false);
		panel_15.add(this.batPath);
		this.batPath.setColumns(35);

		JLabel label_1 = new JLabel("                     ");
		panel_15.add(label_1);

		JPanel panel_16 = new JPanel();
		panel_13.add(panel_16);

		JButton btnNewButton_2 = new JButton("TXT\u6587\u4EF6\uFF1A");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if (Hello.this.batMode.isSelected()) {// 批量文件
					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				} else {// 单个文件
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
				jfc.showDialog((Component) e.getSource(), "选择");
				File file = jfc.getSelectedFile();
				if (file == null) {
					return;
				}
				Hello.this.batContentPath.setText(file.getAbsolutePath());
			}
		});
		btnNewButton_2.setMargin(new Insets(0, 4, 0, 0));
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_16.add(btnNewButton_2);

		batContentPath = new JTextField();
		panel_16.add(batContentPath);
		batContentPath.setColumns(35);

		batMode = new JRadioButton("\u5355\u4E2A\u6587\u4EF6");
		batMode.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Hello.this.fileListListener.setUseful(!Hello.this.batMode
						.isSelected());
			}
		});
		panel_16.add(batMode);

		JPanel panel_17 = new JPanel();
		panel_14.add(panel_17, BorderLayout.SOUTH);
		panel_17.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblDelta_1 = new JLabel("\u95F4\u9694\uFF1A");
		panel_17.add(lblDelta_1);

		this.batDelta = new JTextField();
		panel_17.add(this.batDelta);
		this.batDelta.setColumns(5);

		JButton batConfirm = new JButton("\u786E\u5B9A");
		this.txtMsg = new JTextArea();
		batConfirm.addActionListener(new BatTransListener(this.batPath,
				this.batContentPath, this.batDelta, this.batMode, this.txtMsg,
				this));// 批量处理pdf文件
		panel_17.add(batConfirm);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.RAISED,
				new Color(255, 200, 0), Color.CYAN));
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_14.add(scrollPane, BorderLayout.CENTER);

		txtMsg.setMargin(new Insets(5, 10, 5, 10));
		txtMsg.setLineWrap(true);
		scrollPane.setViewportView(txtMsg);
		tabbedPane.setSelectedIndex(0);

		JPanel panel_18 = new JPanel();
		tabbedPane.addTab("\u4FE1\u606F\u7EDF\u8BA1", new ImageIcon(
				"images/info.png"), panel_18, null);
		panel_18.setLayout(new BorderLayout(0, 0));

		JPanel panel_19 = new JPanel();
		panel_18.add(panel_19, BorderLayout.NORTH);
		panel_19.setLayout(new GridLayout(3, 1, 0, 0));
		/**
		 * 批量信息处理
		 */

		JPanel panel_24 = new JPanel();
		panel_19.add(panel_24);

		JLabel lblNewLabel_1 = new JLabel("\u6587\u4EF6\u5939\uFF1A");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_24.add(lblNewLabel_1);

		pdfInfo = new JTextField();
		pdfInfo.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_24.add(pdfInfo);
		pdfInfo.setEditable(false);
		pdfInfo.setColumns(40);

		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
		panel_24.add(btnNewButton);

		JPanel panel_25 = new JPanel();
		panel_19.add(panel_25);

		JLabel label_6 = new JLabel("\u4E71\u7801\u5E93\uFF1A");
		label_6.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_25.add(label_6);

		randomsLibs = new JTextField();
		randomsLibs.setEditable(false);
		randomsLibs.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_25.add(randomsLibs);
		randomsLibs.setColumns(50);

		JPanel panel_26 = new JPanel();
		panel_19.add(panel_26);

		JLabel label_7 = new JLabel("\u516C\u5F0F\u5E93\uFF1A");
		label_7.setFont(new Font("宋体", Font.PLAIN, 14));
		panel_26.add(label_7);

		formularLibs = new JTextField();
		formularLibs.setEditable(false);
		formularLibs.setFont(new Font("宋体", Font.PLAIN, 14));
		formularLibs.setColumns(50);
		panel_26.add(formularLibs);
		/**
		 * 信息统计
		 */
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) Hello.this.infoTable
						.getModel();
				int count = model.getRowCount();
				for (int i = count - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				Hello.this.infoTable.validate();
				String path = Hello.this.pdfInfo.getText();
				if (path == null && path.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请选择文件夹", "错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Dealer dealer = new Dealer(path);
				dealer.setPro(Hello.this);
				path = Hello.this.randomsLibs.getText();
				if (path == null || path.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请选择乱码库", "错误",
							JOptionPane.ERROR_MESSAGE);
					dealer = null;
					return;
				} else {
					dealer.setBugs(InfoDialog.RANDOMS + "/" + path);
				}
				path = Hello.this.formularLibs.getText();
				if (path == null || path.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请选择公式库", "错误",
							JOptionPane.ERROR_MESSAGE);
					dealer = null;
					return;
				} else {
					dealer.setFormulars(InfoDialog.FORMULARS + "/" + path);
				}
				new Thread(dealer).start();
			}
		});

		extractInfo = new JButton("\u5BFC\u51FA");
		extractInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = Hello.this.pdfInfo.getText();
				if (path == null || path.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "请选择要处理的pdf所在文件夹",
							"参数错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DefaultTableModel model = (DefaultTableModel) Hello.this.infoTable
						.getModel();
				HashMap<String, String> cols = new HashMap<String, String>();
				cols.put(Dealer.NMAE, "PDF文件名");
				cols.put(Dealer.COPY, "是否可拷贝");
				cols.put(Dealer.BUG_NUM, "乱码数");
				cols.put(Dealer.FORMULAR_NUM, "公式数");
				cols.put(Dealer.IMAGE_NUM, "图片数");
				cols.put(Dealer.PAGE_NUM, "页数");
				cols.put(Dealer.NOTE, "备注");
				int len = model.getRowCount();
				JSONArray jsons = new JSONArray();
				JSONObject json;
				for (int i = 0; i < len; i++) {
					json = new JSONObject();
					json.put(Dealer.NMAE, model.getValueAt(i, 0));
					json.put(Dealer.COPY, model.getValueAt(i, 1));
					json.put(Dealer.IMAGE_NUM, model.getValueAt(i, 2));
					json.put(Dealer.FORMULAR_NUM, model.getValueAt(i, 3));
					json.put(Dealer.PAGE_NUM, model.getValueAt(i, 4));
					json.put(Dealer.BUG_NUM, model.getValueAt(i, 5));
					json.put(Dealer.NOTE, model.getValueAt(i, 6));
					jsons.add(json);
				}
				new Thread(new Extractor(new File(path, "statistic.xls")
						.getAbsolutePath(), cols, jsons, Hello.this)).start();
			}
		});
		panel_18.add(extractInfo, BorderLayout.SOUTH);

		JPanel panel_20 = new JPanel();
		panel_18.add(panel_20, BorderLayout.WEST);
		panel_20.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_18.add(scrollPane_1, BorderLayout.CENTER);

		infoTable = new JTable();
		infoTable.setRowHeight(25);
		infoTable.setFont(new Font("宋体", Font.PLAIN, 14));
		infoTable.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, }, new String[] {
				"PDF\u6587\u4EF6\u540D\u79F0",
				"\u662F\u5426\u53EF\u62F7\u8D1D", "\u56FE\u7247\u6570",
				"\u516C\u5F0F\u6570", "\u9875\u6570",
				"\u662F\u5426\u6709\u4E71\u7801", "\u5907\u6CE8" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					Integer.class, Integer.class, Integer.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		infoTable.getColumnModel().getColumn(0).setPreferredWidth(92);
		infoTable.getColumnModel().getColumn(1).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(2).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(3).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(4).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(5).setMaxWidth(100);
		infoTable.getColumnModel().getColumn(6).setMaxWidth(200);
		this.infoTable.setDefaultRenderer(Integer.class, tableRender);
		this.infoTable.setDefaultRenderer(String.class, tableRender);
		scrollPane_1.setViewportView(infoTable);
		/**
		 * TODO:control panel display
		 */
		// frame.getContentPane().add(splitPane,BorderLayout.SOUTH);
		frame.getContentPane().add(splitPane);
		splitPane.setDividerLocation(200);
		// frame.getContentPane().add(this.panel_10);
		// ======================================================
		panel_10.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_1 = new JSplitPane();
		panel_10.add(splitPane_1, BorderLayout.CENTER);
		this.initVisibal();
	}

	private void initVisibal() {
		this.panel_10.setVisible(false);
	}

	/**
	 * 选择文件之后的回调函数
	 */
	@Override
	public void dealMode(String path, JComponent component) {
		// TODO Auto-generated method stub
		if (component instanceof JMenuItem) {
			if (this.mode == 1) {// 批量处理
				this.batPath.setText(path);
				this.batContentPath.setText(path);
			}
			if (this.mode == 2) {// 信息统计
				this.pdfInfo.setText(path);
				Hello.this.extractToppageMenu.setEnabled(true);
			}
		}
		Properties pro = new Properties(Properties.CONFIG_PATH);
		try {
			pro.open();
			File file = new File(path);
			pro.setProperty(Properties.OPEN_DIR,
					file.isDirectory() ? file.getAbsolutePath() : file
							.getParentFile().getAbsolutePath());
			file = null;
			pro.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	/**
	 * 根据从pdf中提取的得到的目录填充table
	 */
	@Override
	public void fillTable(LinkedList<ContentItem> contents) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		for (ContentItem content : contents) {
			model.addRow(new Object[] { content.getContent(),
					content.getPageNumber(), content.getLevel() });
		}
		this.table.validate();
		this.undoStack.clear();
		this.doStack.clear();
	}

	/**
	 * 确定添加目录时更新目录
	 */
	@Override
	public void refreshContent(PdfContent content) {
		// TODO Auto-generated method stub
		content.setItems(null);
		LinkedList<ContentItem> items = new LinkedList<ContentItem>();
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		int rows = model.getRowCount();
		Vector<?> datas = model.getDataVector();
		String text;
		int pageNumber = 0;
		int level = 0;
		ContentItem item;
		for (int row = 0; row < rows; row++) {
			text = (String) ((Vector<?>) datas.elementAt(row)).elementAt(0);
			try {
				pageNumber = (int) ((Vector<?>) datas.elementAt(row))
						.elementAt(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.table, "页数转换失败！\n所在行："
						+ (row + 1) + "\n解决方案：跳过", "转化错误",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}
			try {
				level = (int) ((Vector<?>) datas.elementAt(row)).elementAt(2);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.table, "目录层级转换失败！\n所在行："
						+ (row + 1) + "\n解决方案：跳过", "转化错误",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				continue;
			}
			item = new ContentItem(pageNumber, text);
			item.setLevel(level);
			items.add(item);
		}
		content.setItems(items);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		this.progressBar.setValue(0);
	}

	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub
		if (value > this.progressBar.getMaximum()
				|| value < this.progressBar.getMinimum()) {
			return;
		}
		this.progressBar.setValue(value);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		this.progressBar.setValue(this.progressBar.getMaximum());
		if (this.mode == 2) {// 信息批量处理
			this.extractInfo.setEnabled(true);
			this.extractMenu.setEnabled(true);
		} else if (this.mode == 1) {
			JOptionPane.showMessageDialog(null, "批量处理成功", "完成",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void setMaxValue(int value) {
		// TODO Auto-generated method stub
		if (value > 0) {
			this.progressBar.setMaximum(value);
		}
	}

	@Override
	public void onSuccess(String type, String fileName) {
		// TODO Auto-generated method stub
		if (type.equals(InfoDialog.RANDOMS)) {
			this.randomsLibs.setText(fileName);
		} else if (type.equals(InfoDialog.FORMULARS)) {
			this.formularLibs.setText(fileName);
		}
		this.frame.setEnabled(true);
	}

	@Override
	public void onCalcle() {
		// TODO Auto-generated method stub
		this.frame.setEnabled(true);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		this.frame.setEnabled(true);
	}

	@Override
	public void callback(String type, Object result) {
		// TODO Auto-generated method stub
		if (type == null)
			return;
		switch (type) {
		case ProgressDealer.INFORMATION:
			DefaultTableModel model = (DefaultTableModel) this.infoTable
					.getModel();
			int count = model.getRowCount();
			for (int i = count - 1; i >= 0; i--) {
				model.removeRow(i);
			}
			JSONArray eles = (JSONArray) result;
			Iterator<JSONObject> p = eles.iterator();
			JSONObject json;
			while (p.hasNext()) {
				json = p.next();
				model.addRow(new Object[] { json.getString(Dealer.NMAE),
						json.getInt(Dealer.COPY) == 0 ? "否" : "是",
						json.getInt(Dealer.IMAGE_NUM),
						json.getInt(Dealer.FORMULAR_NUM),
						json.getInt(Dealer.PAGE_NUM),
						json.getInt(Dealer.BUG_NUM) == 0 ? "否" : "是",
						json.getString(Dealer.NOTE) });
			}
			this.infoTable.validate();
			break;
		case ProgressDealer.EXTRACT_INFORMATION:
			JOptionPane.showMessageDialog(null,result.toString(), "完成",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		default:
			break;
		}
	}
}
