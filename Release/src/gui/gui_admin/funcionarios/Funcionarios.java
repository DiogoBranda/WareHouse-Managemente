package gui.gui_admin.funcionarios;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
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

public class Funcionarios {
	
	private static final String MENUADMINSTRING = "menu_admin";
	private static final String ADD = "/img/add.png";
	private static final String REMOVE = "/img/remove.png";
	private static final String REFRESH = "/img/refresh.png";
	private static final String EDIT = "/img/edit1.png";
	private static final String REFRESHSTRING = "Refresh";
	private static final String EDITARSTRING = "Editar";
	private static final String REMOVERSTRING = "Remover";
	private DataBase db;//DATA BASE
	private MessageLogs messageLogs;
	private static final String WAREHOUSE = "/img/funcionario.png";
	private PopUp popUp;
	private Check check;
	private JPanel funcionario;

	private String loginUsername;
	private JPanel funcionarioPanel;
	private JLabel funcionarioTexto;
	private JSeparator funcionarioSeparator;
	private JScrollPane funcionarioScrollPane;
	private JButton funcionarioBtnAdicionar;
	private JButton funcionarioBtnEditar;
	private JButton funcionarioBtnRemover;
	private JButton funcionarioBtnHome;
	private JButton funcionarioBtnRefresh;
	private DefaultTableModel modelFuncionario;
	private TableRowSorter<DefaultTableModel> sorterFuncionario;
	private JTable funcionarioTable;
	private JTextField funcionarioSearch;
	private CardLayout cl;
	private String nomeArmazem;
	/* POP UP */
	private JTextField nifField;
	private JTextField nomeField;
	private JSpinner idade;
	private JTextField funcaoField;
	private JSpinner salario;
	private  MenuBar menuBar;
	
	public Funcionarios(String username, String nomeArmazem, MenuBar menuBar) {
		this.popUp = new PopUp();
		this.check = new Check();
		nifField = new JTextField();
		nomeField = new JTextField();
		funcaoField = new JTextField();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
		this.nomeArmazem = nomeArmazem;
		criaTabela();

	}
	
