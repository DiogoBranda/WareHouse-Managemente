package db;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Logs {
	/**
	 * Insere log do que o user fez
	 * @param db - Base de Dados referente
	 * @param dadosLog - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param acao - ação que o user fez
	 * @param acaoCompleta - ação que o user fez de modo detalhada
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String dadosLog, String acao, String acaoCompleta) {
		String[] dados = dadosLog.split(";");
		if(dados.length != 4)
			return false;
		String sql = "INSERT INTO logs (data,username, admin, acao_min, acao_compl, ip, id_armazem) "
				   + "VALUES ('"+db.localDate() +"', "+"'"+dados[0]+"', "+" '"+dados[1]+"', '"+acao+"', '"+acaoCompleta+"', '"+dados[3]+"', "+"(SELECT id from armazem where nome='"+dados[2]+"'))";
		return db.executeQuery(sql);
	}
	
	/**
	 * Insere no TableModel o historico dos users 
	 * @param db - Base de dados referente 
	 * @param modelFornecedor - Tabela onde se insere os dados
	 * @param numeroLinhas - numero de linhas que deseja visualizar
	 * @return Boolean - True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelFornecedor, String numeroLinhas) {
		String sql = "SELECT data, username, admin, acao_min, ip FROM (SELECT * from logs ORDER BY data DESC LIMIT "+numeroLinhas+") as ta ORDER BY ta.data";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while(rs.next()) {
					String data =rs.getString("data");
					String username = rs.getString("username");
					boolean admin = rs.getBoolean("admin");
					String acao = rs.getString("acao_min");
					String ip = rs.getString("ip");
					modelFornecedor.addRow(new Object[] { data, username, admin, acao, ip });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
}
