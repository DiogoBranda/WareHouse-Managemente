package gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.gui_admin.Admin;

public class PopUp {
	
	public int showPopUpDataBaseError() {
		Object[] options1 = { "Ok", "Definições" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource("/img/help.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1, 0, 0));


		JLabel lblNome2 = new JLabel("Verificar a sua ligação à internet");
		panel.add(lblNome2);
		return JOptionPane.showOptionDialog(null, panel, "Erro a ligar à base de dados", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[1]);	
	}
	
	public int showPopUpDataBaseError2() {
		Object[] options1 = { "Ok" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource("/img/db.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1, 0, 0));


		JLabel lblNome2 = new JLabel("Verificar a sua ligação à internet");
		panel.add(lblNome2);
		return JOptionPane.showOptionDialog(null, panel, "Erro a ligar à base de dados", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);	
	}
	
	public void showPopUpDataBaseSucess() {
		Object[] options1 = { "Ok" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource("/img/db.png"));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1, 0, 0));


		JLabel lblNome2 = new JLabel("Ligado corretamente à base de dados");
		panel.add(lblNome2);
		JOptionPane.showOptionDialog(null, panel, "Ligado com sucesso", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);	
	}
	
	public void showPopUp(String string, String titulo) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    string,
			    titulo,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public int showPopUpConfirmation(String string, String titulo) {
		return JOptionPane.showConfirmDialog(null,string, titulo,
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
	}
	
	private void showPopUpInformation(String string, String titulo) {
		JOptionPane.showMessageDialog(null,string, titulo,
				 JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showPopUpAdicionadoSucesso() {
		showPopUpInformation("Adicionado com sucesso", "Adicionado");
	}
	
	public void showPopUpErroAdicionar() {
		showPopUpInformation("Erro ao adicionar", "Erro");
	}
	
	public void showPopUpEditarSucesso() {
		showPopUpInformation("Editado com sucesso", "Editado");
	}
	
	public void showPopUpErroEditar() {
		showPopUpInformation("Erro ao editar", "Erro");
	}
	
	public void showPopUpErroEliminar() {
		showPopUpInformation("Erro ao eliminar", "Erro");
	}
}
