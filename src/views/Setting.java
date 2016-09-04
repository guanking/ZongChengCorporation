package views;

import interfaces.DialogDealer;
import interfaces.ProgressDealer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.BevelBorder;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import pdfTools.setting.Setter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Toolkit;

public class Setting extends JDialog implements ActionListener, DialogDealer {

	private final JPanel contentPanel = new JPanel();
	private JTextField imgWidth;
	private JTextField imgHeight;
	private JCheckBox pageNum;
	private JCheckBox bugNum;
	private JCheckBox formularNum;
	private JCheckBox annotationNum;
	private JCheckBox imgNum;
	private JCheckBox isBug;
	private Setter setter;
	private JComboBox<?> imgType;
	private JCheckBox needCopy;
	private String libOper;
	private DialogDealer dealer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Setting dialog = new Setting(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Setting(DialogDealer dealer) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\setting.png"));
		this.dealer = dealer;
		setTitle("\u8BBE\u7F6E");
		setBounds(100, 100, 476, 524);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(3, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
					null, null));
			panel.setToolTipText("\u5E93\u7BA1\u7406");
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					JLabel label = new JLabel("\u5E93\u7BA1\u7406\uFF1A");
					label.setFont(new Font("宋体", Font.BOLD, 14));
					panel_1.add(label);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				flowLayout.setVgap(20);
				flowLayout.setHgap(30);
				panel.add(panel_1, BorderLayout.CENTER);
				{
					JButton add = new JButton("\u6DFB\u52A0");
					add.setName("add");
					add.addActionListener(this);
					add.setFont(new Font("宋体", Font.PLAIN, 14));
					panel_1.add(add);
				}
				{
					JButton delete = new JButton("\u5220\u9664");
					delete.setName("delete");
					delete.addActionListener(this);
					delete.setFont(new Font("宋体", Font.PLAIN, 14));
					panel_1.add(delete);
				}
				{
					JButton change = new JButton("\u4FEE\u6539");
					change.setName("change");
					change.addActionListener(this);
					change.setFont(new Font("宋体", Font.PLAIN, 14));
					panel_1.add(change);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
					null, null));
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					JLabel lblNewLabel = new JLabel(
							"\u4FE1\u606F\u7EDF\u8BA1\uFF1A");
					lblNewLabel.setFont(new Font("宋体", Font.BOLD, 14));
					panel_1.add(lblNewLabel);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.CENTER);
				panel_1.setLayout(new GridLayout(4, 0, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
					flowLayout.setHgap(20);
					flowLayout.setAlignment(FlowLayout.LEFT);
					panel_1.add(panel_2);
					{
						JLabel label = new JLabel(
								"\u7EDF\u8BA1\u9879\u76EE\uFF1A");
						label.setFont(new Font("宋体", Font.PLAIN, 14));
						panel_2.add(label);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
					flowLayout.setVgap(0);
					flowLayout.setHgap(0);
					panel_1.add(panel_2);
					{
						pageNum = new JCheckBox("\u9875\u6570");
						panel_2.add(pageNum);
					}
					{
						bugNum = new JCheckBox("\u4E71\u7801\u6570");
						panel_2.add(bugNum);
					}
					{
						formularNum = new JCheckBox("\u516C\u5F0F\u6570");
						panel_2.add(formularNum);
					}
					{
						annotationNum = new JCheckBox("\u6CE8\u91CA\u6570");
						panel_2.add(annotationNum);
					}
					{
						imgNum = new JCheckBox("\u56FE\u7247\u6570");
						panel_2.add(imgNum);
					}
					{
						isBug = new JCheckBox("\u662F\u5426\u4E71\u7801");
						panel_2.add(isBug);
					}
					{
						needCopy = new JCheckBox("\u590D\u5236");
						panel_2.add(needCopy);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
					flowLayout.setAlignment(FlowLayout.LEFT);
					flowLayout.setHgap(20);
					panel_1.add(panel_2);
					{
						JLabel lblNewLabel_2 = new JLabel(
								"\u56FE\u7247\u5927\u5C0F\uFF1A");
						lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 14));
						panel_2.add(lblNewLabel_2);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
					flowLayout.setHgap(3);
					panel_1.add(panel_2);
					{
						JLabel label = new JLabel("\u957F\uFF1A");
						panel_2.add(label);
					}
					{
						imgWidth = new JTextField();
						imgWidth.setHorizontalAlignment(SwingConstants.CENTER);
						panel_2.add(imgWidth);
						imgWidth.setColumns(6);
					}
					{
						JLabel label = new JLabel("\u50CF\u7D20");
						panel_2.add(label);
					}
					{
						JLabel label = new JLabel("        ");
						panel_2.add(label);
					}
					{
						JLabel label = new JLabel("\u5BBD\uFF1A");
						panel_2.add(label);
					}
					{
						imgHeight = new JTextField();
						imgHeight.setHorizontalAlignment(SwingConstants.CENTER);
						panel_2.add(imgHeight);
						imgHeight.setColumns(6);
					}
					{
						JLabel label = new JLabel("\u50CF\u7D20");
						panel_2.add(label);
					}
				}
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
					null, null));
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1, BorderLayout.NORTH);
				{
					JLabel lblNewLabel_1 = new JLabel(
							"\u9996\u9875\u63D0\u53D6\uFF1A");
					lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 14));
					panel_1.add(lblNewLabel_1);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setVgap(10);
				flowLayout.setHgap(20);
				flowLayout.setAlignment(FlowLayout.LEFT);
				panel.add(panel_1, BorderLayout.CENTER);
				{
					JLabel label = new JLabel("\u4FDD\u5B58\u683C\u5F0F\uFF1A");
					label.setFont(new Font("宋体", Font.PLAIN, 14));
					panel_1.add(label);
				}
				{
					imgType = new JComboBox();
					imgType.setModel(new DefaultComboBoxModel(new String[] {
							"JPEG", "PNG", "JPG" }));
					panel_1.add(imgType);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				/**
				 * confirm button to make the configuration validate
				 */
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (!Setting.this.refreshParams()) {
								return;
							}
							Setting.this.setter.finish();
							if (Setting.this.dealer != null) {
								Setting.this.dealer.onSuccess(
										DialogDealer.SETTING, null);
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							JOptionPane.showConfirmDialog(Setting.this,
									"配置文件写入失败，错误原因：\n" + e1.getMessage(), "错误",
									JOptionPane.CANCEL_OPTION,
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
							return;
						}
						Setting.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Setting.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		if (this.dealer instanceof Hello) {
			this.setLocationRelativeTo(((Hello) this.dealer).getFrame());
		}
		this.setVisible(true);
		this.initParameters();
		this.setResizable(false);
	}

	/**
	 * update the configuration
	 */
	protected boolean refreshParams() {
		// TODO Auto-generated method stub
		this.setter.setNeedPageNum(this.pageNum.isSelected());
		this.setter.setNeedBugNum(this.bugNum.isSelected());
		this.setter.setNeedAnnotaionNum(this.annotationNum.isSelected());
		this.setter.setNeedImageNum(this.imgNum.isSelected());
		this.setter.setNeedFormularNum(this.formularNum.isSelected());
		this.setter.setNeedCopy(this.needCopy.isSelected());
		this.setter.setNeedHaveBug(this.isBug.isSelected());
		String text = this.imgHeight.getText().trim();
		int value;
		try {
			value = Integer.parseInt(text);
			if (value < 0) {
				JOptionPane.showConfirmDialog(Setting.this, "图片高度设置不能为负数",
						"警告", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			this.setter.setLegalHeight(value);
		} catch (NumberFormatException e) {
			JOptionPane.showConfirmDialog(Setting.this, "图片高度设置必须为正整数数字", "警告",
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		text = this.imgWidth.getText().trim();
		try {
			value = Integer.parseInt(text);
			if (value < 0) {
				JOptionPane.showConfirmDialog(Setting.this, "图片宽度设置不能为负数",
						"警告", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			this.setter.setLegalWidth(value);
		} catch (NumberFormatException e) {
			JOptionPane.showConfirmDialog(Setting.this, "图片宽度设置必须为正整数数字", "警告",
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		this.setter.setImageType((String) this.imgType.getSelectedItem());
		return true;
	}

	/**
	 * initiate the context in the dialog setting
	 */
	private void initParameters() {
		// TODO Auto-generated method stub
		try {
			this.setter = new Setter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showConfirmDialog(Setting.this, "配置文件出错，请重新安装", "错误",
					JOptionPane.OK_CANCEL_OPTION);
		}
		this.pageNum.setSelected(this.setter.isNeedPageNum());
		this.bugNum.setSelected(this.setter.isNeedBugNum());
		this.annotationNum.setSelected(this.setter.isNeedAnnotaionNum());
		this.imgNum.setSelected(this.setter.isNeedImageNum());
		this.formularNum.setSelected(this.setter.isNeedFormularNum());
		this.needCopy.setSelected(this.setter.isNeedCopy());
		this.bugNum.setSelected(this.setter.isNeedBugNum());
		this.isBug.setSelected(this.setter.isNeedHaveBug());
		this.imgHeight.setText(this.setter.getLegalHeight() + "");
		this.imgWidth.setText(this.setter.getLegalWidth() + "");
		this.imgType.setSelectedItem(this.setter.getImageType());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (this.dealer != null) {
			this.dealer.onExit();
		}
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (((JButton) e.getSource()).getName()) {
		case "add":
			this.libOper = "add";
			(new FileCreateDialog(this)).setVisible(true);
			this.setEnabled(false);
			return;
		case "delete":
			this.libOper = "delete";
			break;
		case "change":
			break;
		default:
			System.out.println("error select "
					+ ((JButton) e.getSource()).getName());
			return;
		}
		this.libOper = ((JButton) e.getSource()).getName();
		InfoDialog dialog = new InfoDialog(this);
		this.setEnabled(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void onSuccess(String type, String fileName) {
		// TODO Auto-generated method stub
		this.setEnabled(true);
		File file = new File(type);
		if (file == null || !file.isDirectory()) {
			file.getParentFile().mkdirs();
			file.mkdir();
		}
		file = new File(file, fileName.endsWith(".txt") ? fileName : fileName
				+ ".txt");
		if (this.libOper.equalsIgnoreCase("delete")) {
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
					Setting.this, "确定删除（将不可恢复）？", "删除警告",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) {
				if (file.delete() == true) {
					JOptionPane.showConfirmDialog(Setting.this, "删除成功", "删除提示",
							JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(Setting.this, "删除成功",
							"用户权限不够，删除失败，请手动删除", JOptionPane.OK_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			return;
		}
		if (file == null || !file.isFile()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(Setting.this,
						"文件创建失败，对当前文件操作权限不够");
				e.printStackTrace();
				return;
			}
		}
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd /c \"" + file.getAbsolutePath() + "\"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCalcle() {
		// TODO Auto-generated method stub
		this.setEnabled(true);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		this.setEnabled(true);
	}
}
