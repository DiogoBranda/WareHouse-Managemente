/**
 * Classe BDFornecedor
 * Responsavel por tratar da tabela Fornecedor da base de dados
 * 
 * @author  Auto-buys
 * @version 1.0
 * @since   2019-11-22 
 */
package db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class Fornecedor {

	
	/**
	 * Insere no TableModel o Nome dos Fornecedores 
	 * @param db Base de dados referente 
	 * @param modelFornecedor Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelFornecedor) {
		String sql = "SELECT id, nome, id_armazem "
				   + "FROM fornecedor";
		ResultSet rs =  db.executeQueryResult(sql);
		try {
			while(rs.next()) {
					String nome = rs.getString("nome");
					modelFornecedor.addRow(new Object[] { nome });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Insere o Fornecedor no Armazem 
	 * @param db base de dados referente 
	 * @param nome Nome da categoria 
	 * @param nomeArmazem nome do Armazem onde se pretende inserir o Fornecedor
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String nome, String nomeArmazem) {
		String sql = "INSERT INTO fornecedor (nome, id_armazem)"
				   + "VALUES ('"+nome+"',"+"(SELECT id FROM armazem WHERE nome="+"'"+nomeArmazem+"')"+")";
		return db.executeQuery(sql);
	}
	
	/**
	 * Update dos dados do fornecedor
	 * @param db Base de dados referente 
	 * @param nomeAntigo Nome Antigo do fornecedor
	 * @param nome Nome novo do fornecedor
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean update(DataBase db, String nomeAntigo,String nome) {
		String sql = "UPDATE fornecedor " + 
				"SET nome="+"'"+nome+"'"+
				"WHERE nome="+"'"+nomeAntigo+"'";
		return db.executeQuery(sql);
	}
	
	/**
	 * Remove o fornecedor da base de dados
	 * @param db Base de dados referente 
	 * @param nome Nome do fornecedor 
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean remove(DataBase db, String nome) {
		String sql = "DELETE FROM fornecedor WHERE nome="+"'"+nome+"'";
		return db.executeQuery(sql);
	}
	
	/**
	 * Devolve todos os fornecedores de produtos
	 * @param db Base de dados referente 
	 * @return ArrayListString Todos os Fornecedores
	 */
	public ArrayList<String> selectFunc(DataBase db) {
		ArrayList<String> x = new ArrayList<>();
		String sql = "SELECT nome "
				   + "FROM fornecedor";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String fornecedores = rs.getString("nome");
					x.add(fornecedores);
				}
		} catch (SQLException e) {
		}
		return x;
	}
	
}
