package logic;

import java.awt.EventQueue;

import gui.gui_login.LoginDesign;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDesign window1 = new LoginDesign();
					window1.getFrmLogin().setVisible(true);
				} catch (Exception e) {

				}
			}
		});
	}

}
