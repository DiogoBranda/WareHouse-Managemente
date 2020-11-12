package gui.gui_user.maquinas;
import logic.Check;
import logic.MessageLogs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.DataBase;
import gui.DefaultDesign;
import gui.PopUp;
import gui.gui_user.UserDesign;
import gui.menu_bar.MenuBar;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Maquinas {
	private static final String REPORT = "/img/reportMaquina.png";
	private static final String REFRESH = "/img/refresh.png";

	private DataBase db;
	private MessageLogs messageLogs;
	private JTextArea descmaqField;
	private PopUp popUp;
	private Check check;	
	private String loginUsername;
	
	private JSeparator separator;
	private JLabel lblMaquinas;
	private JScrollPane scrollPane;
	private JPanel maquinasMenu;
	private JButton btnRefreshMaquinas;
	private JButton btnHomeMaquinas;
	private JButton btnReportarAvaria;
	private JTable tableMaquinas;
	private DefaultTableModel modelMaquinas;
	private JToggleButton tglbtnFiltrarAvariadas;
	private TableRowSorter<DefaultTableModel> sorterMaquinas;
	private JButton btnCorrigirAvaria;
	private JTextField maquinaSearch;
	private JPanel maquinas;
	private MenuBar menuBar;
	private CardLayout cl;
	
	public Maquinas(String username, MenuBar menuBar) {
		this.popUp = new PopUp();
		this.check = new Check();
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
		this.menuBar = menuBar;
	}
	
	private void criaTabela() {
		modelMaquinas = new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "ID", "Avariado" }) {
			
			private static final long serialVersionUID = -96636141310423198L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, Boolean.class };

			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableMaquinas = new JTable();
		tableMaquinas.setModel(modelMaquinas);
		tableMaquinas.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableMaquinas.getColumnModel().getColumn(0).setMaxWidth(150);

		tableMaquinas.getTableHeader().setReorderingAllowed(false);
		tableMaquinas.setAutoCreateRowSorter(true);// para ordenar
		tableMaquinas.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	private void criaMaquinasSearch() {
		sorterMaquinas = new TableRowSorter<>(modelMaquinas);
		tableMaquinas.setRowSorter(sorterMaquinas);

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
					sorterMaquinas.setRowFilter(null);
				} else {
					sorterMaquinas.setRowFilter(RowFilter.regexFilter("(?i)" + str));
				}
			}
		});

	}
	private void criaBotoesMaquinas() {
		btnRefreshMaquinas = new JButton("Refresh");
		DefaultDesign.styleBotaoSimples(btnRefreshMaquinas, REFRESH);

		btnHomeMaquinas = new JButton("Home");
		DefaultDesign.styleBotaoHome(btnHomeMaquinas);

		btnCorrigirAvaria = new JButton("Corrigir Avaria");
		DefaultDesign.styleBotaoSimples(btnCorrigirAvaria, null);

		btnReportarAvaria = new JButton("Reportar Avaria");
		DefaultDesign.styleBotaoSimples(btnReportarAvaria, null);
		
	}
	private GroupLayout putMaquinasLayout() {
		GroupLayout glMaquinasMenu = new GroupLayout(maquinasMenu);
		glMaquinasMenu.setHorizontalGroup(
			glMaquinasMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(glMaquinasMenu.createSequentialGroup()
					.addGap(39)
					.addGroup(glMaquinasMenu.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMaquinas)
						.addGroup(glMaquinasMenu.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnHomeMaquinas, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnRefreshMaquinas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(glMaquinasMenu.createParallelGroup(Alignment.TRAILING)
						.addGroup(glMaquinasMenu.createSequentialGroup()
							.addComponent(tglbtnFiltrarAvariadas)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReportarAvaria)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCorrigirAvaria, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(maquinaSearch, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 565, GroupLayout.PREFERRED_SIZE))
					.addGap(55))
		);
		glMaquinasMenu.setVerticalGroup(
			glMaquinasMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(glMaquinasMenu.createSequentialGroup()
					.addGroup(glMaquinasMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(glMaquinasMenu.createSequentialGroup()
							.addGap(49)
							.addComponent(lblMaquinas, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(59)
							.addComponent(btnRefreshMaquinas)
							.addGap(18)
							.addComponent(btnHomeMaquinas, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(glMaquinasMenu.createSequentialGroup()
							.addGap(88)
							.addGroup(glMaquinasMenu.createParallelGroup(Alignment.BASELINE)
								.addComponent(maquinaSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCorrigirAvaria)
								.addComponent(btnReportarAvaria)
								.addComponent(tglbtnFiltrarAvariadas))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
					.addContainerGap())
		);

		return glMaquinasMenu;
	}
	
	public void showMaquinasMenu(JFrame frame, CardLayout cl) {
		this.cl = cl;
		maquinas = new JPanel();
		frame.getContentPane().add(maquinas, "name_111064051438100");
		maquinas.setLayout(new BorderLayout(0, 0));

		maquinasMenu = new JPanel();
		maquinas.add(maquinasMenu, BorderLayout.CENTER);

		lblMaquinas = new JLabel("Maquinas");
		DefaultDesign.styleLabel28(lblMaquinas);
		
		scrollPane = new JScrollPane();

		tglbtnFiltrarAvariadas = new JToggleButton("Filtrar Avariadas");
		DefaultDesign.styleToggleSimples(tglbtnFiltrarAvariadas, null);
		SwingUtilities.updateComponentTreeUI(tglbtnFiltrarAvariadas);

		separator = new JSeparator();
		DefaultDesign.styleSeparator(separator);
		
		maquinaSearch = new JTextField();
		DefaultDesign.styleSearch(maquinaSearch);
		
		criaBotoesMaquinas();
		
		GroupLayout glMaquinasMenu = putMaquinasLayout();
		
		criaTabela();
		scrollPane.setViewportView(tableMaquinas);
		maquinasMenu.setLayout(glMaquinasMenu);


		db.nomenumeroestadoMaquina(menuBar.getNomeArmazem(), modelMaquinas);

		criaMaquinasSearch();
		botoesMaquinas(frame);
	}

	private void botoesMaquinas(JFrame frame) {
		

		btnHomeMaquinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "userDesign");
			}
		});

		tglbtnFiltrarAvariadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshMaquinas(tglbtnFiltrarAvariadas.isSelected());

			}
		});
		btnCorrigirAvaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				corrigeAvaria();
			}
		});
		
		btnReportarAvaria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reportAvariaMaquina(tglbtnFiltrarAvariadas.isSelected());
			}
		});
		btnRefreshMaquinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshMaquinas(tglbtnFiltrarAvariadas.isSelected());
			}
		});
	}
	
	private int showReportAvariaPopUp() {
		Object[] options1 = { "Recebido", "Sair" };
		ImageIcon icon = new ImageIcon(UserDesign.class.getResource(REPORT));
		
		JSplitPane splitPane = new JSplitPane();
		
		
		JLabel lbldescmaq = new JLabel("Descricao");
		lbldescmaq.setFont(new Font("Tahoma", Font.BOLD, 11));
		JScrollPane scrollPane = new JScrollPane();
		descmaqField = new  JTextArea(4, 20);
		scrollPane.setViewportView(descmaqField);
		splitPane.setLeftComponent(lbldescmaq);
		
		splitPane.setRightComponent(scrollPane);
		splitPane.setEnabled(false);
		
		return JOptionPane.showOptionDialog(null, splitPane, "Reportar Avaria", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
	}
	
	private void refreshMaquinas(boolean tglbtnFiltrarAvariadasisSelected) {
		int rowcont = modelMaquinas.getRowCount();
		for (int i=rowcont-1;i>=0;i--) {
			modelMaquinas.removeRow(i);
		}
		String nomeArmazem = menuBar.getNomeArmazem();
		if(tglbtnFiltrarAvariadasisSelected)
			db.nomenumeroAvariadasMaquina( nomeArmazem,modelMaquinas);
		else	
			db.nomenumeroestadoMaquina(nomeArmazem,modelMaquinas);
	}
	
	private void reportAvariaMaquina(boolean tglbtnFiltrarAvariadasisSelected) {

		boolean isFinished= false;
		int[] indexOfRow = tableMaquinas.getSelectedRows();
		if (check.multipleSelection(indexOfRow)) {
			popUp.showPopUp("Selecione apenas 1 linha", "Erro ao selecionar");
			return;
		}
		while (!isFinished) {
			int result = showReportAvariaPopUp();// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
				isFinished = true;
			} else{
				break;
			}
		}

		if(isFinished) {
			/*Saber se maquina avarida e id armazem*/
			String nomeArmazem=menuBar.getNomeArmazem();
			String nomeMaquina = tableMaquinas.getValueAt(indexOfRow[0], 0).toString();
			String id=tableMaquinas.getValueAt(indexOfRow[0], 1).toString();
			String descricao = descmaqField.getText();
			db.updateavariadoMaquina(nomeArmazem, id, descricao);
			messageLogs.reportaAvaria(loginUsername+";"+false+";"+nomeArmazem, nomeMaquina, id, descricao);
			refreshMaquinas(tglbtnFiltrarAvariadasisSelected);
		}
	}

	private void corrigeAvaria() {
		int[] indexOfRow = tableMaquinas.getSelectedRows();
		for(int i=0; i < indexOfRow.length; i++) {
			String nomeMaquina = tableMaquinas.getValueAt(indexOfRow[i], 0).toString();
			String id = tableMaquinas.getValueAt(indexOfRow[i], 1).toString();
			db.corrigeAvariaMaquina(id);
			messageLogs.corrigeAvaria(loginUsername+";"+false+";"+menuBar.getNomeArmazem(), nomeMaquina, id);
			modelMaquinas.setValueAt(false, indexOfRow[i], 2);
		}
	}

	public JPanel getMaquinas() {
		return maquinas;
	}
	
	
}
