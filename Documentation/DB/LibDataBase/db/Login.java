package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class Login {


	public boolean selectAll(DataBase db, DefaultTableModel modelUser) {
		/* NIF, USERNAME, ADMIN */
		db.connect();

		String sql = "SELECT username, data_criacao, admin, nif_funcionario " + "FROM login " + "ORDER BY username";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String username = rs.getString("username");
				Timestamp data = rs.getTimestamp("data_criacao");
				boolean admin = rs.getBoolean("admin");
				String nif = rs.getString("nif_funcionario");
				// System.out.println("user: "+username+"\tnif: "+nif+"\tadmin: "+admin+
				// "\tdata: "+data);
				modelUser.addRow(new Object[] { "" + nif, username, admin });
			}
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return false;
		}
		db.disconnect();
		return true;
	}

	public ArrayList<String> selectNif(DataBase db) {
		db.connect();
		ArrayList<String> x = new ArrayList<>();
		String sql = "SELECT nif " + "FROM funcionario ";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			for (int i = 0; rs.next(); i++) {
				String nif = rs.getString("nif");
				x.add(nif);
				// System.out.println("nif: "+x[i]);
			}
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		db.disconnect();
		return x;
	}

	public boolean insertAll(DataBase db, int nif, String user, String password, boolean admin) {
		db.connect();

		/* Adiciona USER, PASSWORD, ADMIN no respetivo NIF */
		String sql = "INSERT INTO login " + "VALUES ('" + user + "', '" + password + "'," + "'" + db.localDate() + "'" + ", " + admin
				+ ", " + nif + ")";
		// System.out.println(sql);
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			return false;
		}
		db.disconnect();

		return true;
	}

	public boolean updateUser(String nif, String user, boolean admin) {
		/* D update no user com NIF=nif, com o user e admin respetivo */

		return true;
	}

	public boolean remove(DataBase db, String username) {
		/* remove user com NIF = nif na tabela LOGIN */
		db.connect();
		/* Adiciona USER, PASSWORD, ADMIN no respetivo NIF */
		String sql = "DELETE FROM login WHERE username="+"'"+username+"'";
		 System.out.println(sql);
		try {
			Statement stmt = db.getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
			return false;
		}
		db.disconnect();

		return true;
	}
}
