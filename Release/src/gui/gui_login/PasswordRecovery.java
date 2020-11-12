package gui.gui_login;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import db.DataBase;
import gui.PopUp;
import gui.gui_admin.Admin;
import logic.Check;
import logic.Mail;

public class PasswordRecovery {
	private static final int len = 8;
	private static SecureRandom rnd = new SecureRandom();
	private String key = "";
	private JLabel loading;
	private JLabel loadingText;
	private Check check;
	private PopUp popUp;
	private DataBase db;
	private static final String EMAIL = "/img/email.png";
	private static final String PASS = "/img/lock1.png";

	private JTextField mailField;
	private static final String AB = "0123456789";

	private JTextField keyField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;

	public PasswordRecovery() {
		this.check = new Check();
		this.popUp = new PopUp();
		this.db = DataBase.getInstance();
		keyField = new JTextField();
		passwordField = new JPasswordField();
		passwordField2 = new JPasswordField();
		mailField = new JTextField();


	}

	private void resetBorders() {
		keyField.setBorder(new JTextField().getBorder());
		passwordField.setBorder(new JTextField().getBorder());
		passwordField2.setBorder(new JTextField().getBorder());
	}

	private boolean confirmData() {
		resetBorders();
		if (!check.samePassword(this.key, keyField.getText())) {
			popUp.showPopUp("A key é inválida", "Key");
			keyField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (check.blankText(new String(passwordField.getPassword()))) {
			popUp.showPopUp("É necessario escolher uma password", "Password");
			passwordField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (check.blankText(new String(passwordField2.getPassword()))) {
			popUp.showPopUp("É necessario escolher uma password", "Password");
			passwordField2.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (!check.samePassword(new String(passwordField.getPassword()),
				new String(passwordField2.getPassword()))) {
			popUp.showPopUp("As passwords precisam de ser iguais", "Password");
			passwordField.setBorder(new LineBorder(Color.red, 1));
			passwordField2.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (!check.validPassword(new String(passwordField.getPassword()))) {
			popUp.showPopUp("As passwords precisam de ter um tamanho superior a 6 caracteres", "Password inferior a 6");
			passwordField.setBorder(new LineBorder(Color.red, 1));
			passwordField2.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;
	}

	private String generateRandomKey() {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	private int showPopUpEnviarMail() {
		Object[] options1 = { "Enviar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(EMAIL));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, -100, 0));

		JLabel lblNome = new JLabel("Email");
		panel.add(lblNome);


		panel.add(mailField);
		mailField.setColumns(15);

		return JOptionPane.showOptionDialog(null, panel, "Recuperar password", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	private int showPopUpPasswordRecovery() {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(PASS));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 0, 0));

		loadingText = new JLabel("");
		loading = new JLabel("A enviar...");
		loading.setIcon(new ImageIcon(Admin.class.getResource("/img/loadingEmail.gif")));
		loading.setHorizontalTextPosition(SwingConstants.LEFT);

		panel.add(loadingText);
		panel.add(loading);

		
		JLabel lblUser = new JLabel("Key");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);

		panel.add(keyField);
		keyField.setColumns(10);

		JLabel lblPassword = new JLabel("New Password");
		panel.add(lblPassword);

		panel.add(passwordField);

		JLabel lblRepeatPassword = new JLabel("Repeat Password");
		panel.add(lblRepeatPassword);

		panel.add(passwordField2);

		return JOptionPane.showOptionDialog(null, panel, "Recuperar password", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	private void passwordRecoveryFinal() {
		boolean isFinished = false;
		Mail mail = new Mail();

		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
			  @Override
			  protected Boolean doInBackground() throws Exception {
				  key = generateRandomKey();
				  mail.sendEmail(mailField.getText(), key);
				  return true;
			  }
			  
			  @Override
			  protected void done() {
				  boolean aux=false;
				  try {
					aux=get();
					if(aux) {
						loading.setText("Enviado!   ");
						loading.setIcon(new ImageIcon(Admin.class.getResource("/img/emailEnviado.png")));
						loadingText.setText("");
					}
				} catch (InterruptedException e) {
				} catch (ExecutionException e) {
				}
			  }
			};
	        worker.execute();
		
		while (!isFinished) {
			if (showPopUpPasswordRecovery() != JOptionPane.YES_OPTION)
				break;
			isFinished = confirmData();
		}
		if(isFinished) {
			String pass_encri = BCrypt.hashpw(String.valueOf(passwordField.getPassword()), BCrypt.gensalt());
			String username = db.getUsernameByEmail(mailField.getText());
			if(username != null)
				db.updateUserPassword(username, pass_encri);

		}
	}
	
	public void passwordRecovery() {
		boolean isFinished = false;
		while (!isFinished) {
			int option = showPopUpEnviarMail();
			if (option == JOptionPane.YES_OPTION) {
				if (check.validEmail(mailField.getText())) {
					isFinished=true;
					passwordRecoveryFinal();
				}else {
					mailField.setBorder(new LineBorder(Color.red, 1));
					popUp.showPopUp("Insira um email válido", "Email");

				}
			} else {
				return;
			}
		}
	}
}
