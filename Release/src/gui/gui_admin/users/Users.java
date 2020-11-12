package gui.gui_admin.users;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.security.crypto.bcrypt.BCrypt;
import db.DataBase;
import gui.AutoCompletion;
import gui.DefaultDesign;
import gui.PopUp;
import gui.gui_admin.Admin;
import gui.menu_bar.MenuBar;
import logic.Check;
import logic.MessageLogs;

public class Users {
	
	private JPanel usersPanel;
	private JButton usersBtnAdicionar;
	private JButton usersBtnRemover;
	private JButton usersBtnEditar;
	private JButton usersBtnRefresh;
	private JButton usersBtnHome;

	private TableRowSorter<DefaultTableModel> sorterUser;
	private JTextField userSearch;
	private JPanel users;

	
	private DataBase db;// DATA BASE
	private MessageLogs messageLogs;
	private CardLayout cl;
	private static final String USER = "/img/user.png";
	private PopUp popUp;
	private Check check;
	
	private String loginUsername;
	private DefaultTableModel modelUser;
	private JTable usersTable;
	/* POP UP */
	private JComboBox<String> comboBox;
	private JTextField userField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JCheckBox chckbxAdmin;
	private MenuBar menuBar;

	private static final String REFRESHSTRING = "Refresh";
	private static final String EDITARSTRING = "Editar";
	private static final String REMOVERSTRING = "Remover";
	private static final String ADD = "/img/add.png";
	private static final String REMOVE = "/img/remove.png";
	private static final String REFRESH = "/img/refresh.png";
	private static final String EDIT = "/img/edit1.png";
	private static final String MENUADMINSTRING = "menu_admin";

	/* CONSTRUCTOR */
	public Users(String username, MenuBar menuBar) {
		this.popUp = new PopUp();
		this.check = new Check();
		this.menuBar = menuBar;
		comboBox = new JComboBox<>();
		userField = new JTextField();
		emailField = new JTextField();
		passwordField = new JPasswordField();
		passwordField2 = new JPasswordField();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		criaTabela();
	}

	private void criaTabela() {
		modelUser = new DefaultTableModel(new Object[][] {}, new String[] { "NIF", "Username", "Admin" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 60845133227382893L;

			boolean[] columnEditables = new boolean[] { false, false, false };
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, Boolean.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		usersTable = new JTable(); 
		DefaultDesign.styleTabela(usersTable,modelUser);
	}
	private void criaBotoesUser() {
		usersBtnAdicionar = new JButton("Adicionar");
		DefaultDesign.styleBotaoSimples(usersBtnAdicionar, ADD);
		
		usersBtnRemover =  new JButton(REMOVERSTRING);
		DefaultDesign.styleBotaoSimples(usersBtnRemover, REMOVE);
		
		usersBtnEditar = new JButton(EDITARSTRING);
		DefaultDesign.styleBotaoSimples(usersBtnEditar, EDIT);
		
		usersBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(usersBtnRefresh, REFRESH);
		
		usersBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(usersBtnHome );
	}
	private void criaUserSearch() {
		sorterUser = new TableRowSorter<>(modelUser);
		usersTable.setRowSorter(sorterUser);
		userSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				userSearch.setText("");
			}
		});
		userSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(userSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(userSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(userSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterUser.setRowFilter(null);
				} else {
					sorterUser.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});
	}
	private GroupLayout putUserLayout(JLabel usersTexto, JScrollPane usersScrollPane, JSeparator usersSeparator) {
		GroupLayout glUsersPanel = new GroupLayout(usersPanel);
		glUsersPanel.setHorizontalGroup(glUsersPanel.createParallelGroup(Alignment.TRAILING).addGroup(glUsersPanel
				.createSequentialGroup().addGap(37)
				.addGroup(glUsersPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(usersBtnHome, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(usersTexto, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
						.addComponent(usersSeparator, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE).addComponent(
								usersBtnRefresh, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGap(51)
				.addGroup(glUsersPanel.createParallelGroup(Alignment.LEADING).addGroup(glUsersPanel
						.createSequentialGroup().addComponent(usersBtnAdicionar).addGap(18)
						.addComponent(usersBtnEditar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(usersBtnRemover, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addGap(74).addComponent(userSearch, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
						.addComponent(usersScrollPane, GroupLayout.PREFERRED_SIZE, 632, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(16, Short.MAX_VALUE)));
		glUsersPanel.setVerticalGroup(glUsersPanel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				glUsersPanel.createSequentialGroup().addGroup(glUsersPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(glUsersPanel.createParallelGroup(Alignment.TRAILING).addGroup(glUsersPanel
								.createSequentialGroup().addContainerGap(19, Short.MAX_VALUE)
								.addGroup(glUsersPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(usersBtnAdicionar, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(usersBtnEditar, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(usersBtnRemover, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE))
								.addGap(4))
								.addGroup(Alignment.LEADING,
										glUsersPanel.createSequentialGroup().addContainerGap().addComponent(usersTexto,
												GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
						.addGroup(glUsersPanel.createSequentialGroup().addContainerGap()
								.addComponent(userSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)))
						.addGroup(
								glUsersPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(glUsersPanel.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(usersSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(46)
												.addComponent(usersBtnRefresh, GroupLayout.PREFERRED_SIZE, 30,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(usersBtnHome)
												.addContainerGap(124, Short.MAX_VALUE))
										.addGroup(Alignment.TRAILING,
												glUsersPanel.createSequentialGroup().addComponent(usersScrollPane,
														GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
														.addContainerGap()))));
		return glUsersPanel;
	}
	public void showUserMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		users = new JPanel();
		frame.getContentPane().add(users, "name_1243457881841100");
		users.setLayout(new BorderLayout(0, 0));
		usersPanel = new JPanel();
		users.add(usersPanel, BorderLayout.CENTER);
		JScrollPane usersScrollPane = new JScrollPane();
		usersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel usersTexto = new JLabel("Users");
		DefaultDesign.styleLabel(usersTexto);
		JSeparator usersSeparator = new JSeparator();
		DefaultDesign.styleSeparator(usersSeparator);
		
		criaBotoesUser();
		
		userSearch = new JTextField();
		DefaultDesign.styleSearch(userSearch);
		
		GroupLayout glUsersPanel = putUserLayout(usersTexto, usersScrollPane, usersSeparator);
		usersScrollPane.setViewportView(usersTable);
		usersPanel.setLayout(glUsersPanel);
		criaUserSearch();
		db.nifusernameadminLogin(modelUser);
		buttonsUser(frame);
	}
	private void buttonsUser(JFrame frame) {
		usersBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);


			}
		});
		usersBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUser(menuBar.getNomeArmazem());

			}
		});
		usersBtnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUser( menuBar.getNomeArmazem());
			}
		});
		usersBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		usersBtnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeUser(menuBar.getNomeArmazem());

			}
		});
		usersBtnRemover.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeUser(menuBar.getNomeArmazem());

			}
		});

	}

	
	
