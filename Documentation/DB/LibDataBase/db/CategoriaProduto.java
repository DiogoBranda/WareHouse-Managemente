package db;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class CategoriaProduto {
/*retornem true ou false, dependendo se corre tudo bem ou nao*/
	
	/*Metam como parametro tudo o que precisarem (qual a warehouse a usar)
	 * Meter as dependencias todas!
	 * 
	 * */
	
	public boolean selectAll(DataBase db) {
		db.connect();
//		modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela, fazer isto no inicio
		/*NOME da cateogira*/
		String sql = "SELECT * "
				   + "FROM categoria_produto "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String nome = rs.getString("nome");
				int id_armazem = rs.getInt("id_armazem");
				String format = "%-25s%s%n";
				System.out.printf(format,"nome: "+nome,"id_armazem: "+id_armazem);
//				modelUser.addRow(new Object[] { nome });
			}
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
	        return false;
		}
		db.disconnect();
		return true;	
	}
	

	
	public boolean insertAll(DataBase db, String nome, int id_armazem) {
		/*adiciona o nome  */
		db.connect();
		String sql = "INSERT INTO categoria_produto (nome, id_armazem)"
				   + "VALUES ('"+nome+"',"+id_armazem+")";
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		db.disconnect();
		return true;
	}
	
	public boolean updateUser(String nomeAntigo, String nome) {
		/*Update do nome antigo para o novo nome */
		
		return true;
	}
	
	public boolean remove(String nome) {
		/*remove categoria com estes parametros*/
		
		return true;
	}
}