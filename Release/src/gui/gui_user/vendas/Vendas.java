package gui.gui_user.vendas;
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
public class Vendas {
	private static final String REFRESH = "/img/refresh.png";

	private JPanel vendas;
	private JTable tableVendas;
	private JSeparator separatorVendas;
	private JLabel lblVendas;
	private JPanel vendasMenu;
	private JScrollPane scrollPane;
	private JButton btnRefreshVendas;
	private JButton btnHomeVendas;
	private DefaultTableModel modelVendas;
	private TableRowSorter<DefaultTableModel> sorterVendas;
	private CardLayout cl;
 
	private void criaTabela() {
		tableVendas = new JTable();
		tableVendas.setModel(modelVendas);
		tableVendas.getColumnModel().getColumn(0).setResizable(true);
		tableVendas.getColumnModel().getColumn(0).setMinWidth(140);
		tableVendas.getColumnModel().getColumn(0).setMaxWidth(150);
		tableVendas.getColumnModel().getColumn(1).setResizable(true);
		tableVendas.getColumnModel().getColumn(1).setMinWidth(90);
		tableVendas.getColumnModel().getColumn(1).setMaxWidth(120);
		tableVendas.getColumnModel().getColumn(2).setMinWidth(90);
		tableVendas.getColumnModel().getColumn(2).setMaxWidth(120);
		tableVendas.getColumnModel().getColumn(3).setMinWidth(90);
		tableVendas.getColumnModel().getColumn(3).setMaxWidth(120);
		
		tableVendas.getTableHeader().setReorderingAllowed(false);
		tableVendas.setAutoCreateRowSorter(true);// para ordenar
		tableVendas.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	}
 	private void criaVendasSearch(JTextField vendasSearch) {
	sorterVendas = new TableRowSorter<>(modelVendas);
		tableVendas.setRowSorter(sorterVendas);

		vendasSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				vendasSearch.setText("");
			}
		});
		vendasSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(vendasSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(vendasSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(vendasSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterVendas.setRowFilter(null);
				} else {
					sorterVendas.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesVendas() {
		btnRefreshVendas = new JButton("Refresh");
		DefaultDesign.styleBotaoSimples(btnRefreshVendas, REFRESH);

		btnHomeVendas = new JButton("Home");
		DefaultDesign.styleBotaoHome(btnHomeVendas);

	}
	private GroupLayout putVendasLayout(JTextField vendasSearch) {
		GroupLayout glVendasMenu = new GroupLayout(vendasMenu);
		glVendasMenu.setHorizontalGroup(glVendasMenu.createParallelGroup(Alignment.LEADING).addGroup(glVendasMenu
				.createSequentialGroup().addGap(48)
				.addGroup(glVendasMenu.createParallelGroup(Alignment.LEADING, false).addGroup(glVendasMenu
						.createSequentialGroup()
						.addGroup(glVendasMenu.createParallelGroup(Alignment.LEADING)
								.addGroup(glVendasMenu.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnHomeVendas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnRefreshVendas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(separatorVendas, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
						.addGap(134)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 494, GroupLayout.PREFERRED_SIZE))
						.addGroup(glVendasMenu.createSequentialGroup().addComponent(lblVendas)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(vendasSearch, GroupLayout.PREFERRED_SIZE, 118,
										GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(60, Short.MAX_VALUE)));
		glVendasMenu.setVerticalGroup(glVendasMenu.createParallelGroup(Alignment.LEADING).addGroup(glVendasMenu
				.createSequentialGroup().addGap(42)
				.addGroup(glVendasMenu.createParallelGroup(Alignment.TRAILING).addComponent(lblVendas).addComponent(
						vendasSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(glVendasMenu.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
						.addGroup(glVendasMenu.createSequentialGroup()
								.addComponent(separatorVendas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(39)
								.addComponent(btnRefreshVendas, GroupLayout.PREFERRED_SIZE, 33,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(btnHomeVendas, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));

		return glVendasMenu;
	}
	public void showVendasMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		vendas = new JPanel();
		frame.getContentPane().add(vendas, "name_148919274819900");
		vendas.setLayout(new BorderLayout(0, 0));

		vendasMenu = new JPanel();
		vendas.add(vendasMenu, BorderLayout.CENTER);

		lblVendas = new JLabel("Vendas");
		DefaultDesign.styleLabel(lblVendas);
		criaBotoesVendas();
	
		scrollPane = new JScrollPane();

		separatorVendas = new JSeparator();
		DefaultDesign.styleSeparator(separatorVendas);
		
		JTextField vendasSearch = new JTextField();
		DefaultDesign.styleSearch(vendasSearch);
		
		GroupLayout glVendasMenu = putVendasLayout(vendasSearch);

		modelVendas = new DefaultTableModel(new Object[][] {},
				new String[] { "data Saida", "SKU", "Produto", "destino" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		criaTabela();
		scrollPane.setViewportView(tableVendas);
		vendasMenu.setLayout(glVendasMenu);
		DataBase db = DataBase.getInstance();
		db.produtoVendido(modelVendas);
		criaVendasSearch(vendasSearch);
		botoesVendas(frame);
	}

	private void botoesVendas(JFrame frame) {
		

		// Menu Vendas
		btnHomeVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "userDesign");
			}
		});
		btnRefreshVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshVendas(modelVendas);
			}
		});

	}

	public JPanel getVendas() {
		return vendas;
	}

	public void refreshVendas(DefaultTableModel modelVendas){
		int rowcont = modelVendas.getRowCount();
		for (int i=rowcont-1;i>=0;i--) {
			modelVendas.removeRow(i);
		}
		DataBase db = DataBase.getInstance();
		db.produtoVendido(modelVendas);
	}

}
