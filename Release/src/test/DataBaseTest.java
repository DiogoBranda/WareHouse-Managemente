package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import db.DataBase;

class DataBaseTest {

	private final String armazem="warehouse1";
	
	@Test
	public void testClass() {
		DataBase db = DataBase.getInstance();

		
		//mudar de credenciais
		db.setPassword("testePassword");
		db.setUrl("testeUrl");
		db.setUser("testUser");
		assertEquals("jdbc:postgresql://testeUrl?currentSchema=warehouse", db.getUrl());
		assertEquals("testePassword", db.getPassword());
		assertEquals("testUser", db.getUser());
		assertEquals(false, db.checkConnection());
		db.setC(null);
		assertEquals(false, db.connect());
		assertEquals(false, db.disconnect());
		
		db.setPassword("UbwJSLsu");
		db.setUrl("db.fe.up.pt:5432");
		db.setUser("sinf19a38");
		assertEquals(true, db.checkConnection());

		File file = new File("baseDados\\SQL_CreateTables.sql");
		assertEquals(true,db.createTable(file));

		assertEquals(false,db.createTable(null));

		file = new File("baseDados\\invalid.sql");
		assertEquals(false,db.createTable(file));
	}
	
	@Test
	public void testConnection() {
		DataBase db = DataBase.getInstance();
		assertEquals(true, db.connect());
		assertEquals(true, db.disconnect());
	}
	
	@Test
	public void testArmazem() {
		DataBase db = DataBase.getInstance();
		
		/*Adiciona*/
		assertEquals(true, db.addArmazem("nomeTeste", "nomeLocalizacao"));
		assertEquals(false, db.addArmazem("nomeTeste", "nomeLocalizacao"));//repetido
		DefaultTableModel modelArmazem = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Localização" });
		assertEquals(true, db.nomelocalizacaoArmazem(modelArmazem));
		assertEquals("nomeLocalizacao", db.localizacaoArmazem("nomeTeste"));
		
		/*Update*/
		assertEquals(true, db.updateArmazem("nomeTeste", "novoNome", "novaLocalizacao"));
		assertNotEquals("nomeLocalizacao", db.localizacaoArmazem("nomeTeste"));
		assertEquals("novaLocalizacao", db.localizacaoArmazem("novoNome"));

		
		/*Remove*/
		assertEquals(true, db.removeArmazem("novoNome"));
		assertNotEquals("novaLocalizacao", db.localizacaoArmazem("novoNome"));
		assertEquals(null, db.localizacaoArmazem("novoNome"));

	}
	

	@Test
	public void testCategoria() {
		DataBase db = DataBase.getInstance();
		db.removeCategoriaProduto("nomeCategoria");
		/*Adiciona*/
		assertEquals(true, db.addCategoriaProduto("nomeCategoria", armazem));
		assertEquals(false, db.addCategoriaProduto("nomeCategoria", armazem));//repetido
		DefaultTableModel modelCategoria = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Localização" });
		assertEquals(true, db.categoriaProduto(modelCategoria));
		assertEquals("nomeCategoria", modelCategoria.getValueAt(modelCategoria.getRowCount()-1, 0));
		
		ArrayList<String> result= db.getAllCategoria();
		assertEquals("nomeCategoria", result.get(result.size()-1));


		/*Remove*/
		int size = modelCategoria.getRowCount();
		modelCategoria.setRowCount(0);
		assertEquals(true, db.removeCategoriaProduto("nomeCategoria"));
		assertEquals(true, db.categoriaProduto(modelCategoria));
		assertNotEquals("nomeCategoria", modelCategoria.getValueAt(modelCategoria.getRowCount()-1, 0));
		assertEquals((long)size-1, modelCategoria.getRowCount());

	}

