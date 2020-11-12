package db;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class Maquina {
	
	public boolean selectNomeNumero(DataBase db,String armazem, DefaultTableModel modelMaquina) {
	//	modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela
		/*NOME, numero de serie*/
		db.connect();
		String sql = "SELECT * "
				   + "FROM maquina "
				   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"') "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String numero_serie =rs.getString("numero_serie");
				String nome = rs.getString("nome");
				Boolean avariada=rs.getBoolean("avariada");
				int id_armazem = rs.getInt("id_armazem");
				String format = "%-25s%-25s%-25s%s%n";
				System.out.printf(format,"numero_serie: "+numero_serie,"nome: "+nome,"avariada: "+avariada,"id_armazem: "+id_armazem);
				modelMaquina.addRow(new Object[] {nome , numero_serie });
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
	
	public boolean selectAvaria(DataBase db,String armazem, DefaultTableModel modelMaquina) {
		db.connect();
		modelMaquina.setRowCount(0);//eliminar todos os dados contidos na tabela
		/*NOME, numero de serie*/
		String sql = "SELECT * "
				   + "FROM maquina "
				   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"') "
				   + "AND avariada='true '"
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String numero_serie =rs.getString("numero_serie");
				String nome = rs.getString("nome");
				int id_armazem = rs.getInt("id_armazem");
				String descricao_avaria = rs.getString("descricao_avaria");
				String format = "%-25s%-25s%-25s%s%n";
				System.out.printf(format,"numero_serie: "+numero_serie,"nome: "+nome,"id_armazem: "+id_armazem,"descricao_avaria: "+descricao_avaria,"id_armazem: "+id_armazem);
				modelMaquina.addRow(new Object[] {nome , ""+numero_serie, descricao_avaria});
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

	
	
	public boolean insertAll(DataBase db, String numero_serie, String nome, boolean avariada ,Integer id_armazem) {
		/*Adiciona nome, numero com avaria=false */
		db.connect();
		String sql = "INSERT INTO maquina (numero_serie, nome, avariada, id_armazem)"
				   + "VALUES ('"+numero_serie+"', '"+nome+"', "+avariada+", "+id_armazem+")";
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
	
	public boolean update(DataBase db, String numeroSerieAntigo, String numeroSerie, String nome) {
		/* Update Maquina*/
		db.connect();
		String sql = "UPDATE maquina " + 
				"SET nome='"+nome+"' ,numero_serie='"+numeroSerie+"'"+
				"WHERE numero_serie='"+numeroSerieAntigo+"'";
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
	
	public boolean remove(DataBase db, String numeroSerie) {
		/* remove Maquina*/
		db.connect();
		String sql = "DELETE FROM maquina WHERE numero_serie='"+numeroSerie+"'";
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
