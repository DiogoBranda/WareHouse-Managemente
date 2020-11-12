package db;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Produto {
	
	public boolean selectNomeSKUvendidos(DataBase db, DefaultTableModel modelProduto) {
		db.connect();
		modelProduto.setRowCount(0);//eliminar todos os dados contidos na tabela
		/*NOME, numero de serie*/
		String sql = "SELECT sku, nome "
				   + "FROM produto "
				   + "inner join lote on num_lote=numero_lote "
				   + "WHERE vendido='true' "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String nome =rs.getString("nome");
				String sku = rs.getString("sku");
				String format = "%-25s%s%n";
				System.out.printf(format,"nome: "+nome,"sku: "+sku);
				modelProduto.addRow(new Object[] {nome , sku });
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
	
	public boolean selectNomeSKUnaoVendidos(DataBase db, DefaultTableModel modelProduto) {
		db.connect();
		modelProduto.setRowCount(0);//eliminar todos os dados contidos na tabela
		/*NOME, numero de serie*/
		String sql = "SELECT sku, nome "
				   + "FROM produto "
				   + "inner join lote on num_lote=numero_lote "
				   + "WHERE vendido='false' "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String nome =rs.getString("nome");
				String sku = rs.getString("sku");
				String format = "%-25s%s%n";
				System.out.printf(format,"nome: "+nome,"sku: "+sku);
				modelProduto.addRow(new Object[] {nome , sku });
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

}