	@Test
	public void testFornecedor() {
		DataBase db = DataBase.getInstance();
		
		/*	Adiciona	*/
		assertEquals(true, db.addFornecedor("nomeFornecedor", armazem));
		assertEquals(true, db.addFornecedor("nomeFornecedor", armazem));//repetido
		DefaultTableModel modelFornecedor = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Localização" });
		assertEquals(true, db.nomeFornecedor(modelFornecedor));
		
		ArrayList<String> result= db.getAllFornecedores();
		int i;
		for(i = 0; i < result.size(); i++) {
			if("nomeFornecedor".equals(result.get(i)))
				break;
		}
		assertEquals("nomeFornecedor", result.get(i));
		
		/*Update*/
		modelFornecedor.setRowCount(0);
		assertEquals(true, db.updateFornecedor("nomeFornecedor", "novoFornecedor"));
		assertEquals(true, db.nomeFornecedor(modelFornecedor));
		

		/*Remove*/
		modelFornecedor.setRowCount(0);
		assertEquals(true, db.removeFornecedor("novoFornecedor"));
		assertEquals(true, db.removeFornecedor("novoFornecedor"));
		assertEquals(true, db.nomeFornecedor(modelFornecedor));
		assertNotEquals("novoFornecedor", modelFornecedor.getValueAt(modelFornecedor.getRowCount()-1, 0));
		assertNotEquals("novoFornecedor", modelFornecedor.getValueAt(modelFornecedor.getRowCount()-2, 0));

	}
	
	@Test
	public void testFuncionario() {
		DataBase db = DataBase.getInstance();
		db.removeFuncionarioByNif("123456789123");
		/*Adiciona*/
		assertEquals(true, db.addFuncionario("123456789123"+";"+"nome"+";"+22+";"+"engenheiro"+";"+500.5+";"+armazem));
		assertEquals(false, db.addFuncionario("123456789123"+";"+"nomediferente"+";"+42+";"+"funcionario"+";"+500.5+";"+ armazem));
		assertEquals(false, db.addFuncionario("123456789123"+";"+"nomediferente"+";"+42+";"+"funcionario"+";"+500.5+";"+ "nome de armazem invalido"));

		String[] dados= {"nome","22","engenheiro","500.5", "1"};//1-> id do armazem em questão
		assertTrue(Arrays.equals(dados, db.getFuncinarioByNif("123456789123")));
		DefaultTableModel modelFuncionario = new DefaultTableModel(new Object[][] {},
				new String[] { "nif","Nome", "idade", "funçao" });
		assertEquals(true, db.nifIdadeNomeFuncaoFuncionario(armazem,modelFuncionario));
		assertEquals("123456789123", modelFuncionario.getValueAt(modelFuncionario.getRowCount()-1, 0));
		assertEquals("nome", modelFuncionario.getValueAt(modelFuncionario.getRowCount()-1, 1));
		assertEquals("22", modelFuncionario.getValueAt(modelFuncionario.getRowCount()-1, 2));
		assertEquals("engenheiro", modelFuncionario.getValueAt(modelFuncionario.getRowCount()-1, 3));

		DefaultTableModel modelFuncionario2 = new DefaultTableModel(new Object[][] {},
				new String[] {"Nome", "idade", "funçao" });
		assertEquals(true, db.nomeNifFuncionario(modelFuncionario2, armazem));
		assertEquals("123456789123", modelFuncionario.getValueAt(modelFuncionario2.getRowCount()-1, 0));
		assertEquals("nome", modelFuncionario.getValueAt(modelFuncionario2.getRowCount()-1, 1));

		
		
		/*Update*/
		String[] dados2= {"nomeNovo","32","engenheiro2","700.5", "1"};//2-> id do armazem em questão
		assertEquals(true, db.updateFuncionario("123456789123","987654321;nomeNovo;32;engenheiro2;700.5"));
		assertTrue(Arrays.equals(dados2, db.getFuncinarioByNif("987654321")));
		
		/*Remove*/
		assertEquals(true, db.removeFuncionarioByNif("987654321"));
		assertTrue(Arrays.equals(new Object[] {null, null, null, null, null}, db.getFuncinarioByNif("987654321")));
	}
	
