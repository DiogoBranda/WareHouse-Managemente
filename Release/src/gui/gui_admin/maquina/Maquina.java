package gui.gui_admin.maquina;

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

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import db.DataBase;
import gui.DefaultDesign;
import gui.PopUp;
import gui.gui_admin.Admin;
import gui.menu_bar.MenuBar;
import logic.Check;
import logic.MessageLogs;

public class Maquina {

	private static final String MAQUINA = "/img/machine.png";
	private PopUp popUp;
	private Check check;
	private DataBase db;
	private MessageLogs messageLogs;
	private DefaultTableModel modelMaquina;
	private JTable maquinaTable;
	
	private String loginUsername;

	private JPanel maquinaPanel ;
	private JScrollPane maquinaScrollPane;
	private JSeparator maquinaSeparator;
	private JLabel maquinaTexto;
	private JButton maquinaBtnRefresh;
	private JButton maquinaBtnHome;
	private JButton maquinaBtnAdicionar;
	private JButton maquinaBtnEditar;
	private JButton maquinaBtnRemover;
	private TableRowSorter<DefaultTableModel> sorterMaquina;
	private JTextField maquinaSearch;
	private static final String REFRESHSTRING = "Refresh";
	private static final String EDITARSTRING = "Editar";
	private static final String REMOVERSTRING = "Remover";
	private static final String ADD = "/img/add.png";
	private static final String REMOVE = "/img/remove.png";
	private static final String REFRESH = "/img/refresh.png";
	private static final String EDIT = "/img/edit1.png";
	private static final String MENUADMINSTRING = "menu_admin";

	private JPanel maquina;
	private CardLayout cl;
	private String nomeArmazem;
	private  MenuBar menuBar;

	/* POP UP */
	private JTextField maquinaField;
	private JTextField numeroField;

	public Maquina(String username, String nomeArmazem,  MenuBar menuBar) {
		db = DataBase.getInstance();
		this.popUp = new PopUp();
		this.check = new Check();
		maquinaField = new JTextField();
		numeroField = new JTextField();
		this.loginUsername = username;
		this.nomeArmazem = nomeArmazem;
		this.menuBar = menuBar;
		messageLogs = MessageLogs.getInstance();
		criaTabela();

	}

