package gui.gui_user.receber_produto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import db.DataBase;
import gui.AutoCompletion;
import gui.PopUp;
import gui.gui_user.UserDesign;
import logic.Check;
import logic.MessageLogs;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ReceberProduto {
	private JSpinner quantidadeJS;
	private JTextField numeroLoteJS;
	private JComboBox<String> comboBoxSubCategoria;
	private JComboBox<String> comboBoxCategoria;
	private JComboBox<String> comboBoxFornecedor;
	private JTextField loteField;
	private JDateChooser dateChooser;
	private PopUp popUp;
	private Check check;
	private DataBase db;
	private MessageLogs messageLogs;
	private JTextField skuField;
	private String loginUsername;

	public ReceberProduto(String username) {
		db = DataBase.getInstance();
		check=new Check();
		popUp=new PopUp();
		messageLogs = MessageLogs.getInstance();
		this.loginUsername = username;
	}
	
	private void resetBorders() {
		dateChooser.setBorder(new JTextField().getBorder());
		comboBoxSubCategoria.setBorder(new JTextField().getBorder());
		loteField.setBorder(new JTextField().getBorder());
		comboBoxFornecedor.setBorder(new JTextField().getBorder());
		quantidadeJS.setBorder(new JTextField().getBorder());
		numeroLoteJS.setBorder(new JTextField().getBorder());
	}
	private boolean checkComboBox() {
		if (check.blankText(comboBoxSubCategoria.getSelectedItem().toString())) {
			popUp.showPopUp("É necessario escolher SubCategoria Valida", "Sub Categoria Invalido");
			comboBoxSubCategoria.setBorder(new LineBorder(Color.red, 1));
			return false;
		} 

		return true;
	}
	private boolean confirmData() {
		resetBorders();
		/* Confirma dados */
		try {
			Date pega = dateChooser.getDate();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			formato.format(pega);
		} catch (NullPointerException e) {
			popUp.showPopUp("É necessario escolher uma data válida", "Data");
			dateChooser.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
	
		if (check.blankText(loteField.getText())) {
			popUp.showPopUp("É necessario escolher um Produto valido", "Produto Invalido");
			loteField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		if (check.blankText(skuField.getText())) {
			popUp.showPopUp("É necessario escolher um SKU", "SKU Invalido");
			skuField.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		if (!check.onlyNumbers(numeroLoteJS.getText())) {
			popUp.showPopUp("Escolha um numero de lote valido (so com caracteres numericos)", "Numero lote invalido");
			numeroLoteJS.setBorder(new LineBorder(Color.red, 1));
			return false;
		}
		return checkComboBox();

	}
	private void categoriaComboBox(JPanel panel ) {
		comboBoxCategoria = new JComboBox<>();
		DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>();
		comboBoxCategoria.setModel(model3);
		panel.add(comboBoxCategoria);
		ArrayList<String> categoria = db.getAllCategoria();
		for (int i = 0; i <  categoria.size(); i++) {
			model3.addElement( categoria.get(i));

		}
		comboBoxCategoria.setSelectedItem(categoria);
		AutoCompletion.enable(comboBoxCategoria);
	}
	private void subCategoriaComboBox(JPanel panel) {
		comboBoxSubCategoria = new JComboBox<>();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		comboBoxSubCategoria.setModel(model);
		panel.add(comboBoxSubCategoria);
		AutoCompletion.enable(comboBoxSubCategoria);
		comboBoxButton(model);

	}
	private void designPopUp(JPanel panel) {
		JLabel lblNome2 = new JLabel("Categoria");
		panel.add(lblNome2);
		
		categoriaComboBox(panel);

		JLabel lblNome = new JLabel("Sub-Categoria");
		panel.add(lblNome);
		
		subCategoriaComboBox(panel);
		
		JLabel lblUser = new JLabel("Nome produto");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblUser);
		loteField = new JTextField();
		panel.add(loteField);
		
		JLabel lblProduto = new JLabel("Quantidade");
		panel.add(lblProduto);
		quantidadeJS = new JSpinner();
		quantidadeJS.setModel(new SpinnerNumberModel(1, 1, 10000, 1));
		panel.add(quantidadeJS);
		
		
		JLabel lblOrigem = new JLabel("Fornecedor");
		panel.add(lblOrigem);
		
		fornecedorComboBox(panel);
	}
	private void fornecedorComboBox(JPanel panel) {
		comboBoxFornecedor = new JComboBox<>();
		DefaultComboBoxModel<String> model4 = new DefaultComboBoxModel<>();
		comboBoxFornecedor.setModel(model4);
		panel.add(comboBoxFornecedor);
		ArrayList<String> fornecedor = db.getAllFornecedores();
		for (int i = 0; i <  fornecedor.size(); i++) {
			model4.addElement( fornecedor.get(i));

		}
		comboBoxFornecedor.setSelectedItem(fornecedor);
		AutoCompletion.enable(comboBoxFornecedor);
	}
	private void data(JPanel panel) {
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblData);
		dateChooser=new JDateChooser();
		dateChooser.setMaxSelectableDate(new Date());
		Date date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
		dateChooser.setMinSelectableDate(date);
		dateChooser.setDateFormatString("dd-MM-yyyy");
		Date date22 = new Date();
		dateChooser.setDate(date22);
		panel.add(dateChooser);
	}
	private int showReceberPopUp() {
		Object[] options1 = { "Recebido", "Sair" };
		ImageIcon icon = new ImageIcon(UserDesign.class.getResource("/img/shipping.png"));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 2, 0, 0));
		

		designPopUp(panel);

		data(panel);
		
		JLabel lblNLote = new JLabel("Numero do Lote");
		panel.add(lblNLote);
		numeroLoteJS = new JTextField();
		panel.add(numeroLoteJS);
		
		
		JLabel lblSKU = new JLabel("SKU do Produto");
		panel.add(lblSKU);
		skuField = new JTextField();
		panel.add(skuField);
		
		
		return JOptionPane.showOptionDialog(null, panel, "Receber lote", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options1, options1[0]);
		 }
	private void comboBoxButton(DefaultComboBoxModel<String> model) {
		comboBoxCategoria.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	model.removeAllElements();
		    	String cat = comboBoxCategoria.getSelectedItem().toString();
		    	ArrayList<String> subCategoria = db.getAllSubCategoria(cat);
				for (int i = 0; i <  subCategoria.size(); i++) {
					model.addElement( subCategoria.get(i));

				}
				comboBoxSubCategoria.setSelectedItem(null);
		    }
		});
	}
	public void receberProdutoFunc(String nomeArmazem) {
		boolean itsFinished = false;
		while (!itsFinished) {
			int result = showReceberPopUp();// mostra o pop up
			if (result == JOptionPane.YES_OPTION) {
				itsFinished = confirmData();
			} else {
				break;
			}
		}
		if (itsFinished) {
			String subCategoria = comboBoxSubCategoria.getSelectedItem().toString();
			String nomeProduto = loteField.getText();
			int quantidade= (Integer) quantidadeJS.getValue();
			String origem = comboBoxFornecedor.getSelectedItem().toString();
			String numeroLote = numeroLoteJS.getText();
			
			java.sql.Timestamp timestamp = new java.sql.Timestamp(dateChooser.getDate().getTime());			
			String data = new SimpleDateFormat("MM/dd/yyyy").format(timestamp);
			String sku=skuField.getText();
			sendtoBD(numeroLote+";"+origem+";"+data+";"+subCategoria+";"+nomeProduto,quantidade,sku);
			String dados = nomeProduto+";"+quantidade+";"+origem+";"+data+";"+numeroLote;
			messageLogs.receberProduto(loginUsername+";"+false+";"+nomeArmazem, dados);
		}
	}
	
	private void sendtoBD(String dadosLote,int quantidade,String sku) {		
		String[] aux= dadosLote.split(";");

		if(!db.addLote(dadosLote)) {
			popUp.showPopUp("O Lote ja existe", "Lote invalido");
			return;
		}
		for (int i=0;i<quantidade;i++) {
			db.addProduto(sku+Integer.toString(i+1), aux[0]);
		}
	}
	
	
}
	

