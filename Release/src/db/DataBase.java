/**
* Classe de Base de dados
* Gere as Funções de todos os dados guardados 
* na base de dados
*
* 
* @author  Auto-buys
* @version 1.0
* @since   2019-11-22 
*/
package db;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.ibatis.jdbc.ScriptRunner;
import javax.swing.table.DefaultTableModel;
/**
 * Classe de acesso a base de dados
 * 
 */

public class DataBase {
	private static DataBase instance=null;
	//TABELAS
	private Armazem armazem;
	private Fornecedor fornecedor;
	private Maquina maquina;
	private CategoriaProduto categoriaProduto;
	private SubCategoriaProduto subCategoriaProduto;
	private Lote lote;
	private Produto produto;
	private Funcionario funcionario;
	private Login login;
	private Logs logs;

	//DADOS BASE DE DADOS
	private String url;
	private String user;
	private String password;
	private Connection c;
	
	
	
	/**
	 * Construtor da base de dados com credenciais padrao
	 * 
	 */
	private DataBase() {
		this.armazem = new Armazem();
		this.fornecedor = new Fornecedor();
		this.maquina = new Maquina();
		this.categoriaProduto = new CategoriaProduto();
		this.subCategoriaProduto = new SubCategoriaProduto();
		this.lote = new Lote();
		this.produto = new Produto();
		this.funcionario = new Funcionario();
		this.login = new Login();
		this.logs = new Logs();
		this.url = "jdbc:postgresql://db.fe.up.pt:5432/sinf19a38?currentSchema=warehouse";
		this.user = "sinf19a38";
		this.password = "UbwJSLsu";
		this.c = null;
		DriverManager.setLoginTimeout(3);

	}

	
	
	public static DataBase getInstance() {
		if(instance == null)
			instance = new DataBase();
		return instance;
	}

	/**
	 * Adiciona password à base de dados
	 * @param password - password a colocar na base de dados 	
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Adiciona url à base de dados
	 * @param url - url a colocar na base de dados 	
	 */
	public void setUrl(String url) {
		this.url =  "jdbc:postgresql://"+url+"?currentSchema=warehouse";
	}
	/**
	 * Adiciona user à base de dados
	 * @param user - user a colocar na base de dados 	
	 */

	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Retorna url da base de dados
	 * @return String - url da base de dados 	
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Retorna user da base de dados
	 * @return String - user da base de dados 	
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Retorna password da base de dados
	 * @return String - password da base de dados 	
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Função para conectar à base de dados
	 * @return boolean true se conexão com exito / false caso contrario 	
	 */
	public boolean connect() {
		try {
			Class.forName("org.postgresql.Driver");
	        this.c = DriverManager.getConnection(url,  user, password);
		} catch (Exception e) {
	        return false;
		}
		return true;
	}
	
	/**
	 * Função para testar conexão à base de dados
	 * @return boolean true se conexão com exito / false caso contrario 	
	 */
	public boolean checkConnection() {
		if(connect()) {
			return disconnect();
		}else {
			return false;
		}
	}
	
	/**
	 * Função para disconectar da base de dados
	 * @return boolean true se conexão terminada com exito / false caso contrario 	
	 */
	public boolean disconnect() {
		try {
			this.c.close();
		} catch (Exception e) {
	        return false;
		}
		return true;
	}
	
	/**
	 * Executa a query pretendida
	 * @param sql - string a executar via sql
	 * @return boolean - true se executar corretamente / false caso contrario
	 * */
	protected boolean executeQuery(String sql) {
		connect();
		try {
			Statement stmt = getC().createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			disconnect();
			return false;
		}
		disconnect();
		return true;
	}
	
