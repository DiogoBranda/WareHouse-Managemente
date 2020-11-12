package db;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Armazem {
/*retornem true ou false, dependendo se corre tudo bem ou nao*/
	
	/*Metam como parametro tudo o que precisarem (qual a warehouse a usar)
	 * Meter as dependencias todas!
	 * 
	 * */
	public Armazem() {
		
	}
	
	public boolean selectAll(DataBase db) {
		db.connect();
	//	modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela, fazer isto no inicio
		/*NOME, LOCALIZAAO*/
		String sql = "SELECT * "
				   + "FROM armazem "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String localizacao = rs.getString("localizacao");
				String format = "%-25s%-25s%s%n";
				System.out.printf(format,"id: "+id,"nome: "+nome,"localizacao: "+localizacao);
	//			modelUser.addRow(new Object[] { nome, localizacao });
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
	

	
	public boolean insertAll(DataBase db, String nome, String localizacao) {
		/*Adiciona nome, localizaao */
		db.connect();
		String sql = "INSERT INTO armazem (nome, localizacao)"
				   + "VALUES ('"+nome+"', '"+localizacao+"');";
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
	
	public boolean updateUser(DataBase db, String nomeAntigo, String localizacaoAntigo, String nome, String localizacao) {
		/*D update no NOME=nomeAntigo && LOCALIZACAO = localizacaoAntigo com os respetivos novos*/
		db.connect();
		String sql = "UPDATE armazem "
				   + "SET nome = '"+nome+"', localizacao = '"+localizacao+"' "
				   + "WHERE nome = '"+nomeAntigo+"' AND localizacao = '"+localizacaoAntigo+"';";
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
	
	public boolean remove(DataBase db, String nome, String localizacao) {
		/*remove armazem com estes parametros*/
		db.connect();
		String sql = "DELETE FROM armazem "
				   + "WHERE nome='"+nome+"' AND localizacao='"+localizacao+"';";
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
}
