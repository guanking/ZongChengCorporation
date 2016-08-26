package views;

import interfaces.DialogDealer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import net.sf.json.JsonConfig;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pdfTools.FileHelper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class InfoDialog extends JDialog implements ActionListener {
	/**
	 * 公式库
	 */
	public static final String FORMULARS="formulars";
	/**
	 * 乱码库
	 */
	public static final String RANDOMS="randoms";
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DialogDealer dealer;
	private String type;// 类型
	private String fileName;// 库名称
	private JComboBox<String> name;
	private JComboBox<String> typeBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InfoDialog dialog = new InfoDialog(new DialogDealer() {

				@Override
				public void onSuccess(String type, String fileName) {
					// TODO Auto-generated method stub
					System.out.println(type + " " + fileName);
				}

				@Override
				public void onExit() {
					// TODO Auto-generated method stub
					System.out.println("exit");
				}

				@Override
				public void onCalcle() {
					// TODO Auto-generated method stub
					System.out.println("cancle");
				}
			});
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InfoDialog(DialogDealer dealer) {
		this.dealer = dealer;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"F:\\Coder\\traffic\\PDFTest\\images\\info.png"));
		setTitle("\u5E93");
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setBounds(100, 100, 517, 506);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				{
					this.typeBox = new JComboBox<String>();
					this.typeBox.setPreferredSize(new Dimension(200, 21));
					this.typeBox.setModel(new DefaultComboBoxModel<String>(
							new String[] { "\u516C\u5F0F\u5E93",
									"\u4E71\u7801\u5E93" }));
					{
						JLabel label = new JLabel("\u5E93\u7C7B\u578B\uFF1A");
						panel_1.add(label);
					}
					panel_1.add(this.typeBox);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				{
					JLabel label = new JLabel("\u5E93\u540D\u79F0\uFF1A");
					panel_1.add(label);
				}
				{
					this.name = new JComboBox<String>();
					this.name.setPreferredSize(new Dimension(200, 21));
					panel_1.add(name);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane jscrollpanel = new JScrollPane();
				panel.add(jscrollpanel, BorderLayout.CENTER);
				{
					table = new JTable();
					table.setRowHeight(23);
					table.setFont(new Font("宋体", Font.PLAIN, 14));
					table.setModel(new DefaultTableModel(
							new Object[][] { { null }, },
							new String[] { "\u5185\u5BB9" }) {
						Class[] columnTypes = new Class[] { String.class };

						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
					});
					jscrollpanel.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u53D6\u6D88");
				okButton.addActionListener(this);
				okButton.setName("cancle");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u786E\u5B9A");
				cancelButton.addActionListener(this);
				cancelButton.setName("ok");
				buttonPane.add(cancelButton);
			}
		}
		/**
		 * 选择库类型
		 */
		this.typeBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				InfoDialog.this.type = InfoDialog.this.typeBox
						.getSelectedIndex() == 0 ? "formulars" : "randoms";
				File file = new File(InfoDialog.this.type);
				if (!file.exists() || !file.isDirectory()) {
					file.mkdir();
				}
				DefaultComboBoxModel<String> m = (DefaultComboBoxModel<String>) InfoDialog.this.name
						.getModel();
				int count = m.getSize();
				m.removeAllElements();
				for (String ele : file.list()) {
					m.addElement(ele);
				}
				InfoDialog.this.name.validate();
			}
		});
		/**
		 * 选择名字
		 */
		this.name.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (InfoDialog.this.name.getSelectedItem() == null) {
					return;
				}
				InfoDialog.this.fileName = InfoDialog.this.name
						.getSelectedItem().toString();
				File file = new File(InfoDialog.this.type);
				file = new File(file, InfoDialog.this.fileName);
				String items[] = null;
				try {
					String str = FileHelper.readContentFromFile(file);
					items = str.split("\n");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				DefaultTableModel tm = (DefaultTableModel) InfoDialog.this.table
						.getModel();
				int count = tm.getRowCount();
				for (int i = count - 1; i >= 0; i--) {
					tm.removeRow(i);
				}
				for (String ele : items) {
					tm.addRow(new Object[] { ele });
				}
				InfoDialog.this.table.validate();
			}
		});
		this.typeBox.setSelectedIndex(1);
		setVisible(true);
	}

	@Override
	public void dispose() {
		if (this.dealer != null) {
			this.dealer.onExit();
			this.dealer = null;
		}
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		switch (button.getName()) {
		case "cancle":
			if (this.dealer != null) {
				this.dealer.onCalcle();
			}
			this.dispose();
			break;
		case "ok":
			if (this.dealer != null) {
				this.dealer.onSuccess(this.type, this.fileName);
			}
			this.dispose();
			break;
		}
	}
}
