package gui.menu_bar;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import db.DataBase;
import gui.Help;
import gui.PopUp;
import gui.gui_admin.Admin;
import gui.gui_login.LoginDesign;
import logic.MessageLogs;

public class MenuBar {
	private static final String MENUADMINSTRING = "menu_admin";

	private static final String MORE = "/img/more.png";
	private static final String MORE1 = "/img/more1.png";
	private static final String WEBSITE = "/img/website.PNG";
	private static final String HELP = "/img/help.png";
	private static final String HOMEMIN = "/img/homeMin.png";
	private static final String LOGOUT = "/img/logout.png";
	private static final String CHANGE = "/img/change.png";
	/* MENU BAR */
	private JMenu menuBarArmazem;
	private JMenuItem mntmMenu;
	private JMenuItem mntmExit1;
	private JMenuItem menuWebsite;
	private JMenuItem menuHelpMe;
	private JMenuItem menuMudarNomeArmazem;
	private JLabel lblArmazem;
	private JLabel lblStatusDb;
	private JFrame frame;
	private String nomeArmazem;

	private boolean first = true;
	private Timer counterTimer;
	private String currentPanel;
	private String username;

	private DataBase db;
	private MessageLogs messageLogs;
	private Armazem armazemClass;
	private Help help;
	private PopUp popUp;

	private CardLayout cl;

	public MenuBar(JFrame frame, CardLayout cl, String username) {
		this.frame = frame;
		this.cl = cl;
		db = DataBase.getInstance();
		messageLogs = MessageLogs.getInstance();
		armazemClass = new Armazem(username);
		help = new Help();
		popUp = new PopUp();
		this.username = username; 
	}

	public String getCurrentPanel() {
		return currentPanel;
	}

	public String getNomeArmazem() {
		return nomeArmazem;
	}

	public void setNomeArmazem(String nomeArmazem) {
		this.nomeArmazem = nomeArmazem;
	}

	public void setLblStatusDb(String string) {
		this.lblStatusDb.setText(string);
	}

	public void setCurrentPanel(String currentPanel) {
		this.currentPanel = currentPanel;
	}

	/**
	 * Mostra a barra menu
	 * 
	 * @param choice - 0:admin menu; 1:user menu
	 */
	public void showMenuBar(int choice) {
		JMenuBar menuBar1 = new JMenuBar();
		menuBar1.setBackground(Color.WHITE);
		frame.setJMenuBar(menuBar1);

		JMenu mnFile1 = new JMenu("File");
		mnFile1.setBackground(Color.LIGHT_GRAY);
		menuBar1.add(mnFile1);

		mntmMenu = new JMenuItem("Menu");
		mntmMenu.setIcon(new ImageIcon(Admin.class.getResource(HOMEMIN)));
		mnFile1.add(mntmMenu);

		mntmExit1 = new JMenuItem("Exit");
		mntmExit1.setIcon(new ImageIcon(Admin.class.getResource(LOGOUT)));
		mnFile1.add(mntmExit1);

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(100, 50));
		menuBar1.add(separator);

		JMenu mnNewMenu = new JMenu("Help");
		menuBar1.add(mnNewMenu);

		menuWebsite = new JMenuItem("Website");

