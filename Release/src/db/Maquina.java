/**
 * Classe BDProduto
 * Responsavel por tratar da tabela Maquinas da base de dados
 * 
 * @author  Auto-buys
 * @version 1.0
 * @since   2019-11-22 
 */
package db;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class Maquina {
	
	/**
	 * Insere no TableModel o nome e numero de seri da maquina
	 * @param db Base de Dados Referente
	 * @param armazem Armazem a qual a maquina pertence 
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectNomeNumero(DataBase db,String armazem, DefaultTableModel modelMaquina) {
			String sql = "SELECT * "
					   + "FROM maquina "
					   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"')";
			ResultSet rs =  db.executeQueryResult(sql);

			try {
				while(rs.next()) {
						String numeroSerie =rs.getString("numero_serie");
						String nome = rs.getString("nome");
						modelMaquina.addRow(new Object[] {nome , numeroSerie });
					}
			} catch (SQLException e) {
				return false;
			}
			return true;
		}

	/**
	 * Insere no TableModel o nome e numero de serie das maquinas avariadas 
	 * @param db Base de dados referente 
	 * @param armazem Armazem em que a maquina esta
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAvaria(DataBase db,String armazem, DefaultTableModel modelMaquina) {
		modelMaquina.setRowCount(0);
		String sql = "SELECT * "
				   + "FROM maquina "
				   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"') "
				   + "AND avariada='true'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while(rs.next()) {
					String numeroSerie =rs.getString("numero_serie");
					String nome = rs.getString("nome");
					modelMaquina.addRow(new Object[] {nome , ""+numeroSerie, true});
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Insere no TableModel o nome id e estado da maquina
	 * @param db Base de Dados referente
	 * @param armazem Nome do armazem em que a maquina esta
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectNomeNumeroEstado(DataBase db,String armazem, DefaultTableModel modelMaquina) {
			String sql = "SELECT * "
					   + "FROM maquina "
					   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"')";
			ResultSet rs =  db.executeQueryResult(sql);

			try {
				while(rs.next()) {
						String id =rs.getString("numero_serie");
						String nome = rs.getString("nome");
						Boolean avariada=rs.getBoolean("avariada");
						modelMaquina.addRow(new Object[] {nome , ""+id,avariada});
					}
			} catch (SQLException e) {
				return false;
			}
			return true;
		}
	
	/**
	 * Declara que a maquina esta avariada e adiciona uma descrição do problema
	 * @param db Base de Dados Referente
	 * @param armazem Nome do Armazem a qual a maquina pertence
	 * @param Id Id da maquina
	 * @param descricao descrição do problema
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean updateAvariadoMaquina(DataBase db,String armazem,String Id,String descricao) {
			String sql = "UPDATE maquina "
					   + "SET avariada = true, descricao_avaria = '"+descricao+"'"
					   + "WHERE numero_serie='"+Id+"'";
			return db.executeQuery(sql);
		}
	
	/**
	 * Update da Maquina
	 * @param db Base de dados referente
	 * @param numeroSerieAntigo Numero Serie antigo da maquina
	 * @param numeroSerie Novo numero de Serie
	 * @param nome Novo nome da maquina
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean update(DataBase db, String numeroSerieAntigo, String numeroSerie, String nome) {
		String sql = "UPDATE maquina " + 
					 "SET nome='"+nome+"' ,numero_serie='"+numeroSerie+"'"+
					 "WHERE numero_serie='"+numeroSerieAntigo+"'";
		return db.executeQuery(sql);
	}

	/**
	 * Adiciona uma nova maquina
	 * @param db Base de dados referente
	 * @param numero_serie Numero de serie da maquina
	 * @param nome Nome da Maquina
	 * @param armazem Nome do armazem
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String numero_serie, String nome ,String armazem) {
		String sql = "INSERT INTO maquina (numero_serie, nome, avariada, id_armazem)"
				   + "VALUES ('"+numero_serie+"', '"+nome+"', "+false+", "+"(SELECT id from armazem where nome='"+armazem+"')"+")";
		return db.executeQuery(sql);
	}
	
	/**
	 * Remove Maquina do Armazem
	 * @param db Base de Dados
	 * @param numeroSerie Numero de Serie da maquina a remover
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean remove(DataBase db, String numeroSerie) {
		String sql = "DELETE FROM maquina WHERE numero_serie='"+numeroSerie+"'";
		return db.executeQuery(sql);
	}

	/**
	 * Remove Maquina do Armazem
	 * @param db Base de Dados
	 * @param numeroSerie Numero de Serie da maquina a remover
	 * @return Boolean True se corrigiu corretamente/ False no caso contrario
	 */
	public boolean corrigeAvariaMaquina(DataBase db, String numeroSerie) {
		String sql = "UPDATE maquina SET avariada='false', descricao_avaria="+null+ " WHERE numero_serie='"+numeroSerie+"'";
		return db.executeQuery(sql);
	}

}
