package gui.gui_admin.base_dados;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import db.DataBase;
import gui.DefaultDesign;
import gui.PopUp;
import gui.menu_bar.MenuBar;
import logic.Check;
import logic.MessageLogs;

public class BaseDados {
	
	private static final String MENUADMINSTRING = "menu_admin";
	private static final String ADD = "/img/add.png";
	private static final String WIFI = "/img/wifi.png";
	private static final String DBICON = "/img/db.png";
	private DataBase db;// DATA BASE
	private MessageLogs messageLogs;
	private JTextField baseDadosUser;
	private JTextField baseDadosPassword;
	private JTextField baseDadosUrl;
	private JPanel baseDadosPanel;
	private JLabel baseDadosTexto;
	private JSeparator baseDadosSeparator;
	private JLabel baseDadosTextoUser;
	private JLabel baseDadosTextoPassword;
	private JLabel lblUrl;
	private JButton baseDadosBtnAdicionar;
	private JButton baseDadosBtnhome;
	private JButton baseDadosBtnTest;
	private JButton baseDadosBtnCriarTabelas;
	private JPanel baseDados;
	private MenuBar menuBar;
	private CardLayout cl;
	private PopUp popUp;
	private Check check;

	private String loginUsername;

	public BaseDados(String username, MenuBar menuBar) {
		this.popUp = new PopUp();
		this.check = new Check();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
	}
	