	private void criaTabela() {
		modelMaquina = new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Numero Serie" }) {

			private static final long serialVersionUID = 1880689174093893276L;
			boolean[] columnEditables = new boolean[] { false, false };
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class };

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

	
		maquinaTable = new JTable();
		DefaultDesign.styleTabela(maquinaTable, modelMaquina);
	}
	private void criaMaquinaSearch() {
		sorterMaquina = new TableRowSorter<>(modelMaquina);
		maquinaTable.setRowSorter(sorterMaquina);

		maquinaSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				maquinaSearch.setText("");
			}
		});
		maquinaSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(maquinaSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(maquinaSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(maquinaSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterMaquina.setRowFilter(null);
				} else {
					sorterMaquina.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesMaquina() {
		maquinaBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(maquinaBtnRefresh, REFRESH);

		maquinaBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(maquinaBtnHome);
		
		maquinaBtnAdicionar = new JButton("Adicionar");
		DefaultDesign.styleBotaoSimples(maquinaBtnAdicionar, ADD);
		
		maquinaBtnEditar = new JButton(EDITARSTRING);
		DefaultDesign.styleBotaoSimples(maquinaBtnEditar, EDIT);
		
		maquinaBtnRemover = new JButton(REMOVERSTRING);
		DefaultDesign.styleBotaoSimples(maquinaBtnRemover, REMOVE);
	}
	private GroupLayout putMaquinaLayout() {
		GroupLayout glMaquinaPanel = new GroupLayout(maquinaPanel);
		glMaquinaPanel.setHorizontalGroup(glMaquinaPanel.createParallelGroup(Alignment.TRAILING).addGroup(glMaquinaPanel
				.createSequentialGroup().addGap(37)
				.addGroup(glMaquinaPanel.createParallelGroup(Alignment.LEADING).addGroup(glMaquinaPanel
						.createSequentialGroup().addGap(30)
						.addGroup(
								glMaquinaPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(maquinaBtnRefresh, GroupLayout.PREFERRED_SIZE, 113,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(
												maquinaBtnHome, GroupLayout.PREFERRED_SIZE, 113,
												GroupLayout.PREFERRED_SIZE))
						.addGap(85)
						.addGroup(glMaquinaPanel.createParallelGroup(Alignment.TRAILING, false).addGroup(
								Alignment.LEADING,
								glMaquinaPanel.createSequentialGroup().addComponent(maquinaBtnAdicionar).addGap(18)
										.addComponent(maquinaBtnEditar, GroupLayout.PREFERRED_SIZE, 105,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(maquinaBtnRemover, GroupLayout.PREFERRED_SIZE, 113,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(maquinaSearch, GroupLayout.PREFERRED_SIZE, 127,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(maquinaScrollPane, GroupLayout.PREFERRED_SIZE, 544,
										GroupLayout.PREFERRED_SIZE)))
						.addComponent(maquinaTexto, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
						.addComponent(maquinaSeparator, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
				.addGap(193)));
		glMaquinaPanel.setVerticalGroup(glMaquinaPanel.createParallelGroup(Alignment.LEADING).addGroup(glMaquinaPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(glMaquinaPanel.createParallelGroup(Alignment.TRAILING).addGroup(glMaquinaPanel
						.createSequentialGroup()
						.addComponent(maquinaTexto, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(maquinaSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
						.addComponent(maquinaBtnRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(maquinaBtnHome, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addGap(64))
						.addGroup(glMaquinaPanel.createSequentialGroup().addGroup(glMaquinaPanel
								.createParallelGroup(Alignment.TRAILING)
								.addComponent(maquinaSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(glMaquinaPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(maquinaBtnAdicionar, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(maquinaBtnEditar, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(maquinaBtnRemover, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(maquinaScrollPane,
										GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addGap(34)))));
		return glMaquinaPanel;
	}
	public void showMaquinaMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		maquina = new JPanel();
		frame.getContentPane().add(maquina, "name_1323854438390700");
		maquina.setLayout(new BorderLayout(0, 0));

		maquinaPanel = new JPanel();
		maquina.add(maquinaPanel, BorderLayout.NORTH);

		criaBotoesMaquina();

		maquinaScrollPane = new JScrollPane();
		maquinaScrollPane.setBorder(new LineBorder(Color.BLACK));
		maquinaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		maquinaSeparator = new JSeparator();
		DefaultDesign.styleSeparator(maquinaSeparator);

		maquinaTexto = new JLabel("Maquinas");
		DefaultDesign.styleLabel(maquinaTexto);

		maquinaSearch = new JTextField();
		DefaultDesign.styleSearch(maquinaSearch);

		GroupLayout glMaquinaPanel = putMaquinaLayout();
		maquinaScrollPane.setViewportView(maquinaTable);
		maquinaPanel.setLayout(glMaquinaPanel);
		
		db.nomenumeroMaquina(nomeArmazem, modelMaquina);

		criaMaquinaSearch();
		buttonsMaquina(frame);
	}
	private void buttonsMaquina(JFrame frame) {
		maquinaBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);

			}
		});
		maquinaBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarMaquina(modelMaquina, menuBar.getNomeArmazem());
			}
		});
		maquinaBtnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarMaquina(maquinaTable, modelMaquina, menuBar.getNomeArmazem());
			}
		});
		maquinaBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(modelMaquina, menuBar.getNomeArmazem());
			}
		});
		maquinaBtnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeMaquina(maquinaTable, modelMaquina, menuBar.getNomeArmazem());
			}
		});
		maquinaTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeMaquina(maquinaTable, modelMaquina, menuBar.getNomeArmazem());

			}
		});
		/* Double click num elemento */
		maquinaTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					System.out.println("Clicou no  " + modelMaquina.getValueAt(table.getSelectedRow(), 0));
				}
			}
		});
	}
	


	public JPanel getMaquina() {
		return maquina;
	}

	private void resetBorders() {
		maquinaField.setBorder(new JTextField().getBorder());
		numeroField.setBorder(new JTextField().getBorder());
	}

	private boolean confirmData() {
		maquinaField.setBorder(new JTextField().getBorder());
		numeroField.setBorder(new JTextField().getBorder());

		/* Confirmar dados */
		if (check.blankText(maquinaField.getText())) {
			popUp.showPopUp("É necessario escolher um nome para a maquina", "Maquina");
			maquinaField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (check.blankText(numeroField.getText())) {
			popUp.showPopUp("É necessario escolher um numero para a maquina", "Maquina");
			numeroField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;
	}

	/* User POP UP Adicionar */
	private int showMaquinaPopUpAdicionar() {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(MAQUINA));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, -10, 10));

		JLabel lblArmazem = new JLabel("Maquina");
		lblArmazem.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblArmazem);

		maquinaField.setText("");
		panel.add(maquinaField);
		maquinaField.setColumns(15);

		JLabel lblNumero = new JLabel("Numero Serie");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNumero);

		numeroField.setText("");
		panel.add(numeroField);
		numeroField.setColumns(15);

		return JOptionPane.showOptionDialog(null, panel, "Adicionar máquina", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	/* User POP UP Editar */
	private int showMaquinaPopUpEditar(String nome, String numero) {
		Object[] options1 = { "Ok", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(MAQUINA));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblArmazem = new JLabel("Maquina");
		lblArmazem.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblArmazem);

		panel.add(maquinaField);
		maquinaField.setColumns(15);
		maquinaField.setText(nome);

		JLabel lblNumero = new JLabel("Numero Serie");
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNumero);

		panel.add(numeroField);
		numeroField.setColumns(15);
		numeroField.setText(numero);

		return JOptionPane.showOptionDialog(null, panel, "Editar " + nome, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	/* Só falta base de dados */
	public void adicionarMaquina(DefaultTableModel modelMaquina, String armazem) {

		boolean isFinished = false;

		while (!isFinished) {
			int result = showMaquinaPopUpAdicionar();// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {

				isFinished = confirmData();

			} else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
				resetBorders();
				break;
			}
		}

		if (isFinished) {
			if (db.addMaquina(numeroField.getText(), maquinaField.getText(), armazem)) {
				messageLogs.adicionaMaquina(loginUsername+";"+true+";"+armazem, maquinaField.getText(), numeroField.getText());
				popUp.showPopUpAdicionadoSucesso();
				modelMaquina.addRow(new Object[] { maquinaField.getText(), numeroField.getText() });
			} else {
				popUp.showPopUpErroAdicionar();
			}
		}
			
	}

	public void editarMaquina(JTable maquinaTable, DefaultTableModel modelMaquina, String nomeArmazem) {
		boolean isFinished = false;
		int[] indexOfRow = maquinaTable.getSelectedRows();

		if (indexOfRow.length != 1) {
			popUp.showPopUp("Selecione apenas 1 linha", "Erro ao selecionar");
			return;
		}
		indexOfRow[0] = maquinaTable.convertRowIndexToModel(indexOfRow[0]);// converte para o indice correto (quando
																			// ordena)
		while (!isFinished) {
			int option = showMaquinaPopUpEditar(modelMaquina.getValueAt(indexOfRow[0], 0).toString(),
					modelMaquina.getValueAt(indexOfRow[0], 1).toString());
			if (option == JOptionPane.YES_OPTION) {
				isFinished = confirmData();
			} else {
				resetBorders();
				break;
			}
		}

		if (isFinished) {
			String numero= modelMaquina.getValueAt(indexOfRow[0], 1).toString();
			String nome = modelMaquina.getValueAt(indexOfRow[0], 0).toString();
			if (db.updateMaquina(numero, numeroField.getText(),
					maquinaField.getText())) {
				messageLogs.editaMaquina(loginUsername+";"+true+";"+nomeArmazem, nome, numero, maquinaField.getText(), numeroField.getText());
				popUp.showPopUpEditarSucesso();
				modelMaquina.setValueAt(maquinaField.getText(), indexOfRow[0], 0);
				modelMaquina.setValueAt(numeroField.getText(), indexOfRow[0], 1);
			} else {
				popUp.showPopUpErroEditar();

			}

		}
	}

	public void removeMaquina(JTable maquinaTable, DefaultTableModel modelMaquina, String armazem) {

		int[] selectedRows = maquinaTable.getSelectedRows();
		if (selectedRows.length == 0) {
			popUp.showPopUp("Selecione pelo menos uma linha", "Erro ao selecionar");
			return;
		}

		int option = popUp.showPopUpConfirmation(
				"Tem a certeza que deseja apagar " + selectedRows.length + " maquinas?", "Eliminar maquinas");
		if (option == JOptionPane.YES_OPTION) {
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				String numero = modelMaquina.getValueAt(maquinaTable.convertRowIndexToModel(selectedRows[i]), 1).toString();
				String nome = modelMaquina.getValueAt(maquinaTable.convertRowIndexToModel(selectedRows[i]), 0).toString();
				if (db.removeMaquina(numero)) {
					messageLogs.removeMaquina(loginUsername+";"+true+";"+armazem, nome, numero);
					modelMaquina.removeRow(maquinaTable.convertRowIndexToModel(selectedRows[i]));
				} else {
					popUp.showPopUpErroEliminar();
					break;
				}
			}
		}

	}

	public void refresh(DefaultTableModel modelMaquina, String armazem) {
		int rowCount = modelMaquina.getRowCount();
		if (!db.connect()) {
			popUp.showPopUpDataBaseError();
		} else {
			for (int i = rowCount - 1; i >= 0; i--) {
				modelMaquina.removeRow(i);
			}
			db.nomenumeroMaquina(armazem, modelMaquina);
		}
	}

}
