/**
 * Classe BDProduto
 * Responsavel por tratar da tabela SubCategoriaProduto da base de dados
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

public class SubCategoriaProduto {

	/**
	 * Insere no TableModel a subcategoria
	 * 
	 * @param db             Base de dados referente
	 * @param modelCategoria Tabela onde se insere os dados
	 * @param categoria      Categoria que se prentede analizar
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso
	 *         contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelCategoria, String categoria) {
		String sql = "SELECT * FROM sub_categoria WHERE categoria=" + "'" + categoria + "'";
		ResultSet rs = db.executeQueryResult(sql);

		try {
			while (rs.next()) {
				String subCategoria = rs.getString("sub_categoria");
				modelCategoria.addRow(new Object[] { subCategoria });
			}
		} catch (SQLException e) {
			return false;
		}
		return true;

	}

	/**
	 * Adiciona uma nova subcategoria
	 * 
	 * @param db           Base de dados referente
	 * @param subCategoria Nome da Subcategoria
	 * @param categoria    Categoria a qual pertence
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso
	 *         contrario
	 */
	public boolean insertAll(DataBase db, String subCategoria, String categoria) {
		String sql = "INSERT INTO sub_categoria (sub_categoria, categoria)" + "VALUES ('" + subCategoria + "','"
				+ categoria + "')";
		return db.executeQuery(sql);
	}

	/**
	 * Remove uma subcategoria de uma determinada categoria
	 * 
	 * @param db           Base de dados referente
	 * @param subCategoria Nome da Subcategoria
	 * @param categoria    Categoria a qual pertence
	 * @return Boolean True se eliminou corretamente/ False no caso contrario
	 */
	public boolean remove(DataBase db, String subCategoria, String categoria) {
		String sql = "DELETE FROM  sub_categoria WHERE sub_categoria='" + subCategoria + "' AND categoria='" + categoria
				+ "'";
		return db.executeQuery(sql);
	}

	/**
	 * Devolve todos as sub categorias dos produtos
	 * 
	 * @param db - Base de dados referente
	 * @param cat - SubCategoria do produto
	 * @return ArrayListString - Todas as sub categorias
	 */
	public ArrayList<String> selectSubCat(DataBase db, String cat) {
		ArrayList<String> x = new ArrayList<>();
		String sql = "SELECT sub_categoria " + "FROM sub_categoria " + "WHERE categoria='" + cat + "'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String sub_categoria = rs.getString("sub_categoria");
					x.add(sub_categoria);
				}
		} catch (SQLException e) {
		}
		return x;
	}

}
