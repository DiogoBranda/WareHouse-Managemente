/**
* Classe BDCategoriaProduto
* Responsavel por tratar da tabela categoria de produtos
* da base de dados 
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

public class CategoriaProduto {
	
	/**
	 * Insere no TableModel o Nome da Categoria do produto 
	 * @param db Base de dados referente 
	 * @param modelCategoria Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelCategoria) {
		String sql = "SELECT * FROM categoria_produto ";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while(rs.next()) {
					String nome = rs.getString("nome");
					modelCategoria.addRow(new Object[] { nome });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;	
	}
	
	/**
	 * Insere o nome da categoria no Armazem 
	 * @param db Base de dados referente 
	 * @param nome Nome da categoria 
	 * @param armazem Armazem onde se pretende inserir a categoria
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String nome, String armazem) {
		String sql = "INSERT INTO categoria_produto (nome, id_armazem) "
				+ "VALUES ('"+nome+"',"+"(SELECT id FROM armazem WHERE nome ="+"'"+armazem+"')"+")";
		return db.executeQuery(sql);
	}
	
	/**
	 * Remove a Categoria 
	 * @param db Base de dados referente 
	 * @param nome Nome da categoria
	 * @return boolean True se removeu corretamente / False no caso contrario
	 */
	public boolean remove(DataBase db, String nome) {
		String sql = "DELETE FROM categoria_produto WHERE nome="+"'"+nome+"'";		 
		return db.executeQuery(sql);
	}
	
	/**
	 * Devolve todos as categorias dos produtos
	 * @param db Base de dados referente 
	 * @return ArrayListString Todas as categorias ou null caso haja erro
	 */
	public ArrayList<String> selectCat(DataBase db) {
		ArrayList<String> x = new ArrayList<>();
		String sql = "SELECT nome " + "FROM categoria_produto ";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String categoria = rs.getString("nome");
					x.add(categoria);
				}
		} catch (SQLException e) {
		}
		return x;
	}


}