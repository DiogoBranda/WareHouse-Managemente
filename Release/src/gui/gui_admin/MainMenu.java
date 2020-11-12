package gui.gui_admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import gui.DefaultDesign;
import gui.menu_bar.MenuBar;

public class MainMenu {
	private static final String USERMENU = "/img/avatar.png";
	private static final String MACHINEMENU = "/img/machineMin.png";
	private static final String DBMENU = "/img/db.png";
	private static final String CATEGORIAMENU = "/img/listMin.png";
	private static final String FUNCIONARIOMENU = "/img/worker.png";
	private static final String FORNECEDORMENU = "/img/truckMin.png";
	private static final String LOG = "/img/log.png";
	private JLabel lblData;
	private JEditorPane textoAdmin;
	private JSeparator separator1;
	private JSeparator separator;
	private JSeparator separator2;
	private JSeparator separator3;
	private JLabel lblControlo;
	private JPanel menuAdminPanel;
	private JLabel lblAtividade;
	private JLabel lblSegurana;
	private JButton btnUsers;
	private JButton btnLog;
	private JButton btnFuncionario;
	private JButton btnCategoriaProduto;
	private JButton btnFornecedores;
	private JButton btnMaquinas;
	private JButton btnDB;
	private JPanel menuAdmin;
	private CardLayout cl;
	private MenuBar menuBar;
	
	public MainMenu(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	private void criaDesign() {
		lblSegurana =  new JLabel("Seguran\u00E7a");
		DefaultDesign.styleLabelMenu(lblSegurana);
		separator1 = new JSeparator();
		DefaultDesign.styleSeparator(separator1);
		separator = new JSeparator();
		DefaultDesign.styleSeparator(separator);
		separator2 = new JSeparator();
		DefaultDesign.styleSeparator(separator2);
		lblControlo = new JLabel("Controlo");
		DefaultDesign.styleLabelMenu(lblControlo);
		lblData =new JLabel("Data");
		DefaultDesign.styleLabelMenu(lblData);
		lblAtividade = new JLabel("Atividade");
		DefaultDesign.styleLabelMenu(lblAtividade);
		separator3 = new JSeparator();
		DefaultDesign.styleSeparator(separator3);
		textoAdmin = new JEditorPane();
		DefaultDesign.styleTituloMenu(textoAdmin,"Admin");
	}

 	private GroupLayout putMenuLayout() {
		GroupLayout glMenuAdminPanel = new GroupLayout(menuAdminPanel);
		glMenuAdminPanel.setHorizontalGroup(
			glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glMenuAdminPanel.createSequentialGroup()
					.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glMenuAdminPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(textoAdmin, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(glMenuAdminPanel.createSequentialGroup()
							.addGap(57)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSegurana, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.TRAILING)
									.addComponent(separator1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnUsers, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
							.addGap(54)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblControlo, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(separator)
									.addGroup(glMenuAdminPanel.createSequentialGroup()
										.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.TRAILING)
											.addComponent(btnFornecedores, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnMaquinas, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(btnFuncionario, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnCategoriaProduto, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))))
							.addGap(61)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDB, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
							.addGap(37)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAtividade, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLog, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		glMenuAdminPanel.setVerticalGroup(
			glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glMenuAdminPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(glMenuAdminPanel.createSequentialGroup()
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(glMenuAdminPanel.createSequentialGroup()
									.addComponent(textoAdmin, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addGap(29)
									.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblControlo, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSegurana, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
								.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblAtividade, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(separator,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator2,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(glMenuAdminPanel.createSequentialGroup()
							.addComponent(separator3,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(15)))
					.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLog, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnDB, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(glMenuAdminPanel.createSequentialGroup()
									.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnMaquinas, GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)
										.addComponent(btnFuncionario, GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(glMenuAdminPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnFornecedores, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnCategoriaProduto, GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)))
								.addComponent(btnUsers, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		menuAdminPanel.setLayout(glMenuAdminPanel);
		return glMenuAdminPanel;
	}
	private void criaBotoesMenu() {
		btnUsers = new JButton("Users");
		btnLog =  new JButton("Logs");
		btnFuncionario =  new JButton("<html>Funcionario</html>");
		btnCategoriaProduto =  new JButton("Categoria");
		btnFornecedores =  new JButton("<html>Fornecedores<html>");
		btnMaquinas =  new JButton("Maquinas");
		btnDB =  new JButton("<html>Base de<br >Dados</html>");
		DefaultDesign.styleButton(btnDB, DBMENU, new Insets(5, 20, 5, 20));
		DefaultDesign.styleButton(btnUsers, USERMENU, new Insets(10, 14, 10, 14));
		DefaultDesign.styleButton(btnLog, LOG, new Insets(10, 2, 10, 2));
		DefaultDesign.styleButton(btnFuncionario, FUNCIONARIOMENU, new Insets(10, 2, 10, 2));
		DefaultDesign.styleButton(btnCategoriaProduto, CATEGORIAMENU, new Insets(10, 2, 10, 2));
		DefaultDesign.styleButton(btnFornecedores, FORNECEDORMENU, new Insets(10, 5, 10, 5));
		DefaultDesign.styleButton(btnMaquinas, MACHINEMENU, new Insets(10, 14, 10, 14));
	}
	
	public void showMainMenu(JFrame frame) {
		menuAdmin = new JPanel();

		cl = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cl);
		frame.getContentPane().add(menuAdmin, "name_1243457861194300");

		menuAdmin.setLayout(new BorderLayout(0, 0));

		// MENU_ADMIN_PANEL PANEL 
		menuAdminPanel = new JPanel();
		menuAdminPanel.setPreferredSize(new Dimension(10, 16));
		menuAdmin.add(menuAdminPanel, BorderLayout.CENTER);
		Color color = Color.decode("#f5f5f5");
		menuAdminPanel.setBackground(color);
		criaBotoesMenu();
		criaDesign();	
		
		GroupLayout glMenuAdminPanel = putMenuLayout();
		menuAdminPanel.setLayout(glMenuAdminPanel);
		buttonsMainMenu(frame);
	}

	private void buttonsMainMenu(JFrame frame) {
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "user");
				menuBar.setCurrentPanel("user");
			}
		});
		btnCategoriaProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "categoria_produto");
				menuBar.setCurrentPanel("categoria_produto");

			}
		});
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "fornecedores");
				menuBar.setCurrentPanel("fornecedores");

			}
		});
		btnMaquinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "maquina");
				menuBar.setCurrentPanel("maquina");

			}
		});
		btnDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "base_dados");
				menuBar.setCurrentPanel("base_dados");

			}
		});
		btnFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cl.show(frame.getContentPane(), "funcionario");
				menuBar.setCurrentPanel("funcionario");

			}
		});
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frame.getContentPane(), "logs");
				menuBar.setCurrentPanel("logs");

			}
		});
		
	}
	public JPanel getMenuAdmin() {
		return menuAdmin;
	}
	public CardLayout getCl() {
		return cl;
	}


}
