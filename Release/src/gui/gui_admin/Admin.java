package gui.gui_admin;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.awt.Dimension;
import gui.gui_admin.base_dados.BaseDados;
import gui.gui_admin.categoria_produto.CategoriaProduto;
import gui.gui_admin.fornecedores.Fornecedores;
import gui.gui_admin.funcionarios.Funcionarios;
import gui.gui_admin.logs.Logs;
import gui.gui_admin.maquina.Maquina;
import gui.gui_admin.users.Users;
import gui.menu_bar.MenuBar;
import logic.MessageLogs;
import javax.swing.ImageIcon;


public class Admin {
	private static final String MENUADMINSTRING = "menu_admin";

	private JFrame frmMenuAdmin;

	/* LOGIC CLASS */
	private MainMenu mainMenuClass;
	private Users usersClass;
	private Funcionarios funcionarioClass;
	private Fornecedores fornecedorClass;
	private Maquina maquinaClass;
	private CategoriaProduto categoriaProdutoClass;
	private BaseDados baseDadosClass;
	private Logs logsClass;

	private CardLayout cl;

	/*
	 * Create the application.
	 */
	public Admin(String nomeArmazem, String username) {
		initialize(nomeArmazem, username);
	}

	/* Coloca os panels para conseguir mudar */
	private void putPanels() {
		frmMenuAdmin.getContentPane().add(mainMenuClass.getMenuAdmin(), MENUADMINSTRING);
		frmMenuAdmin.getContentPane().add(usersClass.getUsers(), "user");
		frmMenuAdmin.getContentPane().add(categoriaProdutoClass.getCategoriaProduto(), "categoria_produto");
		frmMenuAdmin.getContentPane().add(fornecedorClass.getFornecedores(), "fornecedores");
		frmMenuAdmin.getContentPane().add(maquinaClass.getMaquina(), "maquina");
		frmMenuAdmin.getContentPane().add(baseDadosClass.getBaseDados(), "base_dados");
		frmMenuAdmin.getContentPane().add(funcionarioClass.getFuncionario(), "funcionario");
		frmMenuAdmin.getContentPane().add(logsClass.getLogs(), "logs");

		cl.show(frmMenuAdmin.getContentPane(), MENUADMINSTRING);// mostrar o main menu
	}

	private void initializeSecund(String nomeArmazem,  String username, MenuBar menuBar) {
		this.mainMenuClass = new MainMenu(menuBar);
		mainMenuClass.showMainMenu(frmMenuAdmin);
		this.cl=mainMenuClass.getCl();
		this.usersClass = new Users(username, menuBar);
		usersClass.showUserMenu(frmMenuAdmin,cl);
		this.maquinaClass = new Maquina(username, nomeArmazem, menuBar);
		maquinaClass.showMaquinaMenu(frmMenuAdmin,cl);
		this.fornecedorClass = new Fornecedores(username,menuBar);
		fornecedorClass.showFornecedoresMenu(frmMenuAdmin,cl);
		this.funcionarioClass = new Funcionarios(username, nomeArmazem, menuBar);
		funcionarioClass.showFuncionarioMenu(frmMenuAdmin, cl);
		this.categoriaProdutoClass = new CategoriaProduto(username, menuBar);
		categoriaProdutoClass.showCategoriaProduto(frmMenuAdmin, cl);
		this.baseDadosClass = new BaseDados(username, menuBar);
		baseDadosClass.showBaseDadosMenu(frmMenuAdmin, cl);
		this.logsClass = new Logs(menuBar);
		logsClass.showLogsMenu(frmMenuAdmin, cl);
	}
	private MenuBar initializeMenu(String nomeArmazem,  String username) {
		MenuBar menuBar = new MenuBar(frmMenuAdmin, cl, username);
		menuBar.setCurrentPanel(MENUADMINSTRING);
		menuBar.setNomeArmazem(nomeArmazem);
		menuBar.showMenuBar(0);	
		return menuBar;
	}
	private void buttons(String username, MenuBar menuBar) {
		frmMenuAdmin.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Tem a certeza que deseja sair do programa?",
						"Exit", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
					MessageLogs messageLogs = MessageLogs.getInstance();
					messageLogs.saiuSistema(username+";"+true+";"+menuBar.getNomeArmazem());
					frmMenuAdmin.dispose();
				}
			}
		});
	}
	public void initialize(String nomeArmazem,  String username) {
		/* cria a frame do windows */
		frmMenuAdmin = new JFrame("Menu Admin");
		frmMenuAdmin.setResizable(false);
		frmMenuAdmin.setMaximizedBounds(new Rectangle(0, 0, 0, 0));
		frmMenuAdmin.setForeground(Color.WHITE);
		frmMenuAdmin.setTitle("Menu Admin");
		frmMenuAdmin.setBackground(Color.WHITE);
		frmMenuAdmin.setBounds(100, 100, 855, 416);
		frmMenuAdmin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frmMenuAdmin.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frmMenuAdmin.getHeight()) / 2);
		frmMenuAdmin.setLocation(x, y);
		URL iconURL = getClass().getResource("/img/logo.png");
		ImageIcon img = new ImageIcon(iconURL);
		frmMenuAdmin.setIconImage(img.getImage());
		MenuBar menuBar = initializeMenu(nomeArmazem,username);
		initializeSecund(nomeArmazem, username, menuBar);
		putPanels();// COLOCAR OS PANELS PARA MANIPULAR
		buttons(username, menuBar);
	}

	public JFrame getFrmMenuAdmin() {
		return frmMenuAdmin;
	}
}
