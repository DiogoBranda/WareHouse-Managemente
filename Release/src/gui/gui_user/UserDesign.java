package gui.gui_user;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import gui.DefaultDesign;
import gui.gui_user.cria_csv.CriarCsv;
import gui.gui_user.enviar_produto.EnviarProduto;
import gui.gui_user.funcionario.Funcionario;
import gui.gui_user.graficos.Graficos;
import gui.gui_user.maquinas.Maquinas;
import gui.gui_user.produto.Produtos;
import gui.gui_user.receber_produto.ReceberProduto;
import gui.gui_user.vendas.Vendas;
import gui.menu_bar.MenuBar;
import logic.MessageLogs;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class UserDesign {

	private String nomeArmazem;
	private String username;

	private JFrame frmUserDesign;
	// Imagens
	private static final String RECEBER = "/img/receber.png";
	private static final String MAQUINA = "/img/machineMin.png";
	private static final String ENVIAR = "/img/truckMin.png";
	private static final String FUNCIONARIO = "/img/avatar.png";
	private static final String RELATORIO = "/img/report.png";
	private static final String VENDAS = "/img/vendas.png";
	private static final String PRODUTO = "/img/box.png";
	private static final String GRAFICO = "/img/grafico.png";

	// Classes
	private Maquinas maquinaClass;
	private Produtos produtoClass;
	private Vendas vendasClass;
	private EnviarProduto enviarprodutoClass;
	private Funcionario funcionarioClasse;
	private ReceberProduto receberprodutoClass;
	private CriarCsv criarCsvClasse;
	private MenuBar menuBar;

	// Main Menu
	private JPanel mainMenuHome;
	private JLabel lblRececao;
	private JLabel lblEnviar;
	private JLabel lblRelatorios;
	private JEditorPane dtrpnUser;
	private JLabel lblArmazem;
	private JSeparator separator;
	private JSeparator separator1;
	private JSeparator separator2;
	private JSeparator separator3;
	private JButton btnVendas;
	private JButton btnProdutos;
	private JButton btnFuncionarios;
	private JButton btnMaquinas;
	private JButton btnRececaoProdutos;
	private JButton btnEnviarProduto;
	private JButton btnGerarRelatorioStock;
	private JButton btnGraficos;
	private JPanel userDesignPanel;

	private CardLayout cl;

	private MessageLogs messageLogs;

	private Graficos chart;


	/**
	 * Adiciona os panels a janela principal
	 */
	private void putPanels() {
		frmUserDesign.getContentPane().add(userDesignPanel, "userDesign");
		frmUserDesign.getContentPane().add(vendasClass.getVendas(), "Vendas");
		frmUserDesign.getContentPane().add(maquinaClass.getMaquinas(), "Maquinas");
		frmUserDesign.getContentPane().add(funcionarioClasse.getFuncionarios(), "Funcionarios");
		frmUserDesign.getContentPane().add(produtoClass.getProdutos(), "Produtos");
		frmUserDesign.getContentPane().add(chart.getGrafico(), "Grafico");
		cl.show(frmUserDesign.getContentPane(), "userDesign");// mostrar o main menu
	}


	public UserDesign(String armazem, String username) {
		this.nomeArmazem = armazem;
		this.enviarprodutoClass = new EnviarProduto(username);
		this.receberprodutoClass = new ReceberProduto(username);
		this.username = username;
		this.criarCsvClasse = new CriarCsv();
		this.messageLogs = MessageLogs.getInstance();
		initialize();
	}

	private void criaTituloMenu() {
		lblArmazem = new JLabel("Armazem");
		DefaultDesign.styleLabelMenu(lblArmazem);

		lblRececao = new JLabel("Rece\u00E7\u00E3o");
		DefaultDesign.styleLabelMenu(lblRececao);

		lblEnviar = new JLabel("Enviar");
		DefaultDesign.styleLabelMenu(lblEnviar);

		lblRelatorios = new JLabel("Relatorios");
		DefaultDesign.styleLabelMenu(lblRelatorios);

		dtrpnUser = new JEditorPane();
		DefaultDesign.styleTituloMenu(dtrpnUser, "User");

		separator = new JSeparator();
		DefaultDesign.styleSeparator(separator);

		separator1 = new JSeparator();
		DefaultDesign.styleSeparator(separator1);

		separator2 = new JSeparator();
		DefaultDesign.styleSeparator(separator2);

		separator3 = new JSeparator();
		DefaultDesign.styleSeparator(separator3);
	}

	private void criaBotoesMenu() {
		btnProdutos = new JButton("<html>Produtos<html>");
		DefaultDesign.styleButton(btnProdutos, PRODUTO, new Insets(2, 14, 10, 14));
		btnFuncionarios = new JButton("Funcionarios");
		DefaultDesign.styleButton(btnFuncionarios, FUNCIONARIO, new Insets(2, 0, 10, 0));
		btnMaquinas = new JButton("Maquinas");
		DefaultDesign.styleButton(btnMaquinas, MAQUINA, new Insets(2, 14, 10, 14));
		btnVendas = new JButton("<html>Vendas<html>");
		DefaultDesign.styleButton(btnVendas, VENDAS, new Insets(2, 14, 10, 14));
		btnRececaoProdutos = new JButton("<html>Receção lote</html>");
		DefaultDesign.styleButton(btnRececaoProdutos, RECEBER, new Insets(10, 2, 10, 2));
		btnEnviarProduto = new JButton("<html>Enviar<br>Produto<html>");
		DefaultDesign.styleButton(btnEnviarProduto, ENVIAR, new Insets(5, 20, 5, 20));
		btnGerarRelatorioStock = new JButton("<html>Gerar<br>Relatorio<br><html>");
		DefaultDesign.styleButton(btnGerarRelatorioStock, RELATORIO, new Insets(5, 20, 5, 20));
		btnGraficos = new JButton("Graficos");
		DefaultDesign.styleButton(btnGraficos, GRAFICO, new Insets(2, 14, 10, 14));

	}

	private GroupLayout putMenuLayout() {
		GroupLayout glMainMenuHome = new GroupLayout(mainMenuHome);
		glMainMenuHome.setHorizontalGroup(
			glMainMenuHome.createParallelGroup(Alignment.LEADING)
				.addGroup(glMainMenuHome.createSequentialGroup()
					.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
						.addGroup(glMainMenuHome.createSequentialGroup()
							.addGap(83)
							.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, glMainMenuHome.createParallelGroup(Alignment.TRAILING)
									.addGroup(glMainMenuHome.createSequentialGroup()
										.addComponent(btnProdutos, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnFuncionarios, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
									.addGroup(Alignment.LEADING, glMainMenuHome.createSequentialGroup()
										.addComponent(btnVendas, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(btnMaquinas, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArmazem, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addGap(51)
							.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRececao, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRececaoProdutos, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addGap(39)
							.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
								.addComponent(separator1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEnviar, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnviarProduto, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addGap(57)
							.addGroup(glMainMenuHome.createParallelGroup(Alignment.TRAILING)
								.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblRelatorios)
									.addComponent(separator2, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
								.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
									.addComponent(btnGraficos, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnGerarRelatorioStock, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
						.addGroup(glMainMenuHome.createSequentialGroup()
							.addContainerGap()
							.addComponent(dtrpnUser, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
					.addGap(103))
		);
		glMainMenuHome.setVerticalGroup(
			glMainMenuHome.createParallelGroup(Alignment.LEADING)
				.addGroup(glMainMenuHome.createSequentialGroup()
					.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
						.addGroup(glMainMenuHome.createSequentialGroup()
							.addContainerGap()
							.addComponent(dtrpnUser, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(glMainMenuHome.createSequentialGroup()
							.addGap(72)
							.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
								.addGroup(glMainMenuHome.createSequentialGroup()
									.addGroup(glMainMenuHome.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblRececao, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblArmazem))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
										.addComponent(separator3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(19)
									.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
										.addGroup(glMainMenuHome.createSequentialGroup()
											.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
												.addComponent(btnVendas, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnMaquinas, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
											.addGap(21)
											.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
												.addComponent(btnFuncionarios, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnProdutos, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
										.addComponent(btnRececaoProdutos, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
								.addGroup(glMainMenuHome.createSequentialGroup()
									.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
										.addGroup(glMainMenuHome.createSequentialGroup()
											.addComponent(lblEnviar, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(glMainMenuHome.createSequentialGroup()
											.addComponent(lblRelatorios, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(separator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(glMainMenuHome.createParallelGroup(Alignment.LEADING)
										.addGroup(glMainMenuHome.createSequentialGroup()
											.addComponent(btnGerarRelatorioStock, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(btnGraficos, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnEnviarProduto, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		return glMainMenuHome;
	}

	private void showMainMenu() {

		userDesignPanel = new JPanel();
		frmUserDesign.getContentPane().add(userDesignPanel, "name_144798506529000");
		userDesignPanel.setLayout(new BorderLayout(0, 0));

		mainMenuHome = new JPanel();
		userDesignPanel.add(mainMenuHome, BorderLayout.NORTH);
		Color color = Color.decode("#f5f5f5");
		userDesignPanel.setBackground(color);
		criaTituloMenu();
		criaBotoesMenu();
		GroupLayout glMainMenuHome = putMenuLayout();
		mainMenuHome.setLayout(glMainMenuHome);
	}

	

	private void botoes() {

		btnVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frmUserDesign.getContentPane(), "Vendas");
			}
		});
		btnMaquinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frmUserDesign.getContentPane(), "Maquinas");
			}
		});
		btnFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frmUserDesign.getContentPane(), "Funcionarios");
			}
		});
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(frmUserDesign.getContentPane(), "Produtos");
			}
		});
		btnGerarRelatorioStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				criarCsvClasse.showPopUpCriarCSV();
			}
		});
		// Menu Pop UPS
		btnRececaoProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receberprodutoClass.receberProdutoFunc(nomeArmazem);

			}
		});
		btnEnviarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarprodutoClass.enviar(nomeArmazem);
			}
		});
		btnGraficos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmUserDesign.setResizable(true);
				menuBar.setCurrentPanel("Grafico");
				cl.show(frmUserDesign.getContentPane(), "Grafico");
			}
		});
		
		frmUserDesign.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Tem a certeza que deseja sair do programa?",
						"Exit", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					messageLogs.saiuSistema(username + ";" + false + ";" + nomeArmazem);
					frmUserDesign.dispose();
				}
			}
		});
	}

	private void initializeSecund() {
		this.vendasClass = new Vendas();
		vendasClass.showVendasMenu(frmUserDesign, cl);
		this.maquinaClass = new Maquinas(username, menuBar);
		maquinaClass.showMaquinasMenu(frmUserDesign, cl);
		this.funcionarioClasse = new Funcionario(menuBar);
		funcionarioClasse.showFuncionariosMenu(frmUserDesign, cl);
		this.produtoClass = new Produtos(username, menuBar);
		produtoClass.showProdutos(frmUserDesign, cl);
		this.chart = new Graficos();
		chart.showGraficos(frmUserDesign);

	}
	private void initializeMenu() {
		menuBar = new MenuBar(frmUserDesign, cl, username);
		menuBar.setCurrentPanel("userDesign");
		menuBar.setNomeArmazem(nomeArmazem);
		menuBar.showMenuBar(1);
	}
	private void initialize() {
		frmUserDesign = new JFrame();
		frmUserDesign.setResizable(false);
		frmUserDesign.setTitle("Menu User");
		frmUserDesign.setBounds(100, 100, 855, 416);
		
		frmUserDesign.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frmUserDesign.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frmUserDesign.getHeight()) / 2);
		frmUserDesign.setLocation(x, y);
		URL iconURL = getClass().getResource("/img/logo.png");
		ImageIcon img = new ImageIcon(iconURL);
		frmUserDesign.setIconImage(img.getImage());
		cl = new CardLayout(0, 0);
		frmUserDesign.getContentPane().setLayout(cl);
		showMainMenu();

		initializeMenu();
		initializeSecund();
		putPanels();

		botoes();
	}

	public JFrame getFrmUserDesign() {
		return frmUserDesign;
	}
}
