package db;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class Funcionario {
/*retornem true ou false, dependendo se corre tudo bem ou nao*/
	
	/*Metam como parametro tudo o que precisarem (qual a warehouse a usar)
	 * Meter as dependencias todas!
	 * 
	 * */
	
	public boolean selectNomeNif(DataBase db, DefaultTableModel modelFuncionario, String armazem) {
	//	modelUser.setRowCount(0);//eliminar todos os dados contidos na tabela, fazer isto no inicio
		/*NOME, NIF*/
		db.connect();
		String sql = "SELECT nome,nif "
				   + "FROM funcionario "
				   + "WHERE id_armazem=(SELECT id from armazem where nome='"+armazem+"') "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String nome = rs.getString("nome");
				String nif = rs.getString("nif");
				String format = "%-25s%s%n";
				System.out.printf(format,"nome: "+nome,"nif: "+nif);
				modelFuncionario.addRow(new Object[] { nif, nome });
			}
		} catch (Exception e) {
			db.disconnect();
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
			return false;
		}
		db.disconnect();
		return true;
	}
	
	public String[] selectAllNif(DataBase db,String n) {
		db.connect();
		String[] x= new String[20]; 
		
		String sql = "SELECT * "
				   + "FROM funcionario "
				   + "WHERE nif='"+n+"' "
				   + "ORDER BY nome";
		Statement stmt = null;
		try {
			stmt = db.getC().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				String nome = rs.getString("nome");
				int idade = rs.getInt("idade");
				String funcao = rs.getString("funcao");
				int salario = rs.getInt("salario");
				int id_aramzem = rs.getInt("id_armazem");
				x[0]=nome;
				x[1]=String.valueOf(idade);
				x[2]=funcao;
				x[3]=String.valueOf(salario);
				x[4]=String.valueOf(id_aramzem);
				String format = "%-25s%-25s%-25s%-25s%s%n";
				System.out.printf(format,"nome: "+x[0],"idade: "+x[1],"funo: "+x[2],"salario: "+x[3],"id_aramzem: "+x[4]);
			}
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
		db.disconnect();
		return x;
	}
	
	

	
	public boolean insertAll(DataBase db, int nif, String nome, int idade, String funcao, int salario, String armazem ) {
		/*Adiciona nif, nome, idade....etc o que est na tabela */
		db.connect();
		String sql = "INSERT INTO funcionario(nif, nome, idade, funcao, salario, id_armazem)"
				   + "VALUES ("+nif+", '"+nome+"', "+idade+", '"+funcao+"',"+salario+", "+"(SELECT id from armazem where nome='"+armazem+"') "+")";
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
	
	public boolean updateUser(DataBase db, String nifAntigo,String nif, String nome, String AdicionemOsParametrosNecessarios) {
		/*D update no NIF=nifAntigo com os respetivos novos valores*/
		/*posso querer editar o nif, entao vamos dar update na tabela todo WHERE nif que tinha*/
		db.connect();
		String sql ="UPDATE funcionario "
				  + "SET nif = '"+nif+"' "
				  + "WHERE nif = '"+nifAntigo+"';";
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
	
	public boolean remove(DataBase db, String nif) {
		/*remove funcionario*/
		db.connect();
		String sql = "DELETE FROM funcionario WHERE nif="+nif;
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