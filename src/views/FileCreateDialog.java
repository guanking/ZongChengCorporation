package views;

import interfaces.DialogDealer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FileCreateDialog extends JDialog implements ActionListener {
	private DialogDealer dealer;
	private final JPanel contentPanel = new JPanel();
	private JTextField libName;
	private JComboBox libType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FileCreateDialog dialog = new FileCreateDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FileCreateDialog(DialogDealer dealer) {
		setTitle("\u521B\u5EFA\u5E93");
		this.dealer = dealer;
		setBounds(100, 100, 409, 191);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setVgap(20);
			flowLayout.setHgap(30);
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				{
					JLabel label = new JLabel("\u5E93\u7C7B\u578B\uFF1A");
					panel_1.add(label);
					label.setFont(new Font("宋体", Font.BOLD, 14));
				}
				{
					libType = new JComboBox();
					panel_1.add(libType);
					libType.setFont(new Font("宋体", Font.PLAIN, 14));
					libType.setModel(new DefaultComboBoxModel(new String[] {
							"\u4E71\u7801\u5E93", "\u516C\u5F0F\u5E93",
							"\u6CE8\u91CA\u5E93" }));
				}
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setVgap(20);
			flowLayout.setHgap(30);
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(panel);
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				{
					JLabel lblNewLabel = new JLabel("\u5E93\u540D\u79F0\uFF1A");
					panel_1.add(lblNewLabel);
					lblNewLabel.setFont(new Font("宋体", Font.BOLD, 14));
				}
				{
					libName = new JTextField();
					panel_1.add(libName);
					libName.setFont(new Font("宋体", Font.PLAIN, 14));
					libName.setColumns(20);
				}
				{
					JLabel lbltxt = new JLabel(".txt");
					panel_1.add(lbltxt);
					lbltxt.setFont(new Font("宋体", Font.PLAIN, 14));
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.setName("confirm");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.setName("cancle");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo((JDialog)this.dealer);
		this.setVisible(true);
	}

	@Override
	public void dispose() {
		if (FileCreateDialog.this.dealer != null) {
			FileCreateDialog.this.dealer.onExit();
			FileCreateDialog.this.dealer = null;
		}
		super.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		switch (button.getName()) {
		/**
		 * confirm button
		 */
		case "confirm":
			String type = null;
			switch (this.libType.getSelectedIndex()) {
			case 0:
				type = InfoDialog.RANDOMS;
				break;
			case 1:
				type = InfoDialog.FORMULARS;
				break;
			case 2:
				type = InfoDialog.ANNOTATIONS;
				break;
			}
			String fileName = this.libName.getText().trim();
			if (fileName.length() == 0) {
				if (JOptionPane.OK_OPTION == JOptionPane
						.showConfirmDialog(this, "库名称不能为空,是否继续？", "错误提醒",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.ERROR_MESSAGE)) {
					return;
				} else {
					break;
				}
			}
			if (FileCreateDialog.this.dealer != null) {
				FileCreateDialog.this.dealer.onSuccess(type, fileName);
			}
			break;
		/**
		 * cancle button
		 */
		case "cancle":
			if (FileCreateDialog.this.dealer != null) {
				FileCreateDialog.this.dealer.onCalcle();
			}
			break;
		}
		this.dispose();
	}
}