	/**
	 * Executa a query pretendida e retorna o ResultSet
	 * @param sql - string a executar via sql
	 * @return ResultSet - retorna corretamente o ResultSet caso nao haja erros / null caso contrario
	 * */
	public ResultSet executeQueryResult(String sql) {
		connect();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = getC().createStatement();
			rs = stmt.executeQuery(sql);
			
		} catch (Exception e) {
			disconnect();
		}
		disconnect();
		return rs;
	}
	
	/**
	 * Funçao usada para saber Data e hora atual 
	 * 
	 * @return DataHora String com data e hora
	 */
	public String localDate() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
		
	}
	
	/**
	 * Cria as tabelas pre definidas a partir de um ficheiro .sql
	 * @param file - ficheiro que pretende executar 
	 * @return boolean - true caso a ação seja completa com sucesso / false caso contrario
	 */
	public boolean createTable(File file) {
		if(file == null)
			return false;
		connect();
		 ScriptRunner sr = new ScriptRunner(getC());
		 sr.setLogWriter(null);
	      //Creating a reader object
	      Reader reader;
		try {
			reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			 //Running the script
			
		      sr.runScript(reader);
		      disconnect();
		      return true;
		} catch (FileNotFoundException e) {
		}
	      disconnect();

		return false;
	     
	}
	
	/**
	 * Retorna a conexão com a base de dados
	 * @return Conexão Conexão com base de dados
	 */
	public Connection getC() {
		return c;
	}
	
	/**
	 * Define a conexao com a base de dados
	 * @param conexao Conexão com a base de dados
	 */
	public void setC(Connection conexao) {
		this.c = conexao;
	}

	
	/**
	 * Adiciona um armazem a base de dados
	 * 
	 * @param nome  Nome do armazem
	 * @param localizacao  Local do armazem
	 * @return boolean  True se inseriu corretamente / False no caso contrario
	 */
	
	public boolean addArmazem(String nome, String localizacao) {
		return armazem.insertAll(this,nome,localizacao);
	}
	
	/**
	 * Da Update no armazem da Base de Dados
	 * @param nomeAntigo  Nome antigo do armazem
	 * @param nome  Novo nome do armazem
	 * @param localizacao  Local do armazem
	 * @return boolean True se deu update corretamente / False no caso contrario
	 */
	public boolean updateArmazem(String nomeAntigo, String nome, String localizacao) {
		return armazem.updateArmazem(this, nomeAntigo, nome, localizacao);
	}
	
	/**
	 * Remove o Armazem com o nome indicado da base de dados
	 * @param nome Nome do armazem
	 * @return  boolean True se removeu corretamente / False no caso contrario
	 */
	public boolean removeArmazem(String nome) {
		return armazem.remove(this, nome);
	}
	
	/**
	 * Insere na tabela o nome e local dos armazens
	 * @param modelArmazem Tabela para os dados dos armazens
	 * @return boolean True se inseriu corretamente na tabela / False no caso contrario
	 */
	public boolean nomelocalizacaoArmazem(DefaultTableModel modelArmazem) {
		return armazem.selectAll(this, modelArmazem);
	}
	
	/**
	 * Retorna a Localização do respetivo Armazem
	 * @param nome nome do armazem a pesquisar
	 * @return String localizacao do armazem se encontrou com aquele nome/ null no caso contrario
	 */
	public String localizacaoArmazem(String nome) {
		return armazem.selectLocalizacao(this, nome);
	}
	
	/**
	 * Insere no TableModel o Nome da Categoria do produto
	 * @param modelCategoria Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean categoriaProduto(DefaultTableModel modelCategoria) {
		return categoriaProduto.selectAll(this, modelCategoria);
	}
	
	/**
	 * Insere Categoria no Armazem 
	 * @param nome Nome da categoria 
	 * @param armazem Armazem onde se pretende inserir a categoria
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addCategoriaProduto(String nome, String armazem) {
		return categoriaProduto.insertAll(this, nome, armazem);
	}
	
	/**
	 * Remove a Categoria 
	 * @param nome Nome da categoria
	 * @return boolean True se removeu corretamente / False no caso contrario
	 */
	public boolean removeCategoriaProduto(String nome) {
		return categoriaProduto.remove(this, nome);
	}
	
	/**
	 * Insere no TableModel o Nome dos Fornecedores 
	 * @param modelFornecedor Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nomeFornecedor(DefaultTableModel modelFornecedor){
		return fornecedor.selectAll(this, modelFornecedor);
	}
	
	/**
	 * Insere o Fornecedor no Armazem 
	 * @param nome Nome do Fornecedor 
	 * @param nomeArmazem nome do Armazem onde se pretende inserir o Fornecedor
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addFornecedor(String nome, String nomeArmazem){
		return fornecedor.insertAll(this, nome, nomeArmazem);
	}
	
	/**
	 * Update dos dados do fornecedor
	 * @param nomeAntigo Nome Antigo do fornecedor
	 * @param nome Nome novo do fornecedor
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean updateFornecedor(String nomeAntigo,String nome){
		return fornecedor.update(this, nomeAntigo, nome);
	}
	
	/**
	 * Remove o fornecedor da base de dados
	 * @param nome Nome do fornecedor 
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean removeFornecedor(String nome) {
		return fornecedor.remove(this, nome);
	}
	
	/**
	 * Insere no TableModel o nif, nome, idade e função do funcionario
	 * @param armazem Armazem onde o funcionario trabalha
	 * @param modelFuncionario Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nifIdadeNomeFuncaoFuncionario(String armazem, DefaultTableModel modelFuncionario){
		return funcionario.selectNifIdadeNomeFuncao(this, armazem, modelFuncionario);
	}
	
	/**
	 * Insere no TableModel o nome, e nif do funcionario
	 * @param modelFuncionario Tabela onde se insere os dados
	 * @param armazem selectIdadeNomeFuncaoarmazem onde o funcionario trabalha
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nomeNifFuncionario(DefaultTableModel modelFuncionario, String armazem){
		return funcionario.selectNomeNif(this, modelFuncionario, armazem);
	}
	
	/**
	 * Devolve um vetor com todos os dados de um certo funcionario
	 * @param nif Nif do funcionario
	 * @return Vetor String com Nome Idade Salario Função e armazem do funcionario
	 */
	public String[] getFuncinarioByNif(String nif){
		return funcionario.selectAllNif(this, nif);
	}
	
	/**
	 * Insere um novo Funcionario
	 * @param dadosFunc - contem o nif, nome, idade, funçao, salario e armazem onde trabalha
	 * @return Boolean True se inseriu corretamente/False caso contrario
	 */
	public boolean addFuncionario(String dadosFunc){
		return funcionario.insertAll(this, dadosFunc);
	}
	
	/**
	 * Update no utilizador
	 * @param nifAntigo Nif antigo 
	 * @param novosDados dados do novo funcionario separado por ";", contem o nif, nome, idaide, funcao e salario do funcionario
	 * @return Boolean True se inseriu corretamente/False caso contrario
	 */
	public boolean updateFuncionario(String nifAntigo,String novosDados){
		return funcionario.updateFuncionario(this, nifAntigo, novosDados);
	}
	
	/**
	 * Remove o funcionario da base de dados
	 * @param nif Nif do funcionario
	 * @return Boolean True se removeu corretamente/False caso contrario
	 */
	public boolean removeFuncionarioByNif(String nif){
		return funcionario.remove(this, nif);
	}
	
	/**
	 * Insere no TableModel o nif, usarname e se é admin ou nao
	 * @param modelUser Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nifusernameadminLogin(DefaultTableModel modelUser) {
		return login.selectAll(this, modelUser);
	}
	
	/**
	 * Devolve todos os nif's dos funcionarios
	 * @return ArrayListString Todos os nifs dos funcionarios
	 */
	public ArrayList<String> getAllNifLogin(){
		return login.selectNif(this);
	}
	
	/**
	 * Devolve o nome do armazem a que o utilizador pertence
	 * @param username Nome do Utilizador
	 * @return String Nome do armazem 
	 */
	public String getUserarmazemLogin(String username) {
		return login.selectNameArmazem(this, username);
	}

	/**
	 * Devolve o username do email enviado
	 * @param email - Email do utilizador
	 * @return String - Username correspondente 
	 */
	public String getUsernameByEmail (String email){
		return login.selectUsernameByEmail(this, email);
	}
	
	/**
	 * Insere User na base de dados
	 * @param dadosUser -  contem o nif, nome e email do utilizador
	 * @param password - Password do Utilizador
	 * @param admin - Se é admin True caso contrario Falso
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addUserLogin(String dadosUser, String password, boolean admin) {
		return login.insertAll(this, dadosUser, password, admin);
	}
	
	/**
	 * Upadate nos dados do User
	 * @param dadosUser - contem o nome de utilizador antigo e o nome de utilizador novo
	 * @param pass PassWord do utilizador
	 * @param admin True se é admin | False caso contrario
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean updateUserLogin(String dadosUser, String pass, boolean admin) {
		return login.update(this, dadosUser, pass, admin);
	}
	
	/**
	 * Upadate nos dados do User
	 * @param user Novo Nome do user 
	 * @param pass PassWord do utilizador
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean updateUserPassword(String user, String pass) {
		return login.updatePassword(this, user, pass);
	}
	
	/**
	 * Remove o user com um certo username
	 * @param username Nome do utilizador a remover
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean removeUserLogin(String username) {
		return login.remove(this, username);
	}
	
	/**
	 * Valida os dados inseridos
	 * @param username Username do Utilizador
	 * @param password Password do Utilizador
	 * @return 0 admin | 1 user | caso contrario nao existe
	 */
	public int checkUserLogin(String username, String password) {
		return login.checkLogin(username, password, this);
	}
	
	/**
	 * Insere no TableModel a data de envio, sku, nome do produto e destinodos produtos vendidos
	 * @param modelProduto Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean produtoVendido(DefaultTableModel modelProduto) {
		return produto.selectProdutoVendido(this, modelProduto);
	}
	
	/**
	 * Insere no TableModel o nome, Sku, origem e se está com defeito ou nao dos produtos nao vendidos
	 * @param modelProduto Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean produtoNaoVendido(DefaultTableModel modelProduto) {
		return produto.selectProdutoNaoVendido(this, modelProduto);
	}
	
	/**
	 * Insere informação do Produto
	 * @param sku Numero do produto
	 * @param numLote Numero de lote a que pertence o produto
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addProduto(String sku, String numLote) {
		return produto.insertAll(this, sku, numLote);
	}
	
	/**
	 * Coloca o produto com a SKU especificada como defeituoso
	 * @param sku - Numero do produto
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean marcarDefeitoProduto(String sku) {
		return produto.marcarDefeitoProduto(this, sku);
	}
	
	/**
	 * Insere na tabela todas as skus dos produtos nao vendidos
	 * @param modelProduto - Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean getAllSkus(DefaultTableModel modelProduto) {
		return produto.selectAllSKUsNaoVendidos(this, modelProduto);
	}
	
	/**
	 * Insere no TableModel a subcategoria
	 * @param modelCategoria Tabela onde se insere os dados
	 * @param categoria Categoria que se prentede analizar
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean subcategoriaSubCategoria(DefaultTableModel modelCategoria, String categoria) {
		return subCategoriaProduto.selectAll(this, modelCategoria, categoria);
	}
	
	/**
	 * Adiciona uma nova subcategoria 
	 * @param subCategoria Nome da Subcategoria
	 * @param categoria Categoria a qual pertence
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean addSubCategoria(String subCategoria, String categoria) {
		return subCategoriaProduto.insertAll(this, subCategoria, categoria);
	}
	
	/**
	 * Elimina uma subcategoria de uma determinada categoria 
	 * @param subCategoria Nome da Subcategoria
	 * @param categoria Categoria a qual pertence
	 * @return Boolean True se eliminou corretamente/ False no caso contrario
	 */
	public boolean removeSubCategoria(String subCategoria, String categoria) {
		return subCategoriaProduto.remove(this, subCategoria, categoria);
	}

	/**
	 * Insere no TableModel o nome e numero de seri da maquina
	 * @param armazem Armazem a qual a maquina pertence 
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nomenumeroMaquina(String armazem, DefaultTableModel modelMaquina) {
		return maquina.selectNomeNumero(this, armazem, modelMaquina);
	}
	
	/**
	 * Insere no TableModel o nome e numero de serie das maquinas avariadas 
	 * @param armazem Armazem em que a maquina esta
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nomenumeroAvariadasMaquina(String armazem, DefaultTableModel modelMaquina) {
		return maquina.selectAvaria(this, armazem, modelMaquina);
	}
	
	/**
	 * Insere no TableModel o nome id e estado da maquina
	 * @param armazem Nome do armazem em que a maquina esta
	 * @param modelMaquina Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean nomenumeroestadoMaquina(String armazem, DefaultTableModel modelMaquina) {
		return maquina.selectNomeNumeroEstado(this, armazem, modelMaquina);
	}
	
	/**
	 * Declara que a maquina esta avariada e adiciona uma descrição do problema
	 * @param armazem Nome do Armazem a qual a maquina pertence
	 * @param Id Id da maquina
	 * @param descricao descrição do problema
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean updateavariadoMaquina(String armazem ,String Id,String descricao) {
		return maquina.updateAvariadoMaquina(this, armazem, Id, descricao);
	}
	
	/**
	 * Update da Maquina
	 * @param numeroSerieAntigo Numero Serie antigo da maquina
	 * @param numeroSerie Novo numero de Serie
	 * @param nome Novo nome da maquina
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean updateMaquina(String numeroSerieAntigo, String numeroSerie, String nome) {
		return maquina.update(this, numeroSerieAntigo, numeroSerie, nome);
	}
	
	/**
	 * Adiciona uma nova maquina
	 * @param numero_serie Numero de serie da maquina
	 * @param nome Nome da Maquina
	 * @param armazem Nome do armazem
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addMaquina(String numero_serie, String nome, String armazem) {
		return maquina.insertAll(this, numero_serie, nome, armazem);
	}
	

	/**
	 * Remove Maquina do Armazem
	 * @param numeroSerie Numero de Serie da maquina a remover
	 * @return Boolean True se corrigiu corretamente/ False no caso contrario
	 */
	public boolean removeMaquina(String numeroSerie) {
		return maquina.remove(this, numeroSerie);
	}	
	/**
	 * Corrige a avaria da maquina
	 * @param numeroSerie Numero de Serie da maquina a corrigir
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean corrigeAvariaMaquina(String numeroSerie) {
		return maquina.corrigeAvariaMaquina(this, numeroSerie);
	}	
	
	
	/**
	 * Insere informação do Lote
	 * @param dadosLote dados do lote numa string separado por ";", numero de lote, origem, data chegada, sub categoria e nome do lote
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addLote(String dadosLote) {
		return lote.insertAll(this, dadosLote);
	}	
	/**
	 * Insere na tabela o numero de lote e o nome do produto
	 * @param modelLote Tabela onde se insere os dados
	 * @return Boolean True se inseriu corretamente na tabela/ False no caso contrario
	 */
	public boolean numeroNomeLote(DefaultTableModel modelLote) {
		return lote.selectAll(this, modelLote);
	}
	
	/**
	 * Remove o Lote
	 * @param numeroLote Numero de lote da subCategoria pertencente
	 * @return Boolean True se removeu corretamente/ False no caso contrario
	 */
	public boolean removeLote(String numeroLote) {
		return lote.remove(this, numeroLote);
	}
	
	/**
	 * Insere log do que o user fez
	 * @param dadosLog - contem o username, se é admin e ip de quem fez a açao e o armazem correspondente
	 * @param acao - ação que o user fez
	 * @param acaoCompleta - ação que o user fez de modo detalhada
	 * @return Boolean True se inseriu corretamente/ False no caso contrario
	 */
	public boolean addLog(String dadosLog, String acao, String acaoCompleta) {
		return logs.insertAll(this, dadosLog, acao, acaoCompleta);
	}
	
	/**
	 * Retorna um ArrayList com todas as subcategorias
	 * @param cat Categoria do produto
	 * @return Array de subcategorias
	 */
	public ArrayList<String> getAllSubCategoria(String cat){
		return  subCategoriaProduto.selectSubCat(this,cat);
	}
	
	/**
	 * 
	 * Eniva o produto
	 * @param SKU - Codigo do Produto
	 * @param Data - Data de venda do produto
	 * @param destino - destino da entrega
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean enviarProduto(String SKU,String Data,String destino) {
		return produto.produtoEnviado(this, SKU, Data,destino);
	}
	
	/**
	 * Retorna um ArrayList com todas as categorias
	 * @return Array de categorias
	 */
	public ArrayList<String> getAllCategoria(){
		return categoriaProduto.selectCat(this);
	}
	/**
	 * Retorna um ArrayList com todos os fornecedores
	 * @return Array de fornecedores
	 */
	public ArrayList<String> getAllFornecedores(){
		return fornecedor.selectFunc(this);
	}
	


	/**
	 * 
	 * @param modelLogs - tabela onde se insere os dados
	 * @param numeroLinhas - numero de linhas que deseja visualizar
	 * @return Boolean True se atualizou corretamente/ False no caso contrario
	 */
	public boolean getLogs(DefaultTableModel modelLogs, String numeroLinhas) {
		return logs.selectAll(this,  modelLogs, numeroLinhas);
	}
}
