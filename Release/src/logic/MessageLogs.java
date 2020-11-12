package logic;

import db.DataBase;

/**
 * Historico das açoes dos users
 */
public class MessageLogs {
	
	private static MessageLogs instance=null;
	private DataBase db;
	private Ip ip;
	
	/**
	 * Construtor
	 */
	private MessageLogs() {
		this.db = DataBase.getInstance();
		this.ip = new Ip();
	}
	
	/**
	 * Inicializa a classe
	 * @return MessageLogs - cria o objeto e retorna ou retorna a classe já criada
	 * */
	public static MessageLogs getInstance() {
		if(instance == null)
			instance = new MessageLogs();
		return instance;
	}
	
	/**
	 * Envia o log para base de dados, alguem saiu no sistema
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean saiuSistema(String dados) {
		return db.addLog(dados+";"+ip.getIp(), "saiu do sistema", "saiu do sistema");
	}
	
	/**
	 * Envia o log para base de dados, alguem entrou no sistema
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean entrouSistema(String dados) {
		return db.addLog(dados+";"+ip.getIp(), "entrou no sistema", "entrou no sistema");
	}
	
	
	/**
	 * Envia o log para base de dados, adicionou user novo
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nif - nif do user que foi adicionado
	 * @param nome - nome do user que foi adicionado
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean adicionaUser(String dados,String nif, String nome) {
		return db.addLog(dados+";"+ip.getIp(),"adicionou user \""+nome+"\"" ,"adicionou o user \""+ nome+"\" com nif \""+ nif+"\"");
	}
	
	/**
	 * Envia o log para base de dados, removeu user
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do user que foi removido
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean removeUser(String dados, String nome) {
		return db.addLog(dados+";"+ip.getIp(), "removeu user \""+nome+"\"","removeu o user \""+ nome+"\"");
	}
	
	/**
	 * Envia o log para base de dados, editou user
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param user - novo nome de user após a ediçao
	 * @param oldUser - nome de user antigo antes da ediçao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean editaUser(String dados,String user, String oldUser) {
		return db.addLog(dados+";"+ip.getIp(),"editou user \""+oldUser+"\"", "editou o user \""+ oldUser+"\" para o user \""+ user+"\"");
	}
	
	/**
	 * Envia o log para base de dados, adicionou maquina nova
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome da maquina que foi adicionado
	 * @param numero - numero da maquina que foi adicionado
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean adicionaMaquina(String dados, String nome, String numero) {
		return db.addLog(dados+";"+ip.getIp(), "adicionou maquina \""+ nome+ "\"","adicionou a maquina \""+ nome+ "\" com o numero \""+ numero+"\"");
	}
	
	/**
	 * Envia o log para base de dados, removeu maquina nova
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome da maquina que foi removido
	 * @param numero - numero da maquina que foi removido
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean removeMaquina(String dados, String nome, String numero) {
		return db.addLog(dados+";"+ip.getIp(), "removeu maquina \""+nome+"\"", "removeu a maquina \""+ nome+"\" com o numero \""+ numero+"\"");
	}
	
	/**
	 * Envia o log para base de dados, editou maquina
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome da maquina
	 * @param numero - numero da maquina
	 * @param novoNome - novo nome da maquina
	 * @param novoNumero - novo numero da maquina
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean editaMaquina(String dados,String nome, String numero, String novoNome, String novoNumero) {
		return db.addLog(dados+";"+ip.getIp(), "editou maquina \""+nome+"\"","editou a maquina \""+ nome+"\" com o numero \""+ numero+"\" para o novo nome \""+ novoNome+"\" com o novo numero \""+novoNumero+"\"");
	}
	
	/**
	 * Envia o log para base de dados, adicionou funcionario novo
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do funcionario que foi adicionado
	 * @param nif - nif do funcionario que foi adicionado
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean adicionaFuncionario(String dados, String nome, String nif) {
		return db.addLog(dados+";"+ip.getIp(),"adicionou funcionario \""+nome+"\"", 
				"adicionou o funcionario \""+ nome+ "\" com o nif \""+ nif+"\"");
	}
	
	/**
	 * Envia o log para base de dados, removeu funcionario
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do funcionario que foi removido
	 * @param nif - nif do funcionario que foi removido
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean removeFuncionario(String dados, String nome, String nif) {
		return db.addLog(dados+";"+ip.getIp(),"removeu funcionario \""+nome+"\"", "removeu o funcionario \""+ nome+"\" com o nif \""+ nif+"\"");
	}
	
	/**
	 * Envia o log para base de dados, editou funcionario
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome de user antigo antes da ediçao
	 * @param numero - numero de user antigo antes da ediçao
	 * @param novoNome - novo nome de user após a ediçao
	 * @param novoNumero - novo numero de user antigo antes da ediçao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean editaFuncionario(String dados,String nome, String numero, String novoNome, String novoNumero) {
		return db.addLog(dados+";"+ip.getIp(),"editou funcionario \""+nome+"\"", "editou o funcionario \""+ nome+"\" com o numero \""+ numero+"\" para o novo nome \""+ novoNome+"\" com o novo numero \""+novoNumero+"\"");
	}
	
	/**
	 * Envia o log para base de dados, adicionou fornecedor novo
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do fornecedor que foi adicionado
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean adicionaFornecedor(String dados, String nome) {
		return db.addLog(dados+";"+ip.getIp(),"adicionou fornecedor \""+nome+"\"","adicionou o fornecedor \""+ nome+ "\"");
	}
	
	/**
	 * Envia o log para base de dados, removeu fornecedor 
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do fornecedor que foi removido
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean removeFornecedor(String dados, String nome ) {
		return db.addLog(dados+";"+ip.getIp(),"removeu fornecedor \""+nome+"\"", "removeu o fornecedor \""+ nome+"\"");
	}
	
	/**
	 * Envia o log para base de dados, editou fornecedor
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nome - nome do fornecedor antes da ediçao
	 * @param novoNome - novo nome do fornecedor apos a ediçao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean editaFornecedor(String dados,String nome, String novoNome) {
		return db.addLog(dados+";"+ip.getIp(),"editou fornecedor \""+nome+"\"", "editou a fornecedor \""+ nome+"\" para o novo nome \""+ novoNome+"\"");
	}
	
	/**
	 * Envia o log para base de dados, adicionou nova categoria ou sub categoria
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param categoria - categoria a que pertence a sub categoria / ou categoria que pretende adicionar
	 * @param subCategoria - nome da sub categoria que foi adicionada / null caso esteja a adicionar uma categoria
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean adicionaCategoria(String dados,String categoria, String subCategoria) {
		if(subCategoria != null)
			return db.addLog(dados+";"+ip.getIp(),"adicionou sub categoria \""+subCategoria+"\"","adicionou a sub categoria \""+ subCategoria+ "\" peretencente à categoria \""+categoria+"\"");
		else
			return db.addLog(dados+";"+ip.getIp(),"adicionou categoria \""+categoria+"\"","adicionou a categoria \""+ categoria+ "\"");
	}
	
	/**
	 * Envia o log para base de dados, removeu categoria ou sub categoria
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param categoria - categoria a que pertence a sub categoria / ou categoria que pretende remover
	 * @param subCategoria - nome da sub categoria que foi removida / null caso esteja a remover uma categoria
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean removeCategoria(String dados,String categoria, String subCategoria) {
		if(subCategoria != null)
			return db.addLog(dados+";"+ip.getIp(),"removeu sub categoria \""+subCategoria+"\"","removeu a sub categoria \""+ subCategoria+ "\" peretencente à categoria \""+categoria+"\"");
		else
			return db.addLog(dados+";"+ip.getIp(),"removeu categoria \""+categoria+"\"","removeu a categoria \""+ categoria+ "\"");
	}
	
	/**
	 * Envia o log para base de dados, editou base de dados
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param dadosAntigos - user,url e password antes da ediçao
	 * @param dadosNovos - user,url e password  antes da ediçao
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean editaBaseDados(String dados,String dadosAntigos,String dadosNovos) {
		String[] aux = dadosAntigos.split(";");
		String userAntigo = aux[0];
		String urlAntigo = aux[0];
		String passAntigo = aux[0];

		String[] aux2 = dadosNovos.split(";");
		String user = aux2[0];
		String url = aux2[0];
		String password = aux2[0];

		return db.addLog(dados+";"+ip.getIp(),"editou base de dados", "editou o user \""+ userAntigo+"\", url \""
		+urlAntigo+"\" password \""+passAntigo+"para  o user \""+ user+"\", url \""+url+"\" password \""+password+"\"");
	}
	
	
	/**
	 * Envia o log para base de dados, reportou avaria na maquina
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nomeMaquina - nome da maquina
	 * @param id - id da maquina
	 * @param descricao - descricao da avaria
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean reportaAvaria(String dados, String nomeMaquina,String id,String descricao) {
		return db.addLog(dados+";"+ip.getIp(),"reportou avaria da maquina\""+nomeMaquina+"\"","reportou avaria da maquina \""
	+ nomeMaquina+ "\" com o id \""+id+"\" e com a descricao: \""+descricao+"\"");
	}
	
	/**
	 * Envia o log para base de dados, corrige avaria na maquina
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param nomeMaquina - nome da maquina
	 * @param id - id da maquina
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean corrigeAvaria(String dados, String nomeMaquina,String id) {
		return db.addLog(dados+";"+ip.getIp(),"corrigiu avaria da maquina\""+nomeMaquina+"\"","corrigiu avaria da maquina \""
	+ nomeMaquina+ "\" com o id \""+id+"\"");
	}
	
	/**
	 * Envia o log para base de dados, reportou defeito no produto
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param sku - sku do produto com defeito
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean reportaDefeito(String dados, String sku) {
		return db.addLog(dados+";"+ip.getIp(),"reportou defeito no produto \""+sku+"\"","reportou defeito no produto \""+sku+"\"");
	}
	
	/**
	 * Envia o log para base de dados, envia produto
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param sku - sku do produto com defeito
	 * @param data - Data do produto
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean enviaProduto(String dados, String sku, String data) {
		return db.addLog(dados+";"+ip.getIp(),"enviou produto \""+sku+"\"","enviou o produto com a sku \""+sku+"\" e com uma data de \""+data+"\"");
	}
	
	/**
	 * Envia o log para base de dados, recebeu lote
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param dadosProduto - dados da receçao do produto, nome do produto, quantidade, origem, data e numero de lote 
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean receberProduto(String dados, String dadosProduto) {
		String[] aux = dadosProduto.split(";");
		if(aux.length != 5)
			return false;
		
		return db.addLog(dados+";"+ip.getIp(),"recebeu lote \""+aux[4]+"\"","recebeu lote \""+aux[4]+"\" de origem \""+aux[2]+"\" na data \""+aux[3]+"\" com \""+aux[1]
				+"\" produtos \""+aux[0]+"\"");
	}

	/**
	 * Envia o log para base de dados, mudou armazem
	 * @param dados - contem o username, se é admin,o armazem correspondente e ip de quem fez a açao
	 * @param dadosArmazem - dados do armazem, nome armazem antigo | localizacao antigo | nome armazem novo | localizacao novo 
	 * @return boolean  - true se enviou corretamente / false caso contrário
	 * */
	public boolean mudarArmazem(String dados, String dadosArmazem) {
		String[] aux = dadosArmazem.split(";");
		if(aux.length != 4)
			return false;
		
		return db.addLog(dados+";"+ip.getIp(),"mudou armazem para \""+aux[2]+"\"","mudou o armazem com o nome \""+aux[0]+"\" com localizacao \""
		+aux[1]+"\" para o nome \""+aux[2]+"\" para a localizacao \""+aux[3]+"\"");
	}
}
