package db;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Fornecedor {
/*retornem true ou false, dependendo se corre tudo bem ou nao*/
	
	/*Metam como parametro tudo o que precisarem (qual a warehouse a usar)
	 * Meter as dependencias todas!
	 * 
	 * */
	
	public boolean selectAll(DataBase db,String armazem, DefaultTableModel modelFornecedor) {
	//	modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela, fazer isto no inicio
		/*NOME*/
		db.connect();
		String sql = "SELECT id, nome, id_armazem "
				   + "FROM fornecedor "
				   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"') "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id =rs.getInt("id");
				String nome = rs.getString("nome");
				int id_armazem = rs.getInt("id_armazem");
				String format = "%-25s%-25s%s%n";
				System.out.printf(format,"id: "+id,"nome: "+nome,"id_armazem: "+id_armazem);
				modelFornecedor.addRow(new Object[] { nome });
			}
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
	        return false;
		}
		db.disconnect();
		return true;
	}
	
	public boolean insertAll(DataBase db, String nome, int id_armazem) {
		/*Adiciona nome */
		db.connect();
		String sql = "INSERT INTO fornecedor (nome, id_armazem)"
				   + "VALUES ('"+nome+"',"+id_armazem+")";
		System.out.println(sql);
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			return false;
		}
		db.disconnect();
		return true;
	}
	
	public boolean update(DataBase db, String nomeAntigo,String nome) {
		/* Update Maquina*/
		db.connect();
		String sql = "UPDATE fornecedor " + 
				"SET nome="+"'"+nome+"'"+
				"WHERE nome="+"'"+nomeAntigo+"'";
		System.out.println(sql);
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			return false;
		}
		db.disconnect();
		return true;
	}
	
	public boolean remove(DataBase db, String nome) {
		
		/* remove Fornecedor com Nome = nome na tabela Fornecedor */
		db.connect();
		String sql = "DELETE FROM fornecedor WHERE nome="+"'"+nome+"'";
		System.out.println(sql);
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} 
		catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			return false;
		}
		db.disconnect();

		return true;
	}
	
}
