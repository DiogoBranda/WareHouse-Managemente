package gui.gui_user.enviar_produto;

import javax.swing.JPanel;

import javax.swing.JComboBox;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import db.DataBase;
import gui.AutoCompletion;
import gui.PopUp;
import gui.gui_user.UserDesign;
import logic.Check;
import logic.MessageLogs;

import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EnviarProduto {
	private static final String SEND = "/img/send.png";

	private JComboBox<String> comboBox;
	private JDateChooser dateChooser;
	private DataBase db;
	private MessageLogs messageLogs;
	private PopUp popUp;
	private Check check;
	private JTextField destinoField;
	private String loginUsername;

	public EnviarProduto(String username) {
		this.popUp = new PopUp();
		this.check = new Check();
		db = DataBase.getInstance();
		comboBox = new JComboBox<>();
		dateChooser = new JDateChooser();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;

	}

	private void resetBorders() {
		dateChooser.setBorder(new JTextField().getBorder());
		comboBox.setBorder(new JTextField().getBorder());

	}

	private boolean confirmData() {
		resetBorders();
		/* Confirma dados */
		try {
			Date pega = dateChooser.getDate();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			formato.format(pega);
		} catch (NullPointerException e) {
			popUp.showPopUp("É necessario escolher uma data válida", "Data");
			dateChooser.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		if (check.blankText(comboBox.getSelectedItem().toString())) {
			popUp.showPopUp("É necessario escolher uma SKU valida", "sku");
			comboBox.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		if (check.blankText(destinoField.getText())) {
			popUp.showPopUp("É necessario escolher Destino valido", "Destino");
			destinoField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}

		return true;

	}

	private void putDesignPopUp(JPanel panel) {
		JLabel lblSKU = new JLabel("SKU");
		lblSKU.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblSKU);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		comboBox.setModel(model);
		panel.add(comboBox);

		DefaultTableModel skus = new DefaultTableModel(new Object[][] {}, new String[] { "skus" });
		db.getAllSkus(skus);
		for (int i = 0; i < skus.getRowCount(); i++) {
			model.addElement(skus.getValueAt(i, 0).toString());
		}
		AutoCompletion.enable(comboBox);

		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblData);

		dateChooser.setMaxSelectableDate(new Date());
		Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		dateChooser.setMinSelectableDate(date);
		dateChooser.setDateFormatString("dd-MM-yyyy");
		Date date22 = new Date();
		dateChooser.setDate(date22);
		panel.add(dateChooser);
		
		JLabel lblLocal = new JLabel("Destino");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblLocal);
		
		destinoField = new JTextField();
		panel.add(destinoField);
	}
	private int showEnviarPopUp() {
		Object[] options1 = { "Enviar", "Sair" };
		ImageIcon icon = new ImageIcon(UserDesign.class.getResource(SEND));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2, 0, 0));

		putDesignPopUp(panel);
		
		return JOptionPane.showOptionDialog(null, panel, "Enviar Produto", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);

	}
	
	public void enviar(String nomeArmazem) {
		boolean itsFinished = false;
		while (!itsFinished) {
			int result = showEnviarPopUp();
			if (result == JOptionPane.YES_OPTION) {
				itsFinished = confirmData();
			} else {
				break;
			}
		}
		if (itsFinished) {
			java.sql.Timestamp timestamp = new java.sql.Timestamp(dateChooser.getDate().getTime());
			
			String data = new SimpleDateFormat("MM/dd/yyyy").format(timestamp);
			String sku=comboBox.getSelectedItem().toString();
			String destino = destinoField.getText();
			db.enviarProduto(sku, data,destino);
			messageLogs.enviaProduto(loginUsername+";"+false+";"+nomeArmazem,sku, data);
		}

	}

	
}