	public JPanel getUsers() {
		return users;
	}

	private void resetBorders() {
		comboBox.setBorder(new JTextField().getBorder());
		userField.setBorder(new JTextField().getBorder());
		emailField.setBorder(new JTextField().getBorder());
		passwordField.setBorder(new JTextField().getBorder());
		passwordField2.setBorder(new JTextField().getBorder());
		chckbxAdmin.setBorder(new JTextField().getBorder());
	}

	/*
	 * choice = 0 -> adicionar choice=1-> editar
	 */
	private boolean confirmData(int choice) {
		resetBorders();
		/* Confirma dados */
		if (choice == 0 && !check.validSelectedItem(comboBox.getSelectedItem())) {
			popUp.showPopUp("É necessario escolher o nif associado ao user", "Nif");
			comboBox.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (check.blankText(userField.getText())) {
			popUp.showPopUp("É necessario escolher um username", "Username");
			userField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if(!check.validEmail(emailField.getText())) {
			popUp.showPopUp("É necessario escolher um email valido", "Email");
			emailField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}else if (check.blankText(new String(passwordField.getPassword()))) {
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

	/* User POP UP Adicionar */
	private int showUserPopUpAdicionar(Object nif, String userName, String email) {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(USER));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2, 0, 0));

		JLabel lblNome = new JLabel("NIF");
		panel.add(lblNome);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		comboBox.setModel(model);
		panel.add(comboBox);
		ArrayList<String> nifs = db.getAllNifLogin();
		for (int i = 0; i < nifs.size(); i++) {
			model.addElement(nifs.get(i));

		}
		comboBox.setSelectedItem(nif);
		AutoCompletion.enable(comboBox);
		/* adicionar aqui com a base de dados os NIFS */

		JLabel lblUser = new JLabel("User");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);

		panel.add(userField);
		userField.setColumns(10);
		userField.setText(userName);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblEmail);

