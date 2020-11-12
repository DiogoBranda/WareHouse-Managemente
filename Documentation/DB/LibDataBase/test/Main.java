package test;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import db.Armazem;
import db.DataBase;
//import warehouse.dataBase;
//import warehouse.insert;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Inicialização das variáveis
		DataBase DB = new DataBase();
		
		
		//Ligação à Base de Dados

		//Teste
		DefaultTableModel a=new DefaultTableModel();
		if(DB.getArmazem().selectAll(DB)) System.out.println("1");
		if(DB.getCategoriaProduto().selectAll(DB)) System.out.println("2");
		if(DB.getFornecedor().selectAll(DB,"warehouse1",a)) System.out.println("3");
		if(DB.getFuncionario().selectNomeNif(DB, a,"warehouse1")) System.out.println("4");
		String[] x=DB.getFuncionario().selectAllNif(DB, "254422772");
		String format = "%-25s%-25s%-25s%-25s%s%n";
		System.out.printf(format,"nome: "+x[0],"idade: "+x[1],"fun��o: "+x[2],"sal�rio: "+x[3],"id_aramzem: "+x[4]);
		if(DB.getLogin().selectAll(DB,a))System.out.println("5");
		ArrayList<String> y =DB.getLogin().selectNif(DB);
		System.out.println("nif: "+y.get(1));
		if(DB.getMaquina().selectNomeNumero(DB, "warehouse1",a))System.out.println("6");
		if(DB.getSubCategoriaProduto().selectAll(DB))System.out.println("7");
		if(DB.getProduto().selectNomeSKUvendidos(DB, a))System.out.println("8");
		if(DB.getProduto().selectNomeSKUnaoVendidos(DB, a))System.out.println("9");
		if(DB.getMaquina().selectAvaria(DB, "warehouse1",a))System.out.println("10");
		//Fim do teste
		
		//Desconecta da Base de Dados

	}
}