	private void criaBotoesBaseDados() {
		baseDadosBtnAdicionar = new JButton("Atualizar");
		DefaultDesign.styleBotaoSimples(baseDadosBtnAdicionar, ADD);

		baseDadosBtnhome = new JButton("Home");
		DefaultDesign.styleBotaoHome(baseDadosBtnhome);

		baseDadosBtnTest = new JButton("<html>Testar<br> Conex\u00E3o</html>");
		DefaultDesign.styleBotaoSimples(baseDadosBtnTest, WIFI);

		baseDadosBtnCriarTabelas = new JButton("Criar Tabelas");
		DefaultDesign.styleBotaoSimples(baseDadosBtnCriarTabelas, DBICON);
	}
	private GroupLayout putBaseDadosLayout() {
		GroupLayout glBaseDadosPanel = new GroupLayout(baseDadosPanel);
		glBaseDadosPanel.setHorizontalGroup(
			glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glBaseDadosPanel.createSequentialGroup()
					.addGap(42)
					.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glBaseDadosPanel.createSequentialGroup()
							.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(baseDadosBtnhome, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addGroup(glBaseDadosPanel.createSequentialGroup()
									.addGap(2)
									.addComponent(baseDadosSeparator, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
								.addComponent(baseDadosBtnTest, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
							.addGap(131)
							.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(glBaseDadosPanel.createSequentialGroup()
									.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(baseDadosTextoUser, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblUrl)
										.addComponent(baseDadosTextoPassword))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(baseDadosPassword, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
										.addComponent(baseDadosUser, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
										.addComponent(baseDadosUrl, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)))
								.addComponent(baseDadosBtnAdicionar))
							.addGap(135)
							.addComponent(baseDadosBtnCriarTabelas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(94))
						.addGroup(glBaseDadosPanel.createSequentialGroup()
							.addComponent(baseDadosTexto, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(686, Short.MAX_VALUE))))
		);
		glBaseDadosPanel.setVerticalGroup(
			glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glBaseDadosPanel.createSequentialGroup()
					.addGap(85)
					.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(baseDadosBtnCriarTabelas, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addGroup(glBaseDadosPanel.createSequentialGroup()
							.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(baseDadosTextoUser, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(baseDadosUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(baseDadosTextoPassword, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addComponent(baseDadosPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(glBaseDadosPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUrl, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addComponent(baseDadosUrl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(baseDadosBtnAdicionar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(107))
				.addGroup(glBaseDadosPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(baseDadosTexto)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(baseDadosSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(baseDadosBtnTest)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(baseDadosBtnhome, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(136, Short.MAX_VALUE))
		);
		return glBaseDadosPanel;
	}
	public void showBaseDadosMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		baseDados = new JPanel();
		frame.getContentPane().add(baseDados, "name_1323856331283900");
		baseDados.setLayout(new BorderLayout(0, 0));

		baseDadosPanel = new JPanel();
		baseDados.add(baseDadosPanel, BorderLayout.CENTER);

		baseDadosTexto = new JLabel("<html>Base de <br>Dados</html>");
		DefaultDesign.styleLabel28(baseDadosTexto);
		baseDadosSeparator = new JSeparator();
		DefaultDesign.styleSeparator(baseDadosSeparator);

		baseDadosTextoUser = new JLabel("User:");
		baseDadosTextoUser.setFont(new Font("Tahoma", Font.BOLD, 16));

		baseDadosTextoPassword = new JLabel("Password:");
		baseDadosTextoPassword.setFont(new Font("Tahoma", Font.BOLD, 16));

		baseDadosUser = new JTextField();
		baseDadosUser.setText("sinf19a38");
		baseDadosUser.setColumns(10);

		baseDadosPassword = new JTextField();
		baseDadosPassword.setText("UbwJSLsu");
		baseDadosPassword.setColumns(10);

		baseDadosUrl = new JTextField();
		baseDadosUrl.setText("db.fe.up.pt:5432");
		baseDadosUrl.setColumns(10);

		lblUrl = new JLabel("Url:");
		lblUrl.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		criaBotoesBaseDados();
		GroupLayout glBaseDadosPanel = putBaseDadosLayout();
		baseDadosPanel.setLayout(glBaseDadosPanel);
		buttonsBaseDados(frame);
	}
	private void buttonsBaseDados(JFrame frame) {
		baseDadosBtnhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);

			}
		});
		baseDadosBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar(baseDadosUser.getText(), baseDadosPassword.getText(), baseDadosUrl.getText(),
						menuBar.getNomeArmazem());
			}
		});
		baseDadosBtnCriarTabelas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser("baseDados");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.sql", "sql");
				File file = null;
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle("Escolha lugar do script");
				if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					if (!file.getName().toLowerCase().endsWith(".sql"))
						file = new File(file.getAbsolutePath() + ".sql");
				}
				db.createTable(file);
			}
		});

		baseDadosBtnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JLabel aux = new JLabel();
				testDataBase(aux);
				menuBar.setLblStatusDb(aux.getText());
			}
		});
	}
	
	
	public JPanel getBaseDados() {
		return baseDados;
	}

	private boolean confirmData(String user, String password, String url) {
		/* Confirma dados */
		if (check.blankText(user)) {
			popUp.showPopUp("É necessario escolher o user", "User");
			return false;
		} else if (check.blankText(password)) {
			popUp.showPopUp("É necessario escolher o password", "Password");
			return false;
		} else if (check.blankText(url)) {
			popUp.showPopUp("É necessario escolher o url", "Url");
			return false;
		} 
		return true;
	}
	
	private void testDataBase(JLabel lblStatusDb) {
		if (db.connect()) {
			lblStatusDb.setText("ON");
			lblStatusDb.setForeground(Color.GREEN);
			popUp.showPopUpDataBaseSucess();
			db.disconnect();
		}else {
			lblStatusDb.setText("OFF");
			lblStatusDb.setForeground(Color.RED);
			popUp.showPopUpDataBaseError2();
		}
	}
	
	private void adicionar(String user, String password, String url, String nomeArmazem) {
		confirmData(user, password, url);
		String userAntigo =db.getUser();
		String urlAntigo =db.getUrl();
		String passAntigo =db.getPassword();
		String dados= userAntigo+";"+urlAntigo+";"+passAntigo;
		String dadosNovos = user+";"+password+";"+url;
		if(db.checkConnection())
			messageLogs.editaBaseDados(loginUsername+";"+ true+";"+ nomeArmazem,dados, dadosNovos);
		
		db.setUser(user);
		db.setPassword(password);
		db.setUrl(url);
		
		if(db.checkConnection())
			messageLogs.editaBaseDados(loginUsername+";"+true+";"+nomeArmazem, dados, dadosNovos);
		
	}

}