		panel.add(emailField);
		emailField.setColumns(10);
		emailField.setText(email);

		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword);

		panel.add(passwordField);

		JLabel lblRepeatPassword = new JLabel("Repeat Password");
		panel.add(lblRepeatPassword);

		panel.add(passwordField2);

		JLabel lblAdmin = new JLabel("Admin");
		panel.add(lblAdmin);

		chckbxAdmin = new JCheckBox("");
		panel.add(chckbxAdmin);

		return JOptionPane.showOptionDialog(null, panel, "Adicionar user", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	/* User POP UP Editar */
	private int showUserPopUpEditar(Object nome, String userName, boolean admin) {
		Object[] options1 = { "Ok", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(USER));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblUser = new JLabel("User");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);

		panel.add(userField);
		userField.setColumns(10);
		userField.setText(userName);

		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword);

		panel.add(passwordField);
		passwordField.setText(null);

		JLabel lblRepeatPassword = new JLabel("Repeat Password");
		panel.add(lblRepeatPassword);

		passwordField2.setText(null);
		panel.add(passwordField2);

		JLabel lblAdmin = new JLabel("Admin");
		panel.add(lblAdmin);

		chckbxAdmin = new JCheckBox("");
		panel.add(chckbxAdmin);
		chckbxAdmin.setSelected(admin);

		return JOptionPane.showOptionDialog(null, panel, "Editar user com nif = " + nome.toString(),
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	private void removeUser( String nomeArmazem) {
		int[] selectedRows = usersTable.getSelectedRows();

		if (selectedRows.length <= 0) {
			popUp.showPopUp("Precisa de selecionar pelo menos 1 linha", "Erro ao eliminar");
			return;
		}

		int option = popUp.showPopUpConfirmation(
				"Tem a certeza que deseja apagar " + selectedRows.length + " utilizadores?", "Eliminar utilizadores");
		if (option == JOptionPane.YES_OPTION) {
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				String user = modelUser.getValueAt(usersTable.convertRowIndexToModel(selectedRows[i]), 1).toString();
				if (db.removeUserLogin(user)) {
					messageLogs.removeUser(loginUsername+";"+true+";"+nomeArmazem, user);
					modelUser.removeRow(usersTable.convertRowIndexToModel(selectedRows[i]));
				} else {
					popUp.showPopUpErroEliminar();
					break;
				}

			}
		}

	}

	private void refresh() {
		int rowCount = modelUser.getRowCount();
		if (!db.connect()) {
			popUp.showPopUpDataBaseError();
		} else {
			db.disconnect();
			for (int i = rowCount - 1; i >= 0; i--) {
				modelUser.removeRow(i);
			}
			db.nifusernameadminLogin(modelUser);
		}
	}

	private void editarUser(String nomeArmazem) {
		boolean isFinished = false;
		int[] indexOfRow = usersTable.getSelectedRows();
		if (check.multipleSelection(indexOfRow)) {
			popUp.showPopUp("Selecione apenas 1 linha", "Erro ao selecionar");
			return;
		}
		indexOfRow[0] = usersTable.convertRowIndexToModel(indexOfRow[0]);// converte para o indice correto (quando
																			// ordena)
		while (!isFinished) {
			int option = showUserPopUpEditar(modelUser.getValueAt(indexOfRow[0], 0).toString(),
					modelUser.getValueAt(indexOfRow[0], 1).toString(), 
					(boolean) modelUser.getValueAt(indexOfRow[0], 2));
			if (option == JOptionPane.YES_OPTION) {
				isFinished = confirmData(1);
			} else {
				resetBorders();
				break;
			}

		}
		/* editar na base de dados */
		if (isFinished) {
			String passEncript = BCrypt.hashpw(String.valueOf(passwordField.getPassword()), BCrypt.gensalt());
			String oldUser = modelUser.getValueAt(indexOfRow[0], 1).toString();
			String user = userField.getText();
			if (db.updateUserLogin(oldUser+";"+user, passEncript,
					chckbxAdmin.isSelected())) {
				messageLogs.editaUser(loginUsername+";"+true+";"+nomeArmazem, oldUser, user);
				popUp.showPopUpEditarSucesso();
				modelUser.setValueAt(userField.getText(), indexOfRow[0], 1);
				modelUser.setValueAt(chckbxAdmin.isSelected(), indexOfRow[0], 2);
			} else {
				popUp.showPopUpErroEditar();
			}

		}

	}

	private void adicionarUser( String nomeArmazem) {
		boolean itsFinished = false;
		Object nif = null;
		String username = null;
		String email = null;

		while (!itsFinished) {
			int result = showUserPopUpAdicionar(nif, username, email);// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
				itsFinished = confirmData(0);
				nif = comboBox.getSelectedItem();
				username = userField.getText();// coloca o nome que tinha escrito, antes de dar erro
				email= emailField.getText();
			} else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
				resetBorders();
				break;
			}
		}
		if (itsFinished) {
			String passEncript = BCrypt.hashpw(String.valueOf(passwordField.getPassword()), BCrypt.gensalt());
			if (db.addUserLogin(comboBox.getSelectedItem().toString()+";"+userField.getText()+";"+ emailField.getText(), passEncript,
					chckbxAdmin.isSelected())) {
				messageLogs.adicionaUser(loginUsername+";"+true+";"+nomeArmazem, comboBox.getSelectedItem().toString(), userField.getText());
				popUp.showPopUpAdicionadoSucesso();
				modelUser.addRow(
						new Object[] { comboBox.getSelectedItem(), userField.getText(), chckbxAdmin.isSelected() });
			} else {
				popUp.showPopUpErroAdicionar();
			}

		}

	}

	
	
}
