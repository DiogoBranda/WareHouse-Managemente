package gui.gui_admin.logs;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.DataBase;
import gui.DefaultDesign;
import gui.menu_bar.MenuBar;

public class Logs {	
	private static final String REFRESH = "/img/refresh.png";
	private static final String REFRESHSTRING = "Refresh";
	private static final String MENUADMINSTRING = "menu_admin";

	private JPanel logs;
	private JPanel logsPanel;
	private JLabel lblLogs;
	private JTable logsTable;
	private JSeparator logSeparator;
	private JScrollPane scrollPane;
	private JTextField logsSearch;
	private DefaultTableModel modelLogs;
	private TableRowSorter<DefaultTableModel> sorterLogs;
	private JButton logsBtnRefresh;
	private JButton logsBtnHome;
	private JComboBox<String> numeroLinhasCombo;
	
	private DataBase db;
	private CardLayout cl;
	private MenuBar menuBar;
	public Logs(MenuBar menuBar) {
		this.db = DataBase.getInstance();
		this.menuBar = menuBar;
	}
	
	private void criaComboBox() {
		numeroLinhasCombo = new JComboBox<>();
		numeroLinhasCombo.setToolTipText("Numero de linhas");
		numeroLinhasCombo.setFont(new Font("Consolas", Font.PLAIN, 14));
		numeroLinhasCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"25", "50", "100", "200", "400"}));
	
	}
	private void criaTabela() {
		modelLogs = new DefaultTableModel(new Object[][] {}, 
				new String[] { "Data", "Username", "Admin", "Acao", "Ip" }) {
			/**
					 * 
					 */
					private static final long serialVersionUID = -7481186986491942822L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, Boolean.class, String.class, String.class };

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
		logsTable = new JTable();
		DefaultDesign.styleTabela(logsTable, modelLogs);
	}
	private void criaLogsSearch() {
		sorterLogs = new TableRowSorter<>(modelLogs);
		logsTable.setRowSorter(sorterLogs);

		logsSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				logsSearch.setText("");
			}
		});
		logsSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search(logsSearch.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(logsSearch.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(logsSearch.getText());
			}

			public void search(String str) {
				if (str.length() == 0) {
					sorterLogs.setRowFilter(null);
				} else {
					sorterLogs.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});
		

	}
	private void criaBotoesLogs() {
		logsBtnRefresh = new JButton(REFRESHSTRING);
		DefaultDesign.styleBotaoSimples(logsBtnRefresh, REFRESH);

		logsBtnHome = new JButton("Home");
		DefaultDesign.styleBotaoHome(logsBtnHome);	
	}
	private GroupLayout putLogsLayout() {
		GroupLayout glLogsPanel = new GroupLayout(logsPanel);
		glLogsPanel.setHorizontalGroup(
			glLogsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glLogsPanel.createSequentialGroup()
					.addGap(27)
					.addGroup(glLogsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLogs, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
						.addComponent(logSeparator, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addComponent(logsBtnRefresh, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(logsBtnHome, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addGap(49)
					.addGroup(glLogsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glLogsPanel.createSequentialGroup()
							.addGap(339)
							.addComponent(numeroLinhasCombo, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(logsSearch, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 548, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		glLogsPanel.setVerticalGroup(
			glLogsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glLogsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(glLogsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glLogsPanel.createSequentialGroup()
							.addGap(41)
							.addGroup(glLogsPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(logsSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(numeroLinhasCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(glLogsPanel.createSequentialGroup()
							.addComponent(lblLogs, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(logSeparator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(glLogsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
						.addGroup(glLogsPanel.createSequentialGroup()
							.addGap(35)
							.addComponent(logsBtnRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(logsBtnHome, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		return glLogsPanel;
	}
	public void showLogsMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		logs = new JPanel();
		frame.getContentPane().add(logs, "name_317578181588800");
		logs.setLayout(new BorderLayout(0, 0));

		logsPanel = new JPanel();
		logs.add(logsPanel, BorderLayout.CENTER);

		lblLogs = new JLabel("Logs");
		DefaultDesign.styleLabel(lblLogs);
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		criaBotoesLogs();

		logsSearch = new JTextField();
		DefaultDesign.styleSearch(logsSearch);

		logSeparator = new JSeparator();
		DefaultDesign.styleSeparator(logSeparator);
		
		criaComboBox();
		GroupLayout glLogsPanel = putLogsLayout();
	
		criaTabela();
		scrollPane.setViewportView(logsTable);
		logsPanel.setLayout(glLogsPanel);
		
		db.getLogs(modelLogs, numeroLinhasCombo.getSelectedItem().toString());
		
		criaLogsSearch();
		buttonsLogs(frame);
	}
	private void buttonsLogs(JFrame frame) {
		logsBtnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				menuBar.setCurrentPanel(MENUADMINSTRING);

			}
		});
		logsBtnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = modelLogs.getRowCount() - 1; i >= 0; i--) {
					modelLogs.removeRow(i);
				}
				db.getLogs(modelLogs, numeroLinhasCombo.getSelectedItem().toString());
			}
		});
		numeroLinhasCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				for (int i = modelLogs.getRowCount() - 1; i >= 0; i--) {
					modelLogs.removeRow(i);
				}
				db.getLogs(modelLogs, numeroLinhasCombo.getSelectedItem().toString());
			}
		});
	}

	public JPanel getLogs() {
		return logs;
	}
	

}