		menuWebsite.setIcon(new ImageIcon(Admin.class.getResource(WEBSITE)));
		mnNewMenu.add(menuWebsite);

		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);

		menuHelpMe = new JMenuItem("Help Me");
		menuHelpMe.setIcon(new ImageIcon(Admin.class.getResource(HELP)));
		mnNewMenu.add(menuHelpMe);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		menuBar1.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JLabel lblBaseDeDados = new JLabel("Base de Dados:");
		panel.add(lblBaseDeDados);

		lblStatusDb = new JLabel("ON");
		lblStatusDb.setForeground(Color.GREEN);
		lblStatusDb.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(lblStatusDb);

		JLabel lblDg = new JLabel("                                                                               ");
		panel.add(lblDg);

		lblArmazem = new JLabel(nomeArmazem);
		lblArmazem.setForeground(Color.BLACK);
		lblArmazem.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		lblArmazem.setBackground(Color.BLACK);
		panel.add(lblArmazem);
		lblArmazem.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblArmazem.setHorizontalAlignment(SwingConstants.RIGHT);
		if (choice == 0) {
			menuBarArmazem = new JMenu("");
			menuBarArmazem.setMaximumSize(new Dimension(50, 50));
			menuBarArmazem.setMargin(new Insets(0, 0, 0, 0));

			menuBarArmazem.setHorizontalAlignment(SwingConstants.LEFT);
			menuBarArmazem.setBackground(Color.WHITE);
			menuBar1.add(menuBarArmazem);
			menuBarArmazem.setIcon(new ImageIcon(Admin.class.getResource(MORE)));

			menuMudarNomeArmazem = new JMenuItem("Mudar informação");

			menuMudarNomeArmazem.setIcon(new ImageIcon(Admin.class.getResource(CHANGE)));
			menuBarArmazem.add(menuMudarNomeArmazem);
		}
		JLabel label = new JLabel("       ");
		menuBar1.add(label);
		label.setBackground(Color.WHITE);
		buttonsMenu(choice);
		backgroundTimer();

		counterTimer.start();

	}

	public JLabel getLblStatusDb() {
		return lblStatusDb;
	}

	private void backgroundTimer() {
		counterTimer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (db.connect()) {

					lblStatusDb.setText("ON");
					lblStatusDb.setForeground(Color.GREEN);
					db.disconnect();
					first = true;
				} else {

					lblStatusDb.setText("OFF");
					lblStatusDb.setForeground(Color.RED);
					if (first) {
						first = false;
						int option=0;
						if("userDesign".equals(currentPanel) || "Grafico".equals(currentPanel)) {
							popUp.showPopUpDataBaseError2();
						}else {
							option = popUp.showPopUpDataBaseError();
						}
						if (option == JOptionPane.NO_OPTION) {
							cl.show(frame.getContentPane(), "base_dados");
						}
					}
				}

			}

		});

	}

	private void buttonsMenu(int choice) {
		mntmMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choice==0) {
				cl.show(frame.getContentPane(), MENUADMINSTRING);
				currentPanel = MENUADMINSTRING;
				}else if(choice==1) {
					if("Grafico".equals(currentPanel)) {
						frame.setBounds(100, 100, 855, 416);
						frame.setResizable(false);
					}
					cl.show(frame.getContentPane(), "userDesign");
					currentPanel = "userDesign";
				}
			}
		});
		mntmExit1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				counterTimer.stop();
				messageLogs.saiuSistema(username+";"+true+";"+nomeArmazem);
				LoginDesign window2 = new LoginDesign();
				window2.getFrmLogin().setVisible(true);
				frame.dispose();

			}
		});
		menuWebsite.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					Desktop desktop = Desktop.getDesktop();
					URI oURL = new URI("https://paginas.fe.up.pt/~up201504257/lpro/user/");
					desktop.browse(oURL);
				} catch (Exception x) {
				}
			}
		});
		if (choice == 0) {
			menuBarArmazem.addMenuListener(new MenuListener() {
				public void menuDeselected(MenuEvent arg0) {
					menuBarArmazem.setIcon(new ImageIcon(Admin.class.getResource(MORE)));

				}

				public void menuSelected(MenuEvent arg0) {
					menuBarArmazem.setIcon(new ImageIcon(Admin.class.getResource(MORE1)));

				}

				@Override
				public void menuCanceled(MenuEvent arg0) {
					// Empty method
				}
			});
			menuMudarNomeArmazem.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					String localizacao = db.localizacaoArmazem(nomeArmazem);
					String[] x = armazemClass.editarArmazem(nomeArmazem, localizacao);
					if (x[0] != null) {
						nomeArmazem = x[0];
						lblArmazem.setText(nomeArmazem);
					}

				}
			});
		}
		menuHelpMe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				help.selectHelp(frame, currentPanel);
			}
		});
	}
}