	@Test
	public void testLogin() {
		DataBase db = DataBase.getInstance();
		
		db.addFuncionario("123456789"+";"+"nome"+";"+22+";"+"engenheiro"+";"+500.5+";"+armazem);//adiciona funcionario
		db.removeUserLogin("username");
		db.removeUserLogin("username123");

		/*Adiciona*/
		String pass_encri = BCrypt.hashpw(String.valueOf("pass"), BCrypt.gensalt());

		assertEquals(true, db.addUserLogin("123456789"+";"+"username"+";"+"fabio@hotmail.com",pass_encri, true));
		assertEquals(true, db.addUserLogin("123456789"+";"+"username123"+";"+"fabio2@hotmail.com", pass_encri, false));
		assertEquals(false, db.addUserLogin("123456789"+";"+"username123"+";"+"fabio@hotmail.com", pass_encri, false));

		/*Check logig*/
		assertEquals(0, db.checkUserLogin("username", "pass"));
		assertEquals(1, db.checkUserLogin("username123", "pass"));
		assertEquals(3, db.checkUserLogin("username invalido", "pass"));
		assertEquals(3, db.checkUserLogin("username", "pass invalida"));

		assertEquals("username", db.getUsernameByEmail("fabio@hotmail.com"));
		assertEquals("username123", db.getUsernameByEmail("fabio2@hotmail.com"));
		assertEquals(null, db.getUsernameByEmail("naoexiste@hotmail.com"));

		ArrayList<String> nifs=db.getAllNifLogin();
		assertEquals("123456789", nifs.get(nifs.size()-1));
		
		DefaultTableModel modelUser = new DefaultTableModel(new Object[][] {},
				new String[] { "nifs", "username", "admin" });
		assertEquals(true, db.nifusernameadminLogin(modelUser));
		assertEquals("123456789", modelUser.getValueAt(modelUser.getRowCount()-1, 0));
		assertEquals("username123", modelUser.getValueAt(modelUser.getRowCount()-1, 1));
		assertEquals(false, modelUser.getValueAt(modelUser.getRowCount()-1, 2));

		assertEquals(armazem, db.getUserarmazemLogin("username"));
		
		/*Update*/
		assertEquals(true, db.updateUserLogin("username"+";"+"username1","passnova",true));
		modelUser.setRowCount(0);
		assertEquals(true, db.nifusernameadminLogin(modelUser));
		assertEquals("123456789", modelUser.getValueAt(modelUser.getRowCount()-1, 0));
		assertEquals("username1", modelUser.getValueAt(modelUser.getRowCount()-1, 1));
		assertEquals(true, modelUser.getValueAt(modelUser.getRowCount()-1, 2));	
		
		assertEquals(true, db.updateUserPassword("username1","novaPass"));
		
		/*Remove*/
		assertEquals(true, db.removeUserLogin("username1"));
		assertEquals(true, db.removeUserLogin("username123"));
		assertNotEquals(armazem, db.getUserarmazemLogin("username1"));
		assertNotEquals(armazem, db.getUserarmazemLogin("username123"));

		assertEquals(true, db.removeFuncionarioByNif("123456789"));//remove funcionario

	}
	private void update(DataBase db, DefaultTableModel modelMaquina) {
		assertEquals(true, db.updateMaquina("0","11111110", "novoNome"));
		modelMaquina.setRowCount(0);
		assertEquals(true, db.nomenumeroestadoMaquina(armazem,modelMaquina));
		int i;
		for(i = 0; i < modelMaquina.getRowCount(); i++){
			if(modelMaquina.getValueAt(i, 0).equals("novoNome"))
				break;
		}
		assertEquals("novoNome", modelMaquina.getValueAt(i, 0));
		assertEquals("11111110", modelMaquina.getValueAt(i, 1));
		assertEquals(true, modelMaquina.getValueAt(i, 2));
	}
	@Test
	public void testMaquina() {
		DataBase db = DataBase.getInstance();
		db.removeMaquina("0");
		db.removeMaquina("11111110");

		/*Adiciona*/
		assertEquals(true, db.addMaquina("0","maquinaTESTE", armazem));
		assertEquals(false, db.addMaquina("0","maquinaTESTE", armazem));
		assertEquals(false, db.addMaquina("43145","maquinaTESTE","nome de armazem invalido"));


		DefaultTableModel modelMaquina = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "numero serie", "avariada"});
		/*nomeNumeroMaquina*/
		assertEquals(true, db.nomenumeroMaquina(armazem,modelMaquina));
		int i;
		for(i = 0; i < modelMaquina.getRowCount(); i++){
			if(modelMaquina.getValueAt(i, 0).equals("maquinaTESTE"))
				break;
		}
		assertEquals("maquinaTESTE", modelMaquina.getValueAt(i, 0));
		assertEquals("0", modelMaquina.getValueAt(i, 1));



		/*nomenumeroestadoMaquina*/
		modelMaquina.setRowCount(0);
		assertEquals(true, db.nomenumeroestadoMaquina(armazem,modelMaquina));
		for(i = 0; i < modelMaquina.getRowCount(); i++){
			if(modelMaquina.getValueAt(i, 0).equals("maquinaTESTE"))
				break;
		}
		assertEquals("maquinaTESTE", modelMaquina.getValueAt(i, 0));
		assertEquals("0", modelMaquina.getValueAt(i, 1));
		assertEquals(false, modelMaquina.getValueAt(i, 2));
		
		
		/*Update*/
		assertEquals(true, db.updateavariadoMaquina(armazem,"0","descriçao do problema"));
		
		/*nomenumeroAvariadasMaquina*/
		modelMaquina.setRowCount(0);
		assertEquals(true, db.nomenumeroAvariadasMaquina(armazem,modelMaquina));
		for(i = 0; i < modelMaquina.getRowCount(); i++){
			if(modelMaquina.getValueAt(i, 0).equals("maquinaTESTE"))
				break;
		}
		assertEquals("maquinaTESTE", modelMaquina.getValueAt(i, 0));
		assertEquals("0", modelMaquina.getValueAt(i, 1));
		assertEquals(true, modelMaquina.getValueAt(i, 2));
		
		
		/*update*/
		update(db, modelMaquina);
		
		/*corrige avaria*/
		assertEquals(true, db.corrigeAvariaMaquina("11111110"));

		
		/*Remove*/
		modelMaquina.setRowCount(0);
		assertEquals(true, db.removeMaquina("11111110"));
		assertEquals(true, db.nomenumeroestadoMaquina(armazem,modelMaquina));
		for(i = 0; i < modelMaquina.getRowCount(); i++){
			if(modelMaquina.getValueAt(i, 0).equals("novoNome"))
				break;
		}
		if(i>=modelMaquina.getRowCount())
			i=0;
		assertNotEquals("novoNome", modelMaquina.getValueAt(i, 0));
		assertNotEquals("11111110", modelMaquina.getValueAt(i, 1));
	}
	
	@Test
	public void testSubCategoria() {
		DataBase db = DataBase.getInstance();
		
		assertEquals(true, db.addCategoriaProduto("categoria", armazem));

		
		/*Adiciona*/
		assertEquals(true, db.addSubCategoria("sub categoria","categoria"));
		assertEquals(false, db.addSubCategoria("sub categoria","categoria"));
		assertEquals(false, db.addSubCategoria("sub categoria2","categoria12"));

		/*subcategoriaSubCategoria*/
		DefaultTableModel modelSubCategoria = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome" });
		assertEquals(true, db.subcategoriaSubCategoria(modelSubCategoria, "categoria"));
		assertEquals("sub categoria", modelSubCategoria.getValueAt(modelSubCategoria.getRowCount()-1, 0));
		
		ArrayList<String> result= db.getAllSubCategoria("categoria");
		assertEquals("sub categoria", result.get(result.size()-1));
		
		/*Remove*/
		modelSubCategoria.setRowCount(0);
		assertEquals(true, db.removeSubCategoria("sub categoria","categoria"));
		
		assertEquals(true, db.subcategoriaSubCategoria(modelSubCategoria, "categoria"));
		assertEquals(0, modelSubCategoria.getRowCount());
		
		
		assertEquals(true, db.removeCategoriaProduto("categoria"));

	}
	
	@Test
	public void testLote() {
		DataBase db = DataBase.getInstance();
		
		db.addCategoriaProduto("categoria teste", armazem);
		db.addSubCategoria("sub categoria teste","categoria teste");
		db.removeLote("123123");
		
		/*Adiciona*/
		assertEquals(true, db.addLote("123123;portugal"+";"+db.localDate()+";"+"sub categoria teste"+";"+"produto nome"));
		assertEquals(false, db.addLote("123123;portugal"+";"+ db.localDate()+";"+"sub categoria teste"+";"+"produto nome"));

		/*numeroNomeLote*/
		DefaultTableModel modelLote = new DefaultTableModel(new Object[][] {},
				new String[] { "numero", "nome" });
		assertEquals(true, db.numeroNomeLote(modelLote));
		assertEquals("123123", modelLote.getValueAt(modelLote.getRowCount()-1, 0));
		assertEquals("produto nome", modelLote.getValueAt(modelLote.getRowCount()-1, 1));


		/*Remove*/
		assertEquals(true, db.removeLote("123123"));
		assertEquals(true, db.removeSubCategoria("sub categoria", "categoria"));
		assertEquals(true, db.removeCategoriaProduto("categoria"));

	}

	@Test
	public void testProduto() {
		DataBase db = DataBase.getInstance();
		
		db.removeSubCategoria("sub categoria", "categoria");
		db.removeCategoriaProduto("categoria");
		 db.removeLote("num123");
		assertEquals(true, db.addCategoriaProduto("categoria", armazem));
		assertEquals(true, db.addSubCategoria("sub categoria","categoria"));
		assertEquals(true, db.addLote("num123"+";"+"portugal"+";"+db.localDate()+";"+"sub categoria"+";"+"produto nome"));
		
		/*Adiciona*/
		assertEquals(true, db.addProduto("sku123", "num123"));
		assertEquals(false, db.addProduto("sku123", "num123"));

		/*sem defeito*/
		DefaultTableModel modelLote = new DefaultTableModel(new Object[][] {},
				new String[] { "nome", "sku", "origem", "defeito" });
		modelLote.setRowCount(0);
		assertEquals(true, db.produtoNaoVendido(modelLote));
		assertEquals("sku123", modelLote.getValueAt(modelLote.getRowCount()-1, 0));
		assertEquals(false, modelLote.getValueAt(modelLote.getRowCount()-1, 3));
		/*com defeito*/
		assertEquals(true, db.marcarDefeitoProduto("sku123") );
		modelLote.setRowCount(0);
		assertEquals(true, db.produtoNaoVendido(modelLote));
		assertEquals("sku123", modelLote.getValueAt(modelLote.getRowCount()-1, 0));
		assertEquals(true, modelLote.getValueAt(modelLote.getRowCount()-1, 3));
		
		DefaultTableModel modelSkus = new DefaultTableModel(new Object[][] {},
				new String[] { "sku" });
		assertEquals(true, db.produtoNaoVendido(modelSkus));
		assertEquals("sku123", modelLote.getValueAt(modelLote.getRowCount()-1, 0));
		assertEquals(true, db.getAllSkus(modelSkus));


		
	
		/*vende produto*/
		assertEquals(true,  db.enviarProduto("sku123", db.localDate(), "destino"));
		modelLote.setRowCount(0);
		assertEquals(true, db.produtoVendido(modelLote));
		assertEquals("sku123", modelLote.getValueAt(modelLote.getRowCount()-1, 1));
		modelLote.setRowCount(0);
		assertEquals(true, db.produtoNaoVendido(modelLote));
		assertNotEquals("sku123", modelLote.getValueAt(modelLote.getRowCount()-1, 0));

		/*Remove*/
		assertEquals(true, db.removeLote("123123"));
		assertEquals(true, db.removeSubCategoria("sub categoria", "categoria"));
		assertEquals(true, db.removeCategoriaProduto("categoria"));

	}
	@Test
	public void testLogs() {
		DataBase db = DataBase.getInstance();
		assertEquals(true, db.addLog("admin"+";"+ true+";"+armazem+";"+"IP-teste", "testes automaticos", "foi feito testes automaticos" ));
		assertEquals(false, db.addLog("admin"+";"+ true+";"+"invalido"+";"+ "IP-teste", "testes automaticos", "foi feito testes automaticos"));

		DefaultTableModel modelLogs = new DefaultTableModel(new Object[][] {},
				new String[] { "data","userna","admin","acao","ip"});
		assertEquals(true, db.getLogs(modelLogs, "20"));

	}
	
}
