package gui.gui_admin.fornecedores;

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

public class Fornecedores {
	private static final String REFRESHSTRING = "Refresh";
	private static final String EDITARSTRING = "Editar";
	private static final String REMOVERSTRING = "Remover";
	private static final String ADD = "/img/add.png";
	private static final String REMOVE = "/img/remove.png";
	private static final String REFRESH = "/img/refresh.png";
	private static final String EDIT = "/img/edit1.png";
	private static final String MENUADMINSTRING = "menu_admin";
	private static final String FORNECEDOR = "/img/truck.png";
	private PopUp popUp;
	private Check check;
	private DataBase db; 
	private MessageLogs messageLogs;
	private DefaultTableModel modelFornecedor;
	private JPanel fornecedoresPanel;
	private JLabel fornecedoresTexto;
	private JScrollPane fornecedoresScrollPane;
	private JButton fornecedoresBtnAdicionar;
	private JButton fornecedoresBtnEditar;
	private JButton fornecedoresBtnRemover;
	private JButton fornecedoresBtnRefresh;
	private JButton fornecedoresBtnHome;
	private JTable fornecedorTable;
	private TableRowSorter<DefaultTableModel> sorterFornecedor;
	private JTextField fornecedorSearch;
	private JPanel fornecedores;

	
	private String loginUsername;
	private MenuBar menuBar;
	private CardLayout cl;
	/* POP UP */
	private JTextField fornecedorField;
	private JSeparator separator;

	public Fornecedores(String username,  MenuBar menuBar) {
		db = DataBase.getInstance();
		this.popUp = new PopUp();
		this.check = new Check();
		fornecedorField = new JTextField();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
		criaTabela();
	}
	
	private void criaTabela() {
		modelFornecedor = new DefaultTableModel(new Object[][] {}, new String[] { "Nome" }) {

			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

	
		fornecedorTable = new JTable();
		DefaultDesign.styleTabela(fornecedorTable, modelFornecedor);
	}
	private void criaFornecedorSearch() {
		sorterFornecedor = new TableRowSorter<>(modelFornecedor);
		fornecedorTable.setRowSorter(sorterFornecedor);

		fornecedorSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fornecedorSearch.setText("");
			}
		});
		fornecedorSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(fornecedorSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(fornecedorSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(fornecedorSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterFornecedor.setRowFilter(null);
				} else {
					sorterFornecedor.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesFornecedores() {
		fornecedoresBtnAdicionar = new JButton("Adicionar");
		DefaultDesign.styleBotaoSimples(fornecedoresBtnAdicionar, ADD);

		fornecedoresBtnEditar = new JButton(EDITARSTRING);
		DefaultDesign.styleBotaoSimples(fornecedoresBtnEditar, EDIT);

		fornecedoresBtnRemover = new JButton(REMOVERSTRING);
		DefaultDesign.styleBotaoSimples(fornecedoresBtnRemover, REMOVE);

		fornecedoresBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(fornecedoresBtnRefresh, REFRESH);

		fornecedoresBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(fornecedoresBtnHome);
	}
	private GroupLayout putFornecedoresLayout() {
		GroupLayout glFornecedoresPanel = new GroupLayout(fornecedoresPanel);
		glFornecedoresPanel.setHorizontalGroup(
			glFornecedoresPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(glFornecedoresPanel.createSequentialGroup()
					.addGap(37)
					.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(fornecedoresTexto, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
						.addGroup(glFornecedoresPanel.createSequentialGroup()
							.addGap(30)
							.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(fornecedoresBtnRefresh, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addComponent(fornecedoresBtnHome, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
							.addGap(85)
							.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(glFornecedoresPanel.createSequentialGroup()
									.addComponent(fornecedoresBtnAdicionar)
									.addGap(18)
									.addComponent(fornecedoresBtnEditar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(fornecedoresBtnRemover, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
								.addComponent(fornecedoresScrollPane, 0, 0, Short.MAX_VALUE))
							.addGap(6)
							.addComponent(fornecedorSearch, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
					.addGap(383))
		);
		glFornecedoresPanel.setVerticalGroup(
			glFornecedoresPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glFornecedoresPanel.createSequentialGroup()
					.addComponent(fornecedoresTexto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(9)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(glFornecedoresPanel.createSequentialGroup()
							.addComponent(fornecedoresBtnRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(fornecedoresBtnHome, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
							.addGap(64))
						.addGroup(glFornecedoresPanel.createSequentialGroup()
							.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(fornecedoresBtnAdicionar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(fornecedoresBtnEditar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(fornecedoresBtnRemover, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(glFornecedoresPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(fornecedoresScrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addComponent(fornecedorSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(34))))
		);
		return glFornecedoresPanel;
	}

	public void showFornecedoresMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		fornecedores = new JPanel();
		frame.getContentPane().add(fornecedores, "name_1323749306915900");
		fornecedores.setLayout(new BorderLayout(0, 0));

		fornecedoresPanel = new JPanel();
		fornecedores.add(fornecedoresPanel, BorderLayout.CENTER);

		fornecedoresTexto = new JLabel("Fornecedores");
		DefaultDesign.styleLabel(fornecedoresTexto);
		separator = new JSeparator();
		DefaultDesign.styleSeparator(separator);
		fornecedoresScrollPane = new JScrollPane();
		fornecedoresScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		criaBotoesFornecedores();

		fornecedorSearch = new JTextField();
		DefaultDesign.styleSearch(fornecedorSearch);
		GroupLayout glFornecedoresPanel = putFornecedoresLayout();

		
		fornecedoresScrollPane.setViewportView(fornecedorTable);
		fornecedoresPanel.setLayout(glFornecedoresPanel);
		
		db.nomeFornecedor(modelFornecedor);
		
		criaFornecedorSearch();
		buttonsFornecedor(frame);
	}
	
	private void buttonsFornecedor(JFrame frame) {
		fornecedoresBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);

			}
		});
		fornecedoresBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFornecedor(modelFornecedor, menuBar.getNomeArmazem());
			}
		});
		fornecedoresBtnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFornecedor(fornecedorTable, modelFornecedor, menuBar.getNomeArmazem());
			}
		});
		fornecedoresBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(modelFornecedor);
			}
		});
		fornecedoresBtnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFornecedor(fornecedorTable, modelFornecedor, menuBar.getNomeArmazem());
			}
		});

		fornecedorTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeFornecedor(fornecedorTable, modelFornecedor, menuBar.getNomeArmazem());

			}
		});
		/* Double click num elemento */
		fornecedorTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
				}
			}
		});
	}

	
	public JPanel getFornecedores() {
		return fornecedores;
	}


	private void resetBorders() {
		fornecedorField.setBorder(new JTextField().getBorder());
	}
	
	private boolean confirmData() {
		fornecedorField.setBorder(new JTextField().getBorder());
		
		/*Confirmar dados*/
		if (check.blankText(fornecedorField.getText())) {//testa se nao é tudo espaços brancos
			popUp.showPopUp("É necessario escolher um nome para o armazem", "Armazem");
			fornecedorField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;
		
	}
	
	/* User POP UP Adicionar */
	private int showFornecedorPopUpAdicionar() {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(FORNECEDOR));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, -10, 10));

		JLabel lblArmazem = new JLabel("Nome Fornecedor");
		lblArmazem.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblArmazem);

		fornecedorField.setText("");
		panel.add(fornecedorField);
		fornecedorField.setColumns(15);

		
		return JOptionPane.showOptionDialog(null, panel, "Adicionar fornecedor", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	/*User POP UP Editar*/
	private int showFornecedorPopUpEditar(String nome) {
		Object[] options1 = { "Ok", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(FORNECEDOR));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		
		JLabel lblUser = new JLabel("Nome");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);
		
		fornecedorField.setText(nome);
		fornecedorField.setColumns(10);
		panel.add(fornecedorField);
		

		
		return JOptionPane.showOptionDialog(null, panel, "Editar "+ nome, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
		 }

	
	private void adicionarFornecedor(DefaultTableModel modelFornecedor, String armazem) {

		boolean isFinished= false;

		while (!isFinished) {
			int result = showFornecedorPopUpAdicionar();// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
		
				isFinished = confirmData();

			} else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
				resetBorders();
				break;
			}
		}

		if(isFinished) {
			db.addFornecedor(fornecedorField.getText() , armazem);
			messageLogs.adicionaFornecedor(loginUsername+";"+true+";"+armazem, fornecedorField.getText());
			modelFornecedor.addRow(new Object[] {fornecedorField.getText()});
		}
	}

	
	private void editarFornecedor(JTable fornecedorTable, DefaultTableModel modelFornecedor, String armazem) {
		boolean isFinished=false;
		int[] indexOfRow = fornecedorTable.getSelectedRows();
		
		if(indexOfRow.length != 1){
			popUp.showPopUp("Selecione apenas 1 linha","Erro ao selecionar");
			return;
		}
		indexOfRow[0]= fornecedorTable.convertRowIndexToModel(indexOfRow[0]);//converte para o indice correto (quando ordena)
		while(!isFinished) {
			int option=showFornecedorPopUpEditar(modelFornecedor.getValueAt(indexOfRow[0], 0).toString());
			if(option == JOptionPane.YES_OPTION) {
				isFinished = confirmData();
			}else {
				resetBorders();
				break;
			}
		}
		
		if(isFinished) {
			String nome = modelFornecedor.getValueAt(indexOfRow[0], 0).toString();
			db.updateFornecedor(nome, fornecedorField.getText());
			messageLogs.editaFornecedor(loginUsername+";"+true+";"+armazem,nome, fornecedorField.getText());
			modelFornecedor.setValueAt(fornecedorField.getText(),indexOfRow[0], 0);
		}
	}
	

	private void removeFornecedor(JTable fornecedorTable,  DefaultTableModel modelFornecedor, String armazem) {

		int[] selectedRows= fornecedorTable.getSelectedRows();
		if(selectedRows.length==0) {
			popUp.showPopUp("Selecione pelo menos uma linha", "Erro ao selecionar");
			return;
		}
		
		int option= popUp.showPopUpConfirmation("Tem a certeza que deseja apagar "+selectedRows.length + " Fornecedores?", "Eliminar fornecedores");
		if(option == JOptionPane.YES_OPTION) {
			for(int i=selectedRows.length-1; i>=0 ; i--) {
				String nome = modelFornecedor.getValueAt(fornecedorTable.convertRowIndexToModel(selectedRows[i]), 0).toString();
				db.removeFornecedor(nome);
				messageLogs.removeFornecedor(loginUsername+";"+true+";"+armazem, nome);
				modelFornecedor.removeRow(fornecedorTable.convertRowIndexToModel(selectedRows[i]));
			}
		}
		
	}

	
	private void refresh(DefaultTableModel modelFornecedor) {
		int rowCount = modelFornecedor.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			modelFornecedor.removeRow(i);
		}
		db.nomeFornecedor( modelFornecedor);
	}
}
