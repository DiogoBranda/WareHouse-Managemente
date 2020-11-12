package gui.menu_bar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import db.DataBase;
import gui.PopUp;
import gui.gui_admin.Admin;
import logic.Check;
import logic.MessageLogs;

public class Armazem {
	
	private DataBase db;// DATA BASE
	private MessageLogs messageLogs;

	private static final String WAREHOUSE = "/img/warehouse.png";
	private PopUp popUp;
	private Check check;

	/* POP UP */
	private JTextField armazemField;
	private JTextField localizacaoField;
	private String loginUsername;

	public Armazem(String username) {
		this.popUp = new PopUp();
		this.check = new Check();
		armazemField = new JTextField();
		localizacaoField = new JTextField();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
	}

	private boolean confirmData() {
		armazemField.setBorder(new JTextField().getBorder());
		localizacaoField.setBorder(new JTextField().getBorder());
		/* Confirma dados */
		if (check.blankText(armazemField.getText())) {
			popUp.showPopUp("É necessario escolher um nome", "Nome");
			armazemField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (!check.textValidWithoutNumbers(localizacaoField.getText())) {
			popUp.showPopUp("É necessario escolher uma localização válida", "Localização");
			localizacaoField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;

	}


	/* User POP UP Editar */
	private int showArmazemPopUpEditar(String nome, String localization) {
		Object[] options1 = { "Ok", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(WAREHOUSE));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblUser = new JLabel("Nome");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);

		armazemField = new JTextField(nome);
		armazemField.setColumns(10);
		panel.add(armazemField);

		JLabel lblPassword = new JLabel("Localização");
		panel.add(lblPassword);

		localizacaoField = new JTextField(localization);
		localizacaoField.setColumns(10);
		panel.add(localizacaoField);

		return JOptionPane.showOptionDialog(null, panel, "Editar " + nome, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}


	public String[] editarArmazem(String armazem, String localizacao) {
		boolean isFinished = false;
		String[] result= new String[2];

		while (!isFinished) {
			int option = showArmazemPopUpEditar(armazem, localizacao);
			if (option == JOptionPane.YES_OPTION) {
				isFinished = confirmData();
			} else {
				break;
			}

		}
		if (isFinished) {
			result[0] = armazemField.getText();
			result[1]= localizacaoField.getText();
			String dados = armazem + ";" + localizacao + ";" + result[0] + ";" + result[1];
			db.updateArmazem(armazem, armazemField.getText(), localizacaoField.getText());
			messageLogs.mudarArmazem(loginUsername+";"+true+";"+result[0], dados);
		}
		return result;

	}

}
