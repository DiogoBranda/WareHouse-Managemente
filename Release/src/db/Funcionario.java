/**
 * Classe BDFuncionario
 * Responsavel por tratar da tabela Funcionario da base de dados
 * 
 * @author  Auto-buys
 * @version 1.0
 * @since   2019-11-22 
 */
package db;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class Funcionario {
	
	/**
	 * Insere no TableModel o nif, nome, idade e função do funcionario
	 * @param db Base de dados referente 
	 * @param armazem Armazem onde o funcionario trabalha
	 * @param modelFuncionario Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectNifIdadeNomeFuncao(DataBase db,String armazem, DefaultTableModel modelFuncionario) {
			String sql = "SELECT * "
					   + "FROM funcionario "
					   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"')";
			ResultSet rs =  db.executeQueryResult(sql);

			try {
				while(rs.next()) {
						String nif =rs.getString("nif");
						String nome =rs.getString("nome");
						String funcao=rs.getString("funcao");
						int idade = rs.getInt("idade");
						modelFuncionario.addRow(new Object[] {nif,nome , ""+idade,funcao });
					}
			} catch (SQLException e) {
				return false;
			}
			return true;
		}
	
	/**
	 * Insere no TableModel o nome, e nif do funcionario
	 * @param db Base de dados referente
	 * @param modelFuncionario Tabela onde se insere os dados
	 * @param armazem armazem onde o funcionario trabalha
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectNomeNif(DataBase db, DefaultTableModel modelFuncionario, String armazem) {
		String sql = "SELECT nome,nif "
				   + "FROM warehouse.funcionario "
				   + "WHERE id_armazem=(SELECT id from warehouse.armazem where nome='"+armazem+"')";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while(rs.next()) {
					String nome = rs.getString("nome");
					String nif = rs.getString("nif");
					modelFuncionario.addRow(new Object[] { nif, nome });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Devolve um vetor com todos os dados de um certo funcionario
	 * @param db Base de dados referente
	 * @param nif Nif do funcionario
	 * @return Vetor String com Nome Idade Salario Função e armazem do funcionario
	 */
	public String[] selectAllNif(DataBase db,String nif) {
		String[] x= new String[5]; 
		
		String sql = "SELECT * "
				   + "FROM funcionario "
				   + "WHERE nif="+"'"+nif+"'"+" "
				   + "ORDER BY nome";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			if(rs.next()) {
					String nome = rs.getString("nome");
					int idade = rs.getInt("idade");
					String função = rs.getString("funcao");
					double salário = rs.getDouble("salario");
					int id_aramzem = rs.getInt("id_armazem");
					x[0]=nome;
					x[1]=String.valueOf(idade);
					x[2]=função;
					x[3]=String.valueOf(salário);
					x[4]=String.valueOf(id_aramzem);
				}
		} catch (SQLException e) {
		}		
		return x;
	}
	
	/**
	 * Insere um novo Funcionario
	 * @param db Base de dados referente
	 * @param dadosFunc - contem o nif, nome, idade, funçao, salario e armazem onde trabalha
	 * @return Boolean True se inseriu corretamente/False caso contrario
	 */
	public boolean insertAll(DataBase db, String dadosFunc) {
		String[] dados= dadosFunc.split(";");
		if(dados.length != 6)
			return false;
		String sql = "INSERT INTO funcionario(nif, nome, idade, funcao, salario, id_armazem)"
				   + "VALUES ("+"'"+dados[0]+"'"+", '"+dados[1]+"', "+dados[2]+", '"+dados[3]+"',"+dados[4]+", "+"(SELECT id from armazem where nome='"+dados[5]+"') "+")";
		return db.executeQuery(sql);
	}
	
	/**
	 * Update no utilizador
	 * @param db Base de Dados referente 
	 * @param nifAntigo Nif antigo 
	 * @param novosDados dados do novo funcionario separado por ";", contem o nif, nome, idade, funcao e salario do funcionario
	 * @return Boolean True se inseriu corretamente/False caso contrario
	 */
	public boolean updateFuncionario(DataBase db, String nifAntigo, String novosDados) {
		String[] dados= novosDados.split(";");
		if(dados.length != 5)
			return false;
		String sql ="UPDATE funcionario "
				  + "SET nif = '"+dados[0]+"' ,nome= '"+dados[1]+"' ,idade= '"+dados[2]+"' ,funcao= '"+dados[3]+"' ,salario=' "+dados[4]+"'"
				  + " WHERE nif = '"+nifAntigo+"'";
		return db.executeQuery(sql);
	}

	/**
	 * Remove o funcionario da base de dados
	 * @param db Base de Dados referente
	 * @param nif Nif do funcionario
	 * @return Boolean True se removeu corretamente/False caso contrario
	 */
	public boolean remove(DataBase db, String nif) {
		String sql = "DELETE FROM funcionario WHERE nif="+"'"+nif+"'";
		return db.executeQuery(sql);

	}
}