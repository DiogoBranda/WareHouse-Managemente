package db;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class SubCategoriaProduto {
/*retornem true ou false, dependendo se corre tudo bem ou nao*/
	
	/*Metam como parametro tudo o que precisarem (qual a warehouse a usar)
	 * Meter as dependencias todas!
	 * 
	 * */
	
	public boolean selectAll(DataBase db) {
		db.connect();
	//	modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela, fazer isto no inicio
		/*NOME da sub-cateogira*/
		String sql = "SELECT * "
				   + "FROM sub_categoria "
				   + "ORDER BY sub_categoria";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String sub_categoria = rs.getString("sub_categoria");
				String categoria = rs.getString("categoria");
				String format = "%-25s%s%n";
				System.out.printf(format,"sub_categoria: "+sub_categoria,"categoria: "+categoria);
		//		modelUser.addRow(new Object[] { "nome" });
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
	

	
	public boolean insertAll(DataBase db, String nome, String categoria) {
		/*adiciona o nome  */
		db.connect();
		String sql = "INSERT INTO sub_categoria (sub_categoria, categoria)"
				   + "VALUES ('"+nome+"','"+categoria+"')";
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
	
	public boolean updateUser(String nomeAntigo, String nome, String nomeDaCategoria) {
		/*Update do nome antigo para o novo nome que pertence  CATEGORIA=nomeDaCategoria */
		
		return true;
	}
	
	public boolean remove(String nome) {
		/*remove sub-categoria com estes parametros*/
		
		return true;
	}
}
