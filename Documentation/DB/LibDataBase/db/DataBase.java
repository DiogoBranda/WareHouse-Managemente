package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;

public class DataBase {
	/*TABELAS*/
	private Armazem armazem;
	private Fornecedor fornecedor;
	private Maquina maquina;
	private CategoriaProduto categoriaProduto;
	private SubCategoriaProduto subCategoriaProduto;
	private Lote lote;
	private Produto produto;
	private Funcionario funcionario;
	private Login login;
	/*DADOS BASE DE DADOS*/
	private String url;
	private String user;
	private String password;
	private Connection c;
	

	public DataBase(String url, String user, String password) {
		this.armazem = new Armazem();
		this.fornecedor = new Fornecedor();
		this.maquina = new Maquina();
		this.categoriaProduto = new CategoriaProduto();
		this.subCategoriaProduto = new SubCategoriaProduto();
		this.lote = new Lote();
		this.produto = new Produto();
		this.funcionario = new Funcionario();
		this.login = new Login();
		this.url = url;
		this.user = user;
		this.password = password;
		this.c = null;

	}
	
	public DataBase() {
		this.armazem = new Armazem();
		this.fornecedor = new Fornecedor();
		this.maquina = new Maquina();
		this.categoriaProduto = new CategoriaProduto();
		this.subCategoriaProduto = new SubCategoriaProduto();
		this.lote = new Lote();
		this.produto = new Produto();
		this.funcionario = new Funcionario();
		this.login = new Login();
		this.url = "jdbc:postgresql://db.fe.up.pt:5432/sinf19a38?currentSchema=Warehouse";
		this.user = "sinf19a38";
		this.password = "UbwJSLsu";
		this.c = null;
	}

	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
	        this.c = DriverManager.getConnection(url,  user, password);
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
		System.out.println("Connected successfully!");
	}
	
	public void disconnect() {
		try {
			this.c.close();
		} catch (Exception e) {
			e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
		System.out.println("Disconnected Successfully!");
	}

	public void createTables() {
		/*querys para criar todas as tabelas na base de dados*/
	}

	public String localDate() {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
		
	}
	/*GETTERS*/
	
	public Armazem getArmazem() {
		return armazem;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public SubCategoriaProduto getSubCategoriaProduto() {
		return subCategoriaProduto;
	}

	public Lote getLote() {
		return lote;
	}

	public Produto getProduto() {
		return produto;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public Login getLogin() {
		return login;
	}

	public Connection getC() {
		return c;
	}

	public void setC(Connection c) {
		this.c = c;
	}
	
	
	

}
