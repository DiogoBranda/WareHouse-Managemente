/**
 * Classe BDProduto
 * Responsavel por tratar da tabela Produto da base de dados
 * 
 * @author  Auto-buys
 * @version 1.0
 * @since   2019-11-22 
 */
package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Produto {

	/**
	 * Insere no TableModel o nome e Sku dos produtos vendidos
	 * 
	 * @param db           Base de dados referente
	 * @param modelProduto Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso
	 *         contrario
	 */
	public boolean selectProdutoVendido(DataBase db, DefaultTableModel modelProduto) {
		modelProduto.setRowCount(0);// eliminar todos os dados contidos na tabela
		/* NOME, numero de serie */
		String sql = "SELECT data_saida,sku,nome, destino " + "FROM produto "
				+ "inner join lote on num_lote=numero_lote " + "WHERE vendido='true'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String data = rs.getString("data_saida");
					String sku = rs.getString("sku");
					String nome = rs.getString("nome");
					String destino = rs.getString("destino");
					modelProduto.addRow(new Object[] { data, sku, nome, destino });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Insere no TableModel o Sku, nome, origem e se está com defeito ou nao dos
	 * produtos nao vendidos
	 * 
	 * @param db           Base de dados referente
	 * @param modelProduto Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso
	 *         contrario
	 */
	public boolean selectProdutoNaoVendido(DataBase db, DefaultTableModel modelProduto) {
		modelProduto.setRowCount(0);// eliminar todos os dados contidos na tabela
		/* NOME, numero de serie */
		String sql = "SELECT sku, nome, origem, com_defeito " + "FROM produto "
				+ "inner join lote on num_lote=numero_lote " + "WHERE vendido='false'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String sku = rs.getString("sku");
					String nome = rs.getString("nome");
					String origem = rs.getString("origem");
					boolean comDefeito = rs.getBoolean("com_defeito");
					modelProduto.addRow(new Object[] { sku, nome, origem, comDefeito });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Insere no TableModel todas as SKUs dos produtos não vendidos
	 * 
	 * @param db-          Base de dados referente
	 * @param modelProduto - Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso
	 *         contrario
	 */
	public boolean selectAllSKUsNaoVendidos(DataBase db, DefaultTableModel modelProduto) {
		modelProduto.setRowCount(0);// eliminar todos os dados contidos na tabela
		/* NOME, numero de serie */
		String sql = "SELECT sku FROM produto inner join lote on num_lote=numero_lote " + "WHERE vendido='false'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String sku = rs.getString("sku");
					modelProduto.addRow(new Object[] { sku });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Insere informação do Produto
	 * 
	 * @param db      Base de Dados referente
	 * @param sku     Numero do produto
	 * @param numLote Numero de lote a que pertence o produto
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String sku, String numLote) {
		String sql = "INSERT INTO produto(sku, data_saida, com_defeito, num_lote,vendido)" + "VALUES ('" + sku + "'"
				+ ", " + null + ", 'false', '" + numLote + "', 'false')";
		return db.executeQuery(sql);
	}

	/**
	 * Coloca o produto com a SKU especificada como defeituoso
	 * 
	 * @param db - Base de Dados referente
	 * 
	 * @param sku - Numero do produto
	 * 
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean marcarDefeitoProduto(DataBase db, String sku) {
		String sql = "UPDATE produto SET com_defeito = 'true' WHERE sku='" + sku + "'";
		return db.executeQuery(sql);
	}


	/**
	 * 	Envia produto
	 * @param db - Base de dados referente
	 * @param sku - SKU do produto
	 * @param Data - Data de venda
	 * @param destino - destino do produto
	 * @return Boolean True se operou corretamente/ False no caso contrario
	 */
	public boolean produtoEnviado(DataBase db, String sku, String Data,String destino) {
		String sql = "UPDATE produto SET data_saida = '" + Data + "'" + ", vendido='true', destino='"+destino+"' WHERE sku='" + sku + "'";
		return db.executeQuery(sql);

	}
}
