package gui.gui_user.produto;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.DataBase;
import gui.DefaultDesign;
import gui.menu_bar.MenuBar;
import logic.MessageLogs;

public class Produtos {
	private static final String REFRESH = "/img/refresh.png";

	private DataBase db;
	private MessageLogs messageLogs;
	private String loginUsername;
	private JPanel produtosMenu;
	private JLabel lblProdutos;
	private JScrollPane scrollPaneProdutos;
	private JSeparator separatorProdutos;
	private JButton btnRefreshProdutos;
	private JButton btnHomeProdutos;
	private DefaultTableModel modelProdutos;
	private JTable tableProdutos;
	private TableRowSorter<DefaultTableModel> sorterProdutos;
	private JButton btnMarcarDefeitoProdutos;
	private JPanel produtos;
	private JTextField produtosSearch;
	private CardLayout cl;
	private MenuBar menuBar;
	
 	public Produtos(String username, MenuBar menuBar) {
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
	}
	
	private void criaProdutosSearch() {

		sorterProdutos = new TableRowSorter<>(modelProdutos);
		tableProdutos.setRowSorter(sorterProdutos);

		produtosSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				produtosSearch.setText("");
			}
		});
		produtosSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(produtosSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(produtosSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(produtosSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterProdutos.setRowFilter(null);
				} else {
					sorterProdutos.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesProdutos() {
		btnRefreshProdutos = new JButton("Refresh");
		DefaultDesign.styleBotaoSimples(btnRefreshProdutos, REFRESH);

		btnHomeProdutos = new JButton("Home");
		DefaultDesign.styleBotaoHome(btnHomeProdutos);
		
		btnMarcarDefeitoProdutos = new JButton("Marcar Defeito");
		DefaultDesign.styleBotaoSimples(btnMarcarDefeitoProdutos, null);
	}
	private GroupLayout putProdutosLayout() {
		GroupLayout glProdutosMenu = new GroupLayout(produtosMenu);
		glProdutosMenu.setHorizontalGroup(glProdutosMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(glProdutosMenu.createSequentialGroup().addGap(40).addGroup(glProdutosMenu
						.createParallelGroup(Alignment.LEADING)
						.addGroup(glProdutosMenu.createParallelGroup(Alignment.LEADING).addGroup(glProdutosMenu
								.createSequentialGroup()
								.addGroup(glProdutosMenu.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnHomeProdutos, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
										.addComponent(btnRefreshProdutos, Alignment.LEADING))
								.addPreferredGap(ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
								.addGroup(glProdutosMenu.createParallelGroup(Alignment.LEADING, false).addGroup(
										Alignment.TRAILING,
										glProdutosMenu.createSequentialGroup().addComponent(btnMarcarDefeitoProdutos)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(produtosSearch, GroupLayout.PREFERRED_SIZE, 120,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPaneProdutos, GroupLayout.PREFERRED_SIZE, 572,
												GroupLayout.PREFERRED_SIZE))
								.addGap(38))
								.addGroup(glProdutosMenu.createSequentialGroup()
										.addComponent(separatorProdutos, GroupLayout.PREFERRED_SIZE, 132,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(677, Short.MAX_VALUE)))
						.addGroup(glProdutosMenu.createSequentialGroup().addComponent(lblProdutos).addContainerGap(678,
								Short.MAX_VALUE)))));
		glProdutosMenu.setVerticalGroup(glProdutosMenu.createParallelGroup(Alignment.LEADING).addGroup(glProdutosMenu
				.createSequentialGroup().addGap(41)
				.addComponent(lblProdutos, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(glProdutosMenu.createParallelGroup(Alignment.LEADING).addGroup(glProdutosMenu
						.createSequentialGroup()
						.addGroup(glProdutosMenu.createParallelGroup(Alignment.BASELINE)
								.addComponent(produtosSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnMarcarDefeitoProdutos))
						.addGap(6)
						.addComponent(scrollPaneProdutos, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(glProdutosMenu.createSequentialGroup()
								.addComponent(separatorProdutos,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(40)
								.addComponent(btnRefreshProdutos, GroupLayout.PREFERRED_SIZE, 33,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(btnHomeProdutos, GroupLayout.PREFERRED_SIZE, 59,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(6, Short.MAX_VALUE)));
		return glProdutosMenu;
	}
	public void showProdutos(JFrame frame, CardLayout cl) {
		this.cl = cl;
		produtos = new JPanel();
		frame.getContentPane().add(produtos, "name_127783532138000");
		produtos.setLayout(new BorderLayout(0, 0));

		produtosMenu = new JPanel();
		produtos.add(produtosMenu, BorderLayout.CENTER);

		lblProdutos = new JLabel("Produtos");
		DefaultDesign.styleLabel28(lblProdutos);
		
		scrollPaneProdutos = new JScrollPane();

		separatorProdutos = new JSeparator();
		DefaultDesign.styleSeparator(separatorProdutos);
		produtosSearch = new JTextField();
		DefaultDesign.styleSearch(produtosSearch);
		criaBotoesProdutos();
		GroupLayout glProdutosMenu =putProdutosLayout();

		modelProdutos = new DefaultTableModel(new Object[][] {},
				new String[] { "SKU", "Produto", "Origem", "Com Defeito" }) {

			private static final long serialVersionUID = 60845133227382893L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Boolean.class };

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

		tableProdutos = new JTable();

		tableProdutos.setModel(modelProdutos);
		tableProdutos.getColumnModel().getColumn(0).setMinWidth(120);
		tableProdutos.getColumnModel().getColumn(0).setMaxWidth(150);

		tableProdutos.getColumnModel().getColumn(1).setMinWidth(120);

		tableProdutos.getColumnModel().getColumn(2).setMinWidth(90);
		tableProdutos.getColumnModel().getColumn(3).setMinWidth(90);
		tableProdutos.getColumnModel().getColumn(3).setMaxWidth(90);

		tableProdutos.getTableHeader().setReorderingAllowed(false);
		tableProdutos.setAutoCreateRowSorter(true);// para ordenar
		tableProdutos.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPaneProdutos.setViewportView(tableProdutos);
		produtosMenu.setLayout(glProdutosMenu);
		
		db.produtoNaoVendido(modelProdutos);
		criaProdutosSearch();
		botoesProdutos(frame);
	}
	private void botoesProdutos(JFrame frame) {


		btnMarcarDefeitoProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				marcarDefeito(tableProdutos, modelProdutos, menuBar.getNomeArmazem());
			}
		});
		btnHomeProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "userDesign");
			}
		});

		btnRefreshProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshProdutos(modelProdutos);
			}
		});

	}

	private void marcarDefeito(JTable tableProdutos, DefaultTableModel modelProdutos, String nomeArmazem) {
		int[] selectedRows = tableProdutos.getSelectedRows();

		for (int i = selectedRows.length - 1; i >= 0; i--) {
			String sku = modelProdutos.getValueAt(tableProdutos.convertRowIndexToModel(selectedRows[i]), 0).toString();
			db.marcarDefeitoProduto(sku);
			messageLogs.reportaDefeito(loginUsername+";"+false+";"+nomeArmazem, sku);
		}
		refreshProdutos(modelProdutos);
	}
	private void refreshProdutos(DefaultTableModel modelProdutos){
		int rowcont = modelProdutos.getRowCount();
		for (int i=rowcont-1;i>=0;i--) {
			modelProdutos.removeRow(i);
		}
		db.produtoNaoVendido(modelProdutos);
	}

	public JPanel getProdutos() {
		return produtos;
	}
}
