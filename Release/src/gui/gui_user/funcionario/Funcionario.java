package gui.gui_user.funcionario;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.DataBase;
import gui.DefaultDesign;
import gui.gui_admin.Admin;
import gui.menu_bar.MenuBar;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Funcionario {
	
	private static final String USER = "/img/user.png";
	private static final String REFRESH = "/img/refresh.png";

	private DataBase db;
	private JLabel lblFuncionarios;
	private JPanel funcionariosMenu;
	private JScrollPane scrollPaneFuncionario;
	private JSeparator separatorFuncionario;
	private JButton btnRefreshFuncionarios;
	private JButton btnHomeFuncionarios;
	private DefaultTableModel modelFuncionarios;
	private JTable tabelaFuncionarios;
	private TableRowSorter<DefaultTableModel> sorterFuncionario;
	private JTextField funcionarioSearch;
	private JPanel funcionarios;
	private MenuBar menuBar;
	private CardLayout cl;
	
	public Funcionario(MenuBar menuBar) {
		db = DataBase.getInstance();
		this.menuBar = menuBar;
	}
	
	
	private void criaFuncionariosSearch() {
		sorterFuncionario = new TableRowSorter<>(modelFuncionarios);
		tabelaFuncionarios.setRowSorter(sorterFuncionario);

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
	private void criaBotoesFuncionarios() {
		btnRefreshFuncionarios = new JButton("Refresh");
		DefaultDesign.styleBotaoSimples(btnRefreshFuncionarios, REFRESH);

		btnHomeFuncionarios = new JButton("Home");
		DefaultDesign.styleBotaoHome(btnHomeFuncionarios);
	}
	private GroupLayout putFuncionariosLayout() {
		GroupLayout glFuncionariosMenu = new GroupLayout(funcionariosMenu);
		glFuncionariosMenu.setHorizontalGroup(glFuncionariosMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(glFuncionariosMenu.createSequentialGroup().addGap(33).addGroup(glFuncionariosMenu
						.createParallelGroup(Alignment.LEADING)
						.addGroup(glFuncionariosMenu.createSequentialGroup().addGroup(glFuncionariosMenu
								.createParallelGroup(Alignment.LEADING)
								.addComponent(separatorFuncionario, GroupLayout.PREFERRED_SIZE, 155	, GroupLayout.PREFERRED_SIZE)
								.addGroup(glFuncionariosMenu.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnRefreshFuncionarios, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnHomeFuncionarios, GroupLayout.DEFAULT_SIZE, 109,
												Short.MAX_VALUE)))
								.addPreferredGap(ComponentPlacement.RELATED, 135, Short.MAX_VALUE).addComponent(
										scrollPaneFuncionario, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE))
						.addGroup(glFuncionariosMenu.createSequentialGroup().addComponent(lblFuncionarios)
								.addPreferredGap(ComponentPlacement.RELATED, 560, Short.MAX_VALUE)
								.addComponent(funcionarioSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGap(38)));
		glFuncionariosMenu.setVerticalGroup(glFuncionariosMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(glFuncionariosMenu.createSequentialGroup().addGap(41)
						.addComponent(lblFuncionarios, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(separatorFuncionario, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(43)
						.addComponent(btnRefreshFuncionarios, GroupLayout.PREFERRED_SIZE, 33,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnHomeFuncionarios,GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING,
						glFuncionariosMenu.createSequentialGroup().addContainerGap(73, Short.MAX_VALUE)
								.addComponent(funcionarioSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneFuncionario, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
								.addGap(19)));
		return glFuncionariosMenu;
	}

	public void showFuncionariosMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		funcionarios = new JPanel();
		frame.getContentPane().add(funcionarios, "name_126627817283200");
		funcionarios.setLayout(new BorderLayout(0, 0));

		funcionariosMenu = new JPanel();
		funcionarios.add(funcionariosMenu, BorderLayout.CENTER);

		scrollPaneFuncionario = new JScrollPane();

		lblFuncionarios = new JLabel("Funcionarios");
		DefaultDesign.styleLabel28(lblFuncionarios);
		
		criaBotoesFuncionarios();

		separatorFuncionario = new JSeparator();
		DefaultDesign.styleSeparator(separatorFuncionario);
		
		funcionarioSearch = new JTextField();
		DefaultDesign.styleSearch(funcionarioSearch);
		
		GroupLayout glFuncionariosMenu = putFuncionariosLayout();
		
		modelFuncionarios = new DefaultTableModel(new Object[][] {},
				new String[] { "NIF", "Nome", "Idade", "Fun\u00E7\u00E3o" }) {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		};

		tabelaFuncionarios = new JTable();
		tabelaFuncionarios.setModel(modelFuncionarios);
		tabelaFuncionarios.getColumnModel().getColumn(0).setMinWidth(80);
		tabelaFuncionarios.getColumnModel().getColumn(0).setMaxWidth(80);

		tabelaFuncionarios.getColumnModel().getColumn(1).setMinWidth(100);
		tabelaFuncionarios.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabelaFuncionarios.getColumnModel().getColumn(2).setMinWidth(40);
		tabelaFuncionarios.getColumnModel().getColumn(2).setMaxWidth(50);
		tabelaFuncionarios.getColumnModel().getColumn(3).setMinWidth(150);

		scrollPaneFuncionario.setViewportView(tabelaFuncionarios);
		funcionariosMenu.setLayout(glFuncionariosMenu);

		tabelaFuncionarios.getTableHeader().setReorderingAllowed(false);
		tabelaFuncionarios.setAutoCreateRowSorter(true);// para ordenar
		tabelaFuncionarios.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		db.nifIdadeNomeFuncaoFuncionario(menuBar.getNomeArmazem(), modelFuncionarios);

		criaFuncionariosSearch();
		botoesFuncionarios(frame);
	}

	private void botoesFuncionarios(JFrame frame) {
		
		btnHomeFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "userDesign");
			}
		});
		tabelaFuncionarios.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				int index = table.convertRowIndexToModel(table.getSelectedRow());
				if (mouseEvent.getClickCount() == 2 && index != -1) {
					showAllInfoFuncionar(modelFuncionarios.getValueAt(index, 0).toString());
				}
			}
		});
		
		btnRefreshFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshFuncionarios(modelFuncionarios, menuBar.getNomeArmazem());
			}
		});

	}

	private int showAllInfoFuncionar(String nif) {
		Object[] options1 = { "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(USER));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, 0, 0));
		
		String[] user= db.getFuncinarioByNif(nif);
		JLabel lblNif = new JLabel("NIF:");
		panel.add(lblNif);

		JLabel lblNifUser = new JLabel(nif);
		panel.add(lblNifUser);
		
		JLabel lblNome = new JLabel("Nome:");
		panel.add(lblNome);

		JLabel lblNomeUser = new JLabel(user[0]);
		panel.add(lblNomeUser);
		
		JLabel lblIdade = new JLabel("Idade:");
		panel.add(lblIdade);

		JLabel lblIdadeUser = new JLabel(user[1]);
		panel.add(lblIdadeUser);
		
		JLabel lblFuncao = new JLabel("Funcao:");
		panel.add(lblFuncao);

		JLabel lblFuncaoUser = new JLabel(user[2]);
		panel.add(lblFuncaoUser);
		
		JLabel lblSalario = new JLabel("Salario:");
		panel.add(lblSalario);

		JLabel lblSalarioUser = new JLabel(user[3]+" €");
		panel.add(lblSalarioUser);
		
		return JOptionPane.showOptionDialog(null, panel, lblNomeUser.getText(), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}
	
	private void refreshFuncionarios(DefaultTableModel modelFuncionarios, String nomeArmazem) {
		int rowcont = modelFuncionarios.getRowCount();
		for (int i=rowcont-1;i>=0;i--) {
			modelFuncionarios.removeRow(i);
		}
		db.nifIdadeNomeFuncaoFuncionario(nomeArmazem,modelFuncionarios);
	}



	public JPanel getFuncionarios() {
		return funcionarios;
	}
	
	
}
