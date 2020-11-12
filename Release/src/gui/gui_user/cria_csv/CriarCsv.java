package gui.gui_user.cria_csv;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.PopUp;
import gui.gui_admin.Admin;


public class CriarCsv {
	
	private static final String RELATORIO = "/img/report.png";

	private void geraRelatorio(String tipoRelatorio, JCheckBox chckbx) {
		 logic.CriarCsv criaCsv = new logic.CriarCsv();
		if ("Funcionários".equals(tipoRelatorio))
			criaCsv.csvFuncionarios(chckbx.isSelected());
		else if ("Produtos".equals(tipoRelatorio))
			criaCsv.csvProdutos(chckbx.isSelected());
		else if	("Atividade".equals(tipoRelatorio))
			criaCsv.csvAtividade(chckbx.isSelected());
		else if	("Lotes".equals(tipoRelatorio))
			criaCsv.csvLotes(chckbx.isSelected());
		else if	("Maquinas".equals(tipoRelatorio))
			criaCsv.csvMaquinas(chckbx.isSelected());
	}
	public void showPopUpCriarCSV(){
		Object[] options = { "Exportar", "Cancelar" };
		ImageIcon icon = new ImageIcon(Admin.class.getResource(RELATORIO));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblNome = new JLabel("Relatório:\n");
		panel.add(lblNome);
		
		String possibilities[] = {"Funcionários", "Atividade", "Produtos","Lotes","Maquinas"};
		JComboBox<String> comboBox = new JComboBox<>(possibilities);
		panel.add(comboBox);
		
		JLabel lblAdmin = new JLabel("Abrir File");
		panel.add(lblAdmin);

		JCheckBox chckbx= new JCheckBox("");
		panel.add(chckbx);
		
		int result = JOptionPane.showOptionDialog(null, panel, "Gerar Relatório", JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.PLAIN_MESSAGE, icon, options,options[0]);
		
		if(result == JOptionPane.YES_OPTION) {
			String tipoRelatorio = comboBox.getSelectedItem().toString();
			if( (tipoRelatorio != null) && (tipoRelatorio.length() > 0) ) {
				geraRelatorio(tipoRelatorio, chckbx);
			}
		}
	}
	
 	public File localSave() {
 		File file=null;
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");
		
	    fileChooser.setFileFilter(filter);
	    fileChooser.setDialogTitle("Escolha lugar onde guardar");
		if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (!file.getName().toLowerCase().endsWith(".csv"))
				file = new File(file.getAbsolutePath() + ".csv");
		}
		return file;
	}
	
 	public void naoSuportado() {
 		PopUp popUp = new PopUp();
 		popUp.showPopUp("Não suportado", "Erro");
 	}
	
}