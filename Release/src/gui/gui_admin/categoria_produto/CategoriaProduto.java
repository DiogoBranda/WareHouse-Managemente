package gui.gui_admin.categoria_produto;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import db.DataBase;
import gui.AutoCompletion;
import gui.DefaultDesign;
import gui.PopUp;
import gui.gui_admin.Admin;
import gui.menu_bar.MenuBar;
import logic.Check;
import logic.MessageLogs;

public class CategoriaProduto {
	private static final String MENUADMINSTRING = "menu_admin";
	private static final String ADD = "/img/add.png";
	private static final String REMOVE = "/img/remove.png";
	private static final String REFRESH = "/img/refresh.png";
	private static final String REFRESHSTRING = "Refresh";
	private static final String REMOVERSTRING = "Remover";
	private DataBase db;// DATA BASE
	private MessageLogs messageLogs;
	private static final String CATEGORIA = "/img/list.png";
	private PopUp popUp;
	private Check check;
	private DefaultTableModel modelCategoriaProduto;
	private DefaultTableModel modelSubCategoriaProduto;
	private JTable subCategoriaProdutoTable;
	private JTable categoriaProdutoTable;
	private String loginUsername;
	private JPanel categoriaProduto;

	private JLabel categoriaProdutoTexto;
	private JSeparator categoriaProdutoSeparator;
	private JScrollPane categoriaProdutoScrollPane;
	private JScrollPane categoriaProdutoScrollPane2;
	private JPanel categoriaProdutoPanel;
	private JButton categoriaProdutoBtnAdicionar;
	private JButton categoriaProdutoBtnRemover;
	private JButton categoriaProdutoBtnRefresh;
	private JButton categoriaProdutoBtnHome;
	private CardLayout cl;
	private MenuBar menuBar;
	
	/* POP UP */
	private JTextField nomeField;
	private JComboBox<String> comboBoxCategoria;
	private JComboBox<String> comboBox;

	public CategoriaProduto(String username, MenuBar menuBar) {
		this.popUp = new PopUp();
		this.check = new Check();
		nomeField = new JTextField();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
		criaTabela();

	}
	private DefaultTableModel criaCategoriaModel() {
		return new DefaultTableModel(new Object[][] {}, new String[] { "Categoria" }) {

			private static final long serialVersionUID = -4871994548676222605L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}
	private DefaultTableModel criaSubCategoriaModel() {
		return new DefaultTableModel(new Object[][] {}, new String[] { "Sub-Categoria" }) {
			private static final long serialVersionUID = 7212103990277148587L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class };

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}
	
	private void criaTabela() {
		modelCategoriaProduto = criaCategoriaModel();
		modelSubCategoriaProduto = criaSubCategoriaModel();
		
		subCategoriaProdutoTable = new JTable();
		categoriaProdutoTable = new JTable();
		subCategoriaProdutoTable.setModel(modelSubCategoriaProduto);
		categoriaProdutoTable.setModel(modelCategoriaProduto);

		/* Para nao mover */
		subCategoriaProdutoTable.getTableHeader().setReorderingAllowed(false);
		subCategoriaProdutoTable.setAutoCreateRowSorter(true);// para ordenar
		subCategoriaProdutoTable.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		/* Para nao mover */
		categoriaProdutoTable.getTableHeader().setReorderingAllowed(false);
		categoriaProdutoTable.setAutoCreateRowSorter(true);// para ordenar
		categoriaProdutoTable.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	}
	private void criaBotoesCategoria() {
		categoriaProdutoBtnAdicionar = new JButton("Adicionar");
		DefaultDesign.styleBotaoSimples(categoriaProdutoBtnAdicionar, ADD);

		categoriaProdutoBtnRemover = new JButton(REMOVERSTRING);
		DefaultDesign.styleBotaoSimples(categoriaProdutoBtnRemover, REMOVE);
		
		categoriaProdutoBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(categoriaProdutoBtnRefresh, REFRESH);

		categoriaProdutoBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(categoriaProdutoBtnHome);
	}
	private GroupLayout putCategoriaLayout() {
		GroupLayout glCategoriaProdutoPanel = new GroupLayout(categoriaProdutoPanel);
		glCategoriaProdutoPanel.setHorizontalGroup(
				glCategoriaProdutoPanel.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						glCategoriaProdutoPanel.createSequentialGroup().addGap(37).addGroup(glCategoriaProdutoPanel
								.createParallelGroup(Alignment.LEADING).addComponent(categoriaProdutoTexto)
								.addGroup(glCategoriaProdutoPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(categoriaProdutoSeparator, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(glCategoriaProdutoPanel.createSequentialGroup().addGap(15)
												.addGroup(glCategoriaProdutoPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(categoriaProdutoBtnRefresh,
																GroupLayout.PREFERRED_SIZE, 113,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(categoriaProdutoBtnHome,
																GroupLayout.PREFERRED_SIZE, 113,
																GroupLayout.PREFERRED_SIZE)))))
								.addGap(99)
								.addGroup(glCategoriaProdutoPanel.createParallelGroup(Alignment.LEADING, false)
										.addGroup(glCategoriaProdutoPanel.createSequentialGroup()
												.addComponent(categoriaProdutoBtnAdicionar)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(categoriaProdutoBtnRemover, GroupLayout.PREFERRED_SIZE,
														113, GroupLayout.PREFERRED_SIZE))
										.addComponent(categoriaProdutoScrollPane, GroupLayout.DEFAULT_SIZE, 414,
												Short.MAX_VALUE)
										.addComponent(categoriaProdutoScrollPane2, 0, 0, Short.MAX_VALUE))
								.addContainerGap(91, Short.MAX_VALUE)));
		glCategoriaProdutoPanel.setVerticalGroup(glCategoriaProdutoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glCategoriaProdutoPanel.createSequentialGroup().addGroup(glCategoriaProdutoPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(glCategoriaProdutoPanel.createSequentialGroup().addContainerGap()
								.addComponent(categoriaProdutoTexto).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(categoriaProdutoSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(54)
								.addComponent(categoriaProdutoBtnRefresh, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(categoriaProdutoBtnHome, GroupLayout.PREFERRED_SIZE, 59,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(glCategoriaProdutoPanel.createSequentialGroup().addGap(26)
								.addGroup(glCategoriaProdutoPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(categoriaProdutoBtnAdicionar, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(categoriaProdutoBtnRemover, GroupLayout.PREFERRED_SIZE, 40,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(categoriaProdutoScrollPane, GroupLayout.PREFERRED_SIZE, 117,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(categoriaProdutoScrollPane2, GroupLayout.PREFERRED_SIZE, 117,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(34, Short.MAX_VALUE)));
		return glCategoriaProdutoPanel;
	}
	public void showCategoriaProduto(JFrame frame, CardLayout cl) {
		this.cl = cl;
		categoriaProduto = new JPanel();
		frame.getContentPane().add(categoriaProduto, "name_1323741143870400");
		categoriaProduto.setLayout(new BorderLayout(0, 0));
		categoriaProdutoPanel = new JPanel();
		categoriaProduto.add(categoriaProdutoPanel, BorderLayout.CENTER);

		categoriaProdutoTexto = new JLabel("<html>Categoria<br>Produto</html>");
		DefaultDesign.styleLabel28(categoriaProdutoTexto);
		
		categoriaProdutoSeparator = new JSeparator();
		DefaultDesign.styleSeparator(categoriaProdutoSeparator);
		
		categoriaProdutoScrollPane = new JScrollPane();
		categoriaProdutoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		categoriaProdutoScrollPane2 = new JScrollPane();
		categoriaProdutoScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		criaBotoesCategoria();
		
		GroupLayout glCategoriaProdutoPanel = putCategoriaLayout();
		
		categoriaProdutoScrollPane2.setViewportView(subCategoriaProdutoTable);
		categoriaProdutoScrollPane.setViewportView(categoriaProdutoTable);
		categoriaProdutoPanel.setLayout(glCategoriaProdutoPanel);

		db.categoriaProduto(modelCategoriaProduto);
		buttonsCategoria(frame);
	}
	private void buttonsCategoria(JFrame frame) {
		categoriaProdutoBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);
			}
		});
		categoriaProdutoBtnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCategoria(modelCategoriaProduto, modelSubCategoriaProduto, menuBar.getNomeArmazem());
			}
		});
		categoriaProdutoBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh(modelCategoriaProduto, modelSubCategoriaProduto);
			}
		});
		categoriaProdutoBtnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeCategoria(categoriaProdutoTable, subCategoriaProdutoTable,
						modelCategoriaProduto, modelSubCategoriaProduto, menuBar.getNomeArmazem());
			}
		});

