/**
 * Classe BDLogin
 * Responsavel por tratar da tabela Login da base de dados
 * 
 * @author  Auto-buys
 * @version 1.0
 * @since   2019-11-22 
 */
package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Login {

	/**
	 * Insere no TableModel o nif, usarname e se é admin ou nao
	 * @param db Base de Dados referente
	 * @param modelUser Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean selectAll(DataBase db, DefaultTableModel modelUser) {
		String sql = "SELECT username, data_criacao, admin, nif_funcionario " + "FROM warehouse.login";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String username = rs.getString("username");
					boolean admin = rs.getBoolean("admin");
					String nif = rs.getString("nif_funcionario");
					modelUser.addRow(new Object[] { "" + nif, username, admin });
				}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Devolve todos os nif's dos funcionarios
	 * @param db Base de dados referente 
	 * @return ArrayListString Todos os nifs dos funcionarios
	 */
	public ArrayList<String> selectNif(DataBase db) {
		ArrayList<String> x = new ArrayList<>();
		String sql = "SELECT nif " + "FROM funcionario ";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					String nif = rs.getString("nif");
					x.add(nif);
				}
		} catch (SQLException e) {
		}
		return x;
	}
	
	/**
	 * Devolve o nome do armazem a que o utilizador pertence
	 * @param db Base de Dados referente
	 * @param username Nome do Utilizador
	 * @return String Nome do armazem 
	 */
	public String selectNameArmazem(DataBase db, String username) {
		String x=null;
		String sql = "SELECT nome FROM armazem WHERE id= "
				+ "(SELECT id_armazem FROM funcionario WHERE nif= "
				+ "(Select nif_funcionario FROM login WHERE username = '"+username+"'))";
		ResultSet rs =  db.executeQueryResult(sql);
		try {
			while (rs.next()) {
					x = rs.getString("nome");
				}
		} catch (SQLException e) {
		}
		return x;
	}
	
	/**
	 * Devolve o username do email enviado
	 * @param db - Base de Dados referente
	 * @param email - Email do utilizador
	 * @return String - Username correspondente ou null caso haja erro 
	 */
	public String selectUsernameByEmail(DataBase db, String email) {
		String username=null;
		String sql = "SELECT username FROM login WHERE email='"+email+"'";
		ResultSet rs =  db.executeQueryResult(sql);

		try {
			while (rs.next()) {
					username = rs.getString("username");
				}
		} catch (SQLException e) {
		}
		return username;
	}
	
	/**
	 * Insere User na base de dados
	 * @param db Base de dados referente
	 * @param dadosUser -  contem o nif, nome e email do utilizador
	 * @param password Password do Utilizador
	 * @param admin Se é admin True caso contrario Falso
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean insertAll(DataBase db, String dadosUser, String password, boolean admin) {
		String[] dados = dadosUser.split(";");
		if(dados.length != 3)
			return false;
		String sql = "INSERT INTO login " + "VALUES ('" + dados[1] + "', '" + password + "'," + "'" + db.localDate() + "'" + ", " + admin
				+ ", '" + dados[0] + "', '"+dados[2]+"')";
		return db.executeQuery(sql);
	}

	/**
	 * Upadate nos dados do User
	 * @param db Base de Dados referente
	 * @param dadosUser - contem o nome de utilizador antigo e o nome de utilizador novo
	 * @param pass PassWord do utilizador
	 * @param admin True se é admin | False caso contrario
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean update(DataBase db, String dadosUser, String pass, boolean admin) {
		String[] dados = dadosUser.split(";");
		if(dados.length != 2)
			return false;
		String sql = "UPDATE login " + 
					 "SET username='"+dados[1]+"' ,password='"+pass+"' ,admin="+admin+
					 " WHERE username='"+dados[0]+"'";
		return db.executeQuery(sql);
	}
	
	/**
	 * Update na password do user
	 * @param db -  Base de Dados referente
	 * @param user - Novo Nome do user 
	 * @param pass - PassWord do utilizador
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean updatePassword(DataBase db, String user, String pass) {
		String sql = "UPDATE login " + 
					 "SET password='"+pass+"' WHERE username='"+user+"'";
		return db.executeQuery(sql);
	}

	/**
	 * Remove o user com um certo username
	 * @param db Base de dados referente
	 * @param username Nome do utilizador a remover
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean remove(DataBase db, String username) {
		String sql = "DELETE FROM login WHERE username="+"'"+username+"'";
		return db.executeQuery(sql);
	}

	/**
	 * Valida os dados inseridos
	 * @param username Username do Utilizador
	 * @param password Password do Utilizador
	 * @param db Base de Dados Referente
	 * @return 0 admin | 1 user | caso contrario nao existe
	 */
	public int checkLogin(String username, String password, DataBase db) {
		db.connect();
		String sql = "SELECT *"
				   + "FROM login;";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String user = rs.getString("username");
				String passEncr = rs.getString("password");
				boolean admin = rs.getBoolean("admin");
				
				if(username.equals(user) && BCrypt.checkpw(password,passEncr)) {
					if(admin) {
						rs.close();
						stmt.close();
						return 0;
					}else if(!admin){
						rs.close();
						stmt.close();
						return 1;
					}
				}
			}
		} catch (Exception e) {
			db.disconnect();
		}
		db.disconnect();
		return 3;
	}

}
