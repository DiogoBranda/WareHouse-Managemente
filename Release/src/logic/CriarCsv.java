package logic;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import db.DataBase;

public class CriarCsv {
	private DataBase db;
	private gui.gui_user.cria_csv.CriarCsv criaCsvDesign;

	public CriarCsv() {
		db = DataBase.getInstance();
	}

	public void openFile(File file) {
		if (!Desktop.isDesktopSupported()) {
			criaCsvDesign.naoSuportado();
		}
		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) {
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void csvFuncionarios(boolean openFile) {
		criaCsvDesign = new gui.gui_user.cria_csv.CriarCsv();
		File file = criaCsvDesign.localSave();

		StringBuilder sb = new StringBuilder();

		db.connect();
		String query = "SELECT * FROM funcionario";
		Statement stmt = null;
		try {
			sb.append("NIF");
			sb.append(",");
			sb.append("Nome");
			sb.append(",");
			sb.append("Idade");
			sb.append(",");
			sb.append("Função");
			sb.append(",");
			sb.append("Salario");
			sb.append("\r\n");

			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sb.append(rs.getString("nif"));
				sb.append(",");
				sb.append(rs.getString("nome"));
				sb.append(",");
				sb.append(rs.getString("idade"));
				sb.append(",");
				sb.append(rs.getString("funcao"));
				sb.append(",");
				sb.append(rs.getString("salario"));
				sb.append("\r\n");
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(sb.toString());
			pw.close();

			if (openFile)
				openFile(file);

		} catch (Exception e) {
		}
	}

	public void csvProdutos(boolean openFile) {
		criaCsvDesign = new gui.gui_user.cria_csv.CriarCsv();

		File file = criaCsvDesign.localSave();

		StringBuilder sb = new StringBuilder();

		db.connect();
		String query = "SELECT * FROM produto";
		Statement stmt = null;
		try {
			sb.append("SKU");
			sb.append(",");
			sb.append("Data Saida");
			sb.append(",");
			sb.append("Destino");
			sb.append(",");
			sb.append("Com Defeito");
			sb.append(",");
			sb.append("Num Lote");
			sb.append(",");
			sb.append("Vendido");
			sb.append("\r\n");

			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sb.append(rs.getString("sku"));
				sb.append(",");
				sb.append(rs.getString("data_saida"));
				sb.append(",");
				sb.append(rs.getString("destino"));
				sb.append(",");
				sb.append(rs.getString("com_defeito"));
				sb.append(",");
				sb.append(rs.getString("num_lote"));
				sb.append(",");
				sb.append(rs.getString("vendido"));
				sb.append("\r\n");
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(sb.toString());
			pw.close();

			if (openFile)
				openFile(file);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void csvAtividade(boolean openFile) {
		criaCsvDesign = new gui.gui_user.cria_csv.CriarCsv();

		File file = criaCsvDesign.localSave();

		StringBuilder sb = new StringBuilder();

		db.connect();
		String query = "SELECT * FROM logs";
		Statement stmt = null;
		try {
			sb.append("ID");
			sb.append(",");
			sb.append("Data");
			sb.append(",");
			sb.append("Username");
			sb.append(",");
			sb.append("Admin");
			sb.append(",");
			sb.append("Ação");
			sb.append(",");
			sb.append("IP");
			sb.append("\r\n");

			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sb.append(rs.getString("id"));
				sb.append(",");
				sb.append(rs.getString("data"));
				sb.append(",");
				sb.append(rs.getString("username"));
				sb.append(",");
				sb.append(rs.getString("admin"));
				sb.append(",");
				sb.append(rs.getString("acao_compl"));
				sb.append(",");
				sb.append(rs.getString("ip"));
				sb.append("\r\n");
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(sb.toString());
			pw.close();

			if (openFile)
				openFile(file);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void csvMaquinas(boolean openFile) {
		criaCsvDesign = new gui.gui_user.cria_csv.CriarCsv();

		File file = criaCsvDesign.localSave();

		StringBuilder sb = new StringBuilder();

		db.connect();
		String query = "SELECT * FROM maquina";
		Statement stmt = null;
		try {
			sb.append("numero_serie");
			sb.append(",");
			sb.append("nome");
			sb.append(",");
			sb.append("avariada");
			sb.append(",");
			sb.append("id_armazem");
			sb.append(",");
			sb.append("descricao_avaria");
			sb.append("\r\n");

			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sb.append(rs.getString("numero_serie"));
				sb.append(",");
				sb.append(rs.getString("nome"));
				sb.append(",");
				sb.append(rs.getString("avariada"));
				sb.append(",");
				sb.append(rs.getString("id_armazem"));
				sb.append(",");
				sb.append(rs.getString("descricao_avaria"));
				sb.append("\r\n");
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(sb.toString());
			pw.close();

			if (openFile)
				openFile(file);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void csvLotes(boolean openFile) {
		criaCsvDesign = new gui.gui_user.cria_csv.CriarCsv();

		File file = criaCsvDesign.localSave();

		StringBuilder sb = new StringBuilder();

		db.connect();
		String query = "SELECT * FROM lote";
		Statement stmt = null;
		try {
			sb.append("numero_lote");
			sb.append(",");
			sb.append("origem");
			sb.append(",");
			sb.append("data_de_chegada");
			sb.append(",");
			sb.append("sub_categoria");
			sb.append(",");
			sb.append("nome");
			sb.append("\r\n");

			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				sb.append(rs.getString("numero_lote"));
				sb.append(",");
				sb.append(rs.getString("origem"));
				sb.append(",");
				sb.append(rs.getString("data_de_chegada"));
				sb.append(",");
				sb.append(rs.getString("sub_categoria"));
				sb.append(",");
				sb.append(rs.getString("nome"));
				sb.append("\r\n");
			}

			PrintWriter pw = new PrintWriter(file);
			pw.write(sb.toString());
			pw.close();

			if (openFile)
				openFile(file);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