	private void criaTabela() {
		modelFuncionario = new DefaultTableModel(new Object[][] {}, new String[] { "NIF", "Nome" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class };

			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		funcionarioTable = new JTable();
		DefaultDesign.styleTabela(funcionarioTable,modelFuncionario);
		funcionarioTable.getColumnModel().getColumn(0).setResizable(true);
		funcionarioTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		funcionarioTable.getColumnModel().getColumn(0).setMaxWidth(150);
		funcionarioTable.getColumnModel().getColumn(1).setResizable(true);
	}
	private void criaFuncionarioSearch() {
		sorterFuncionario = new TableRowSorter<>(modelFuncionario);
		funcionarioTable.setRowSorter(sorterFuncionario);

		funcionarioSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				funcionarioSearch.setText("");
			}
		});
		funcionarioSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(funcionarioSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(funcionarioSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(funcionarioSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterFuncionario.setRowFilter(null);
				} else {
					sorterFuncionario.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesFuncionario() {
		funcionarioBtnAdicionar = new JButton("Adicionar");
		DefaultDesign.styleBotaoSimples(funcionarioBtnAdicionar, ADD);

		funcionarioBtnEditar = new JButton(EDITARSTRING);
		DefaultDesign.styleBotaoSimples(funcionarioBtnEditar, EDIT);

		funcionarioBtnRemover = new JButton(REMOVERSTRING);
		DefaultDesign.styleBotaoSimples(funcionarioBtnRemover, REMOVE);
		
		funcionarioBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(funcionarioBtnHome);

		funcionarioBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(funcionarioBtnRefresh, REFRESH);		
	}
	private GroupLayout putFuncionarioLayout() {
		GroupLayout glFuncionarioPanel = new GroupLayout(funcionarioPanel);
		glFuncionarioPanel.setHorizontalGroup(
			glFuncionarioPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(glFuncionarioPanel.createSequentialGroup()
					.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glFuncionarioPanel.createSequentialGroup()
							.addGap(20)
							.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(funcionarioTexto, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
								.addComponent(funcionarioSeparator, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
						.addGroup(glFuncionarioPanel.createSequentialGroup()
							.addGap(44)
							.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(funcionarioBtnRefresh, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addComponent(funcionarioBtnHome, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(glFuncionarioPanel.createSequentialGroup()
							.addComponent(funcionarioBtnAdicionar)
							.addGap(18)
							.addComponent(funcionarioBtnEditar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(funcionarioBtnRemover, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(funcionarioSearch, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
						.addComponent(funcionarioScrollPane, GroupLayout.PREFERRED_SIZE, 548, GroupLayout.PREFERRED_SIZE))
					.addGap(42))
		);
		glFuncionarioPanel.setVerticalGroup(
			glFuncionarioPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(glFuncionarioPanel.createSequentialGroup()
					.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glFuncionarioPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(funcionarioTexto, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(funcionarioSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(52)
							.addComponent(funcionarioBtnRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(funcionarioBtnHome, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(glFuncionarioPanel.createSequentialGroup()
							.addGap(32)
							.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(glFuncionarioPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(funcionarioBtnAdicionar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addComponent(funcionarioBtnRemover, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addComponent(funcionarioBtnEditar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addComponent(funcionarioSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(funcionarioScrollPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
					.addGap(37))
		);
		return glFuncionarioPanel;
	}
	public void showFuncionarioMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		funcionario = new JPanel();
		frame.getContentPane().add(funcionario, "name_1832054822092700");
		funcionario.setLayout(new BorderLayout(0, 0));

		funcionarioPanel = new JPanel();
		funcionario.add(funcionarioPanel, BorderLayout.CENTER);

		funcionarioTexto = new JLabel("Funcion\u00E1rios");
		DefaultDesign.styleLabel(funcionarioTexto);

		funcionarioSeparator = new JSeparator();
		DefaultDesign.styleSeparator(funcionarioSeparator);

		funcionarioScrollPane = new JScrollPane();
		funcionarioScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		funcionarioScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		funcionarioSearch = new JTextField();
		DefaultDesign.styleSearch(funcionarioSearch);

		criaBotoesFuncionario();
		
		GroupLayout glFuncionarioPanel = putFuncionarioLayout();
		
		
		funcionarioScrollPane.setViewportView(funcionarioTable);
		funcionarioPanel.setLayout(glFuncionarioPanel);
		
		db.nomeNifFuncionario(modelFuncionario, nomeArmazem);

		criaFuncionarioSearch();
		buttonsFuncionarios(frame);
	}
	private void buttonsFuncionarios(JFrame frame) {
		funcionarioBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);

			}
		});
		funcionarioBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFuncionario(modelFuncionario, menuBar.getNomeArmazem());
			}
		});
		funcionarioBtnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFuncionario(funcionarioTable, modelFuncionario);
			}
		});
		funcionarioBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(modelFuncionario, menuBar.getNomeArmazem());
			}
		});
		funcionarioBtnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFuncionario(funcionarioTable, modelFuncionario, menuBar.getNomeArmazem());
			}
		});
		funcionarioTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeFuncionario(funcionarioTable, modelFuncionario, menuBar.getNomeArmazem());

			}
		});

	}
	
	public JPanel getFuncionario() {
		return funcionario;
	}

	public DefaultTableModel getModelFuncionario() {
		return modelFuncionario;
	}

	public JTable getFuncionarioTable() {
		return funcionarioTable;
	}

	private boolean confirmData() {

		nomeField.setBorder(new JTextField().getBorder());
		nifField.setBorder(new JTextField().getBorder());
		funcaoField.setBorder(new JTextField().getBorder());

		/* Confirma dados */
		if (!check.isNifValid(nifField.getText())) {
			popUp.showPopUp("NIF inválido, por favor introduzir um NIF válido", "NIF");
			nifField.setBorder(new LineBorder(Color.red, 1));

			return false;
		} else if (!check.textValidWithoutNumbers(nomeField.getText())) {
			popUp.showPopUp("É necessario escolher uma nome válido", "Nome");
			nomeField.setBorder(new LineBorder(Color.red, 1));
			return false;
		} else if (!check.textValidWithoutNumbers(funcaoField.getText())) {
			popUp.showPopUp("É necessario escolher uma função válido", "Função");
			funcaoField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;

	}

	/* User POP UP Adicionar */
	private int showFuncionarioPopUpAdicionar(String nif, String nome, int idade, String funcao, double salario) {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(WAREHOUSE));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, -10, 10));

		JLabel lblNif = new JLabel("NIF");
		lblNif.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNif);

		panel.add(nifField);
		nifField.setColumns(15);
		nifField.setText(nif);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNome);

		panel.add(nomeField);
		nomeField.setColumns(15);
		nomeField.setText(nome);

		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblIdade);

		this.idade = new JSpinner();
		this.idade.setModel(new SpinnerNumberModel(22, 16, 99, 1));
		panel.add(this.idade);
		this.idade.setValue(Integer.valueOf(idade));

		JLabel lblFuncao = new JLabel("Função");
		lblFuncao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblFuncao);

		panel.add(funcaoField);
		funcaoField.setColumns(15);
		funcaoField.setText(funcao);

		JLabel lblSalario = new JLabel("Salário");
		lblSalario.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblSalario);

		this.salario = new JSpinner();
		panel.add(this.salario);
		this.salario.setModel(new SpinnerNumberModel(new Double(500), new Double(0), new Double(500000), new Double(200)));
		this.salario.setValue(Double.valueOf(salario));

		return JOptionPane.showOptionDialog(null, panel, "Adicionar Funcionario", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	/* User POP UP Editar */
	private int showFuncionarioPopUpEditar(String nif, String nome, int idade, String funcao, float salario) {
		Object[] options1 = { "Ok", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(WAREHOUSE));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblNif = new JLabel("NIF");
		lblNif.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNif);

		panel.add(nifField);
		nifField.setColumns(15);
		nifField.setText(nif);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNome);

		panel.add(nomeField);
		nomeField.setColumns(15);
		nomeField.setText(nome);

		JLabel lblIdade = new JLabel("Idade");
		lblIdade.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblIdade);

		this.idade = new JSpinner();
		this.idade.setModel(new SpinnerNumberModel(idade, 16, 99, 1));
		panel.add(this.idade);

		JLabel lblFuncao = new JLabel("Função");
		lblFuncao.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblFuncao);

		panel.add(funcaoField);
		funcaoField.setColumns(15);
		funcaoField.setText(funcao);

		JLabel lblSalario = new JLabel("Salário");
		lblSalario.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblSalario);

		this.salario = new JSpinner();
		panel.add(this.salario);
		this.salario
				.setModel(new SpinnerNumberModel(Float.valueOf(salario), Float.valueOf(0), Float.valueOf(500000), Float.valueOf(200)));

		return JOptionPane.showOptionDialog(null, panel, "Editar " + nome, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	public void adicionarFuncionario(DefaultTableModel modelFuncionario, String armazem) {
		String nif = null;
		String nome = null;
		int idade = 22;
		String funcao = null;
		double salario = (double) 500.0;

		boolean isFinished = false;

		while (!isFinished) {
			int result = showFuncionarioPopUpAdicionar(nif, nome, idade, funcao, salario);// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
				/* adiciona na tabela e base de dados */
				isFinished = confirmData();
				nif = nifField.getText();
				nome = nomeField.getText();
				idade = (Integer) this.idade.getValue();
				funcao = funcaoField.getText();
				salario = (double) this.salario.getValue();
			} else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
				break;
			}
		}

		if (isFinished) {
			db.addFuncionario(nif+";"+nome+";"+idade+";"+funcao+";"+salario+";"+armazem);
			messageLogs.adicionaFuncionario(loginUsername+";"+true+";"+armazem, nome, nif);
			modelFuncionario.addRow(new Object[] { nifField.getText(), nomeField.getText() });
		}

	}

	public void editarFuncionario(JTable funcionarioTable, DefaultTableModel modelFuncionario) {
		boolean isFinished = false;
		int[] indexOfRow = funcionarioTable.getSelectedRows();
		if (indexOfRow.length != 1) {
			popUp.showPopUp("Selecione apenas 1 linha", "Erro ao selecionar");
			return;
		}
		indexOfRow[0]= funcionarioTable.convertRowIndexToModel(indexOfRow[0]);
		String nif = (String) modelFuncionario.getValueAt(indexOfRow[0], 0);
		String nome = (String) modelFuncionario.getValueAt(indexOfRow[0], 1);

		while (!isFinished) {
			/* Colocar a informaçao vinda da base de dados */
			int option = showFuncionarioPopUpEditar(nif, nome, 18, "Engenheiro", (float) 1000.50);
			if (option == JOptionPane.YES_OPTION) {
				isFinished = confirmData();
				nif = nifField.getText();
				nome = nomeField.getText();
			} else {
				break;
			}
		}
		if (isFinished) {
			String dados = nifField.getText()+";"+nomeField.getText()+";"+this.idade.getValue()+";"+funcaoField.getText()+";"+this.salario.getValue();
			db.updateFuncionario(modelFuncionario.getValueAt(indexOfRow[0],0 ).toString(), dados);
			modelFuncionario.setValueAt(nomeField.getText(), indexOfRow[0], 1);
			modelFuncionario.setValueAt(nifField.getText(), indexOfRow[0], 0);
		}

	}

	public void removeFuncionario(JTable funcionarioTable, DefaultTableModel modelFuncionario, String armazem) {
		/* eliminar na base de dados */
		int[] selectedRows = funcionarioTable.getSelectedRows();
		if(selectedRows.length<=0) {
			popUp.showPopUp("Precisa de selecionar pelo menos 1 linha", "Erro ao eliminar");
			return;
		}
	
		int option = popUp.showPopUpConfirmation(
				"Tem a certeza que deseja apagar " + selectedRows.length + " funcionarios?", "Eliminar funcionarios");
		if (option == JOptionPane.YES_OPTION) {
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				String nif= modelFuncionario.getValueAt(funcionarioTable.convertRowIndexToModel(selectedRows[i]), 0).toString();
				String nome = modelFuncionario.getValueAt(funcionarioTable.convertRowIndexToModel(selectedRows[i]), 1).toString();
				db.removeFuncionarioByNif(nif);
				messageLogs.removeFuncionario(loginUsername+";"+true+";"+armazem, nome, nif);
				modelFuncionario.removeRow(funcionarioTable.convertRowIndexToModel(selectedRows[i]));
			}
		}
	}

	public void refresh(DefaultTableModel modelFuncionario, String armazem) {
		int rowCount = modelFuncionario.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			modelFuncionario.removeRow(i);
		}
		db.nomeNifFuncionario(modelFuncionario, armazem);

	}

}