		/* listener de quando o selection muda */
		categoriaProdutoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				showSubCategoria(categoriaProdutoTable, modelCategoriaProduto,
						modelSubCategoriaProduto);

			}
		});
		/* listener das teclas */
		categoriaProdutoTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeCategoria(categoriaProdutoTable, subCategoriaProdutoTable,
							modelCategoriaProduto, modelSubCategoriaProduto, menuBar.getNomeArmazem());

			}
		});
		subCategoriaProdutoTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_DELETE)
					removeCategoria(categoriaProdutoTable, subCategoriaProdutoTable,
							modelCategoriaProduto, modelSubCategoriaProduto, menuBar.getNomeArmazem());

			}
		});

		categoriaProdutoPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				categoriaProdutoTable.clearSelection();
				subCategoriaProdutoTable.clearSelection();

			}
		});
	}
	

	public JPanel getCategoriaProduto() {
		return categoriaProduto;
	}
	private boolean confirmData() {
		nomeField.setBorder(new JTextField().getBorder());

		/* Confirma dados */
		if (!check.textValidWithoutNumbers(nomeField.getText())) {
			popUp.showPopUp("Por favor introduzir um nome válido", "Nome");
			nomeField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return true;

	}

	private void showSubCategoria(JTable categoriaTable, DefaultTableModel modelCategoria,
			DefaultTableModel modelSubCategoria) {
		int rowCount2 = modelSubCategoria.getRowCount();

		for (int i = rowCount2 - 1; i >= 0; i--) {
			modelSubCategoria.removeRow(i);
		}
		if (categoriaTable.getSelectedRow() >= 0) {
			String categoria = modelCategoria
					.getValueAt((categoriaTable.convertRowIndexToModel(categoriaTable.getSelectedRow())), 0).toString();
			db.subcategoriaSubCategoria(modelSubCategoria, categoria);

		}
	}

	/* User POP UP Adicionar */
	private int showCategoriaPopUpAdicionar(DefaultTableModel modelCategoria, String name) {
		Object[] options1 = { "Adicionar", "Sair" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(CATEGORIA));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 10, 10));

		comboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> modelCombo = new DefaultComboBoxModel<String>(
				new String[] { "Categoria", "Sub-Categoria" });
		comboBox.setModel(modelCombo);

		panel.add(comboBox);

		comboBoxCategoria = new JComboBox<String>();
		DefaultComboBoxModel<String> modelComboSub = new DefaultComboBoxModel<String>(new String[] {});
		comboBoxCategoria.setModel(modelComboSub);
		comboBoxCategoria.setEnabled(false);
		AutoCompletion.enable(comboBoxCategoria);

		panel.add(comboBoxCategoria);

		/* Mete todas as sub categorias na comboBox */
		for (int i = 0; i < modelCategoria.getRowCount(); i++)
			modelComboSub.addElement(modelCategoria.getValueAt(i, 0).toString());

		JLabel lbl = new JLabel("Nome da " + comboBox.getSelectedItem());
		lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lbl);

		panel.add(nomeField);
		nomeField.setColumns(15);
		nomeField.setToolTipText("Nome " + comboBox.getSelectedItem());
		nomeField.setText(name);

		/* Serve para mudar o texto do "lblnome" dependendo da escolha */
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				nomeField.setToolTipText("Nome " + comboBox.getSelectedItem());
				if (comboBox.getSelectedItem().equals("Sub-Categoria")) {
					comboBoxCategoria.setEnabled(true);
					lbl.setText("Nome da " + comboBox.getSelectedItem());

				} else {
					comboBoxCategoria.setEnabled(false);
					lbl.setText("Nome da " + comboBox.getSelectedItem());

				}
			}
		});

		return JOptionPane.showOptionDialog(null, panel, "Adicionar fornecedor", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}

	

	private void adicionarCategoria(DefaultTableModel modelCategoria, DefaultTableModel modelSubCategoria,
			String armazem) {
		boolean isFinished = false;
		String name = null;
		while (!isFinished) {
			int result = showCategoriaPopUpAdicionar(modelCategoria, name);// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
				isFinished = confirmData();
				name = nomeField.getText();
			} else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
				break;
			}
		}
		if (isFinished) {
			if (comboBox.getSelectedItem().toString().equals("Categoria")) {
				db.addCategoriaProduto(name, armazem);
				messageLogs.adicionaCategoria(loginUsername+";"+true+";"+armazem, name, null);
				modelCategoria.addRow(new Object[] { name });

			} else {
				db.addSubCategoria(name, comboBoxCategoria.getSelectedItem().toString());// sub categoria, categoria
				messageLogs.adicionaCategoria(loginUsername+";"+true+";"+armazem, comboBoxCategoria.getSelectedItem().toString(), name);
				modelSubCategoria.addRow(new Object[] { name });
			}
		}

	}

	private void eliminaSubCategoria(int[] selectedRows2, JTable subCategoriaTable,
			DefaultTableModel modelSubCategoria, String categoria, String armazem) {
		for (int i = selectedRows2.length - 1; i >= 0; i--) {
			int index= subCategoriaTable.convertRowIndexToModel(selectedRows2[i]);
			String subCategoria = (String)modelSubCategoria.getValueAt(index,0);
			if (db.removeSubCategoria(subCategoria, categoria)) {
				messageLogs.removeCategoria(loginUsername+";"+true+";"+armazem, categoria, subCategoria);
				modelSubCategoria.removeRow(index);
			}else{
				popUp.showPopUpErroEliminar();
				break;
			}
		}
	}

	private void eliminaCategoria(int[] selectedRows, JTable categoriaTable, DefaultTableModel modelCategoria, String armazem) {
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			String categoria= (String)modelCategoria.getValueAt(categoriaTable.convertRowIndexToModel(selectedRows[i]),0);
			if (db.removeCategoriaProduto(categoria)) {
				messageLogs.removeCategoria(loginUsername+";"+true+";"+armazem,categoria, null);
				modelCategoria.removeRow(categoriaTable.convertRowIndexToModel(selectedRows[i]));
			}else{
				popUp.showPopUpErroEliminar();
				break;
			}
		}
	}

	private void removeCategoria(JTable categoriaTable, JTable subCategoriaTable, DefaultTableModel modelCategoria,
			DefaultTableModel modelSubCategoria, String armazem) {
		int option = 0;
		int[] selectedRows = categoriaTable.getSelectedRows();
		int[] selectedRows2 = subCategoriaTable.getSelectedRows();
		int selected = check.selectedIndex(selectedRows, selectedRows2);

		if (selected == 3) {
			popUp.showPopUp("Precisa de selecionar pelo menos 1 linha", "Erro ao eliminar");
			return;
		} else if (selected == -1) {
			popUp.showPopUp("Selecione só 1 categoria e depois uma ou mais sub categorias", "Erro ao eliminar");
			return;
		}

		if (selected == 1) {
			option = popUp.showPopUpConfirmation(
					"Tem a certeza que deseja apagar " + (selectedRows.length) + " categorias?", "Eliminar categorias");
		} else if (selected == 2) {
			option = popUp.showPopUpConfirmation(
					"Tem a certeza que deseja apagar " + (selectedRows2.length) + " sub categorias?",
					"Eliminar sub categorias");
		}

		if (option == JOptionPane.YES_OPTION) {
			/* quando quer eliminar uma sub categoria pertencente a uma CATEGORIA */
			if (selected == 2) {
				String categoria=(String)modelCategoria.getValueAt(categoriaTable.convertRowIndexToModel(selectedRows[0]), 0);
				eliminaSubCategoria(selectedRows2, subCategoriaTable, modelSubCategoria, categoria, armazem);
				/* quando quer eliminar uma categoria */
			} else if (selected == 1) {
				eliminaCategoria(selectedRows, categoriaTable, modelCategoria, armazem);
				if (selectedRows.length > 0)
					categoriaTable.getSelectionModel().setSelectionInterval(0, 0);
			}

		}

	}

	private void refresh(DefaultTableModel modelCategoriaProduto, DefaultTableModel modelSubCategoria) {
		int rowCount = modelCategoriaProduto.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			modelCategoriaProduto.removeRow(i);
		}
		int rowCount2 = modelSubCategoria.getRowCount();
		for (int i = rowCount2 - 1; i >= 0; i--) {
			modelSubCategoria.removeRow(i);
		}

		db.categoriaProduto(modelCategoriaProduto);
	}

}
