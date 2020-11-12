package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Lote {

	/**
	 * Insere informação do Lote
	 * @param db Base de Dados referente
	 * @param dadosLote dados do lote numa string separado por ";", numero de lote, origem, data chegada, sub categoria e nome do lote
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String dadosLote) {
		String[] aux= dadosLote.split(";");
		String sql = "INSERT INTO lote "
				   + "VALUES ('"+aux[0]+"', "+"'"+aux[1]+"', "+" '"+aux[2]+"', '"+aux[3]+"', '"+aux[4]+"')";
		return db.executeQuery(sql);
	}
	
	/**
	 * Insere no TableModel os dados numero lote e nome 
	 * do respetivo Armazemd
	 * @param db Base de dados respetiva
	 * @param modelArmazem Tabela onde se insere os dados
	 * @return boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelArmazem) {
		String sql = "SELECT * FROM lote";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while(rs.next()) {
					String numeroLote = rs.getString("numero_lote");
					String nome = rs.getString("nome");
					modelArmazem.addRow(new Object[] { numeroLote, nome });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Remove o Lote
	 * @param db Base de Dados referente 
	 * @param numeroLote Numero de lote da subCategoria pertencente
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean remove(DataBase db,String numeroLote) {
		String sql = "DELETE FROM lote WHERE numero_lote="+"'"+numeroLote+"'";
		return db.executeQuery(sql);
	}

	
}
