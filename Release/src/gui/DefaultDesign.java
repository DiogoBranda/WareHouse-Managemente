package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import gui.gui_admin.Admin;

public class DefaultDesign {
	private static final String HOME = "/img/home.png";
	private DefaultDesign() {
		
	}
	public static void styleBotaoSimples(JButton button, String icon) {
		button.setFont(new Font("Consolas", Font.PLAIN, 12));
		if(icon!=null)
			button.setIcon(new ImageIcon(Admin.class.getResource(icon)));
		Color color = Color.decode("#f9f6f7");
		button.setBackground(color);
		Color colorText = Color.decode("#364f6b");
		button.setForeground(colorText);
		button.setFocusable(false);
	}
	public static void styleToggleSimples(JToggleButton button, String icon) {
		button.setFont(new Font("Consolas", Font.PLAIN, 12));
		if(icon!=null)
			button.setIcon(new ImageIcon(Admin.class.getResource(icon)));
		Color color = Color.decode("#f9f6f7");
		button.setBackground(color);
		Color colorText = Color.decode("#364f6b");
		button.setForeground(colorText);
		button.setFocusable(false);
		Color colorPressed = Color.decode("#364f6b");

		UIManager.put("ToggleButton.select", colorPressed);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setBorder(UIManager.getBorder("Button.border"));


	}

	public static void styleButton(JButton button, String icon, Insets insets) {
		button.setMargin(insets);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.BOTTOM);
		button.setVerticalTextPosition(SwingConstants.TOP);
		button.setIcon(new ImageIcon(Admin.class.getResource(icon)));
		button.setBorder(UIManager.getBorder("Button.border"));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Color color = Color.decode("#f9f6f7");
		button.setBackground(color);
		button.setFont(new Font("Consolas", Font.BOLD, 12));
		Color colorText = Color.decode("#364f6b");
		button.setForeground(colorText);
		button.setFocusable(false);
	}
	
	public static void styleBotaoHome(JButton button) {
		button.setFont(new Font("Consolas", Font.BOLD, 12));
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setIcon(new ImageIcon(Admin.class.getResource(HOME)));
		Color color = Color.decode("#f9f6f7");
		button.setBackground(color);
		Color colorText = Color.decode("#364f6b");
		button.setForeground(colorText);
		button.setFocusable(false);
	}
	public static void styleBotaoLogin(JToggleButton btnLogin) {
		btnLogin.setMaximumSize(new Dimension(36, 23));
		btnLogin.setFont(new Font("Calibri", Font.BOLD, 18));
		btnLogin.setBackground(new Color(0x2dce98));
		btnLogin.setForeground(Color.white);
		btnLogin.setUI(new StyledButtonUI());
		btnLogin.setFocusable(false);
	}
	public static void styleBotaoRecuPass(JButton btnRecuperarPasswod) {
		btnRecuperarPasswod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRecuperarPasswod.setForeground(new Color(12, 51, 243));
		btnRecuperarPasswod.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRecuperarPasswod.setOpaque(false);
		btnRecuperarPasswod.setContentAreaFilled(false);
		btnRecuperarPasswod.setBorderPainted(false);
		btnRecuperarPasswod.setFocusable(false);

	}
	public static void styleLabel(JLabel label) {
		label.setFont(new Font("HP Simplified", Font.BOLD, 38));
		Color colorText = Color.decode("#364f6b");
		label.setForeground(colorText);
	}
	public static void styleLabel28(JLabel label) {
		label.setFont(new Font("HP Simplified", Font.BOLD, 28));
		Color colorText = Color.decode("#364f6b");
		label.setForeground(colorText);
	}
	public static void styleLabelMenu(JLabel label) {
		label.setFont(new Font("Open Sans", Font.BOLD, 20));
		Color colorText = Color.decode("#fc5185");
		label.setForeground(colorText);
	}
	
	public static void styleSearch(JTextField search) {
		search.setText("Quick Access");
		search.setToolTipText("Quick Access");
		search.setColumns(10);
	}
	
	public static void styleTabela(JTable table, DefaultTableModel model) {
		table.setSelectionForeground(Color.BLACK);
		table.setModel(model);

		/* Para nao mover */
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);// para ordenar
		table.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public static void styleSeparator(JSeparator separator) {
		separator.setPreferredSize(new Dimension(0, 2));
		separator.setMinimumSize(new Dimension(2, 2));
		separator.setFont(new Font("Dialog", Font.BOLD, 2));
		Color color = Color.decode("#3fc1c9");
		separator.setForeground(color);
	     Border blackline = BorderFactory.createLineBorder(color, 2);

		separator.setBorder(blackline);
	}
	
	public static void styleTituloMenu(JEditorPane titulo, String string) {
		Color color1 = Color.decode("#364f6b");

		titulo.setBorder(new MatteBorder(1, 1, 3, 1, color1));
		titulo.setFocusable(false);
		titulo.setIgnoreRepaint(true);
		titulo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		titulo.setFont(new Font("Lucida Sans", Font.BOLD, 22));
		titulo.setEditable(false);
		titulo.setText(string);
		Color color = Color.decode("#f5f5f5");
		titulo.setBackground(color);
		Color color2 = Color.decode("#364f6b");
		titulo.setForeground(color2);

	}
}
