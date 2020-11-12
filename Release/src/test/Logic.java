package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import logic.Check;
import logic.Ip;
import logic.Mail;
import logic.MessageLogs;

class Logic {
	private final String armazem= "warehouse1";

	@Test
	public void testBlankText() {
		Check chk = new Check();
		assertEquals(true, chk.blankText(null));
		assertEquals(true, chk.blankText(""));
		assertEquals(true, chk.blankText("       "));
		assertEquals(true, chk.blankText("    test   "));
		assertEquals(false, chk.blankText("test"));
		assertEquals(true, chk.blankText("t;e"));
	}
	@Test
	public void testSamePassword() {
		Check chk = new Check();
		assertEquals(false, chk.samePassword("", null));
		assertEquals(true, chk.samePassword("", ""));
		assertEquals(false, chk.samePassword("test", "gtest"));
		assertEquals(true, chk.samePassword("test", "test"));
		assertEquals(true, chk.samePassword("test", "test"));

	}
	
	@Test
	public void testValidPassword() {
		Check chk = new Check();
		assertEquals(false, chk.validPassword(null));
		assertEquals(false, chk.validPassword(""));
		assertEquals(false, chk.validPassword("test"));
		assertEquals(false, chk.validPassword("                 "));
		assertEquals(false, chk.validPassword(" passcorreta123"));
		assertEquals(false, chk.validPassword("passcorreta123 "));
		assertEquals(true, chk.validPassword("passcorreta123"));

	}

	@Test
	public void testMultipleSelection() {
		Check chk = new Check();
		int[] x1= {1,2};
		assertEquals(true, chk.multipleSelection(x1));
		int[] x2= {};
		assertEquals(true, chk.multipleSelection(x2));
		int[] x3= {1};
		assertEquals(false, chk.multipleSelection(x3));
		int[] x4= {1,2,3};
		assertEquals(true, chk.multipleSelection(x4));
		
		assertEquals(false, chk.multipleSelection(null));

	}

	@Test
	public void testOnlyNumbers() {
		Check chk = new Check();
		assertEquals(false, chk.onlyNumbers(null));
		assertEquals(false, chk.onlyNumbers(""));
		assertEquals(false, chk.onlyNumbers("test"));
		assertEquals(false, chk.onlyNumbers("                 "));
		assertEquals(false, chk.onlyNumbers(" passcorreta123"));
		assertEquals(false, chk.onlyNumbers("passcorreta123 "));
		assertEquals(false, chk.onlyNumbers(" 12312"));
		assertEquals(false, chk.onlyNumbers("12312 "));
		assertEquals(true, chk.onlyNumbers("12312"));


	}
	
	@Test
	public void testValidSelectedItem() {
		Check chk = new Check();
		assertEquals(false, chk.validSelectedItem(null));
		Object x = new Object();
		assertEquals(true, chk.validSelectedItem(x));


	}
	
	@Test
	public void testTextValidWithoutNumbers() {
		Check chk = new Check();

		assertEquals(false, chk.textValidWithoutNumbers(null));
		assertEquals(false, chk.textValidWithoutNumbers("asd123"));
		assertEquals(false, chk.textValidWithoutNumbers("123asd"));
		assertEquals(false, chk.textValidWithoutNumbers("123asd123"));
		assertEquals(false, chk.textValidWithoutNumbers(" asd"));
		assertEquals(false, chk.textValidWithoutNumbers("asd "));
		assertEquals(false, chk.textValidWithoutNumbers("       "));
		assertEquals(false, chk.textValidWithoutNumbers(""));
		assertEquals(true, chk.textValidWithoutNumbers("asd"));


	}
	
	
	@Test
	public void testIsNifValid() {
		Check chk = new Check();

		assertEquals(false, chk.isNifValid(null));
		assertEquals(false, chk.isNifValid("asd123"));
		assertEquals(false, chk.isNifValid("123asd"));
		assertEquals(false, chk.isNifValid("123asd123"));
		assertEquals(false, chk.isNifValid("asd"));
		assertEquals(false, chk.isNifValid(" asd"));
		assertEquals(false, chk.isNifValid("asd "));
		assertEquals(false, chk.isNifValid("       "));
		assertEquals(false, chk.isNifValid("432135877"));
		assertEquals(false, chk.isNifValid("232135877"));
		
		assertEquals(true, chk.isNifValid("241135877"));
	}
	
	@Test
	public void testselectedIndex() {
		Check chk = new Check();

		assertEquals(0, chk.selectedIndex(null, null));
		assertEquals(0, chk.selectedIndex(new int[] {1}, null));
		assertEquals(0, chk.selectedIndex(null, new int[] {1}));

		assertEquals(1, chk.selectedIndex(new int[] {1}, new int[] {}));
		
		assertEquals(2, chk.selectedIndex(new int[] {1}, new int[] {1}));
		assertEquals(2, chk.selectedIndex(new int[] {1}, new int[] {1,2,3}));

		assertEquals(-1, chk.selectedIndex(new int[] {1,2,3}, new int[] {1}));
		assertEquals(-1, chk.selectedIndex(new int[] {1,2,3}, new int[] {1,3,2}));

		assertEquals(-2, chk.selectedIndex(new int[] {}, new int[] {1,2,3}));

		assertEquals(3, chk.selectedIndex(new int[] {}, new int[] {}));

		
	}
	
	@Test
	public void testValidEmail() {
		Check chk = new Check();

		assertEquals(true, chk.validEmail("fabio@hotmail.com"));
		assertEquals(true, chk.validEmail("fabio@gmail.com"));
		assertEquals(true, chk.validEmail("up201504257@fe.up.pt"));

		assertEquals(false, chk.validEmail("fabiouds@.com"));
		assertEquals(false, chk.validEmail("fabiouds"));
		assertEquals(false, chk.validEmail("fabioudshotmail.com"));
		assertEquals(false, chk.validEmail("123213@12312"));
		assertEquals(false, chk.validEmail("@das.com"));
		assertEquals(false, chk.validEmail("@hotmail.com"));
		assertEquals(false, chk.validEmail("fabio@hotmail."));
		assertEquals(false, chk.validEmail("fabio@hotmail.."));
		assertEquals(false, chk.validEmail("......@......----"));
		assertEquals(false, chk.validEmail("......@hotmail.com"));

		
		assertEquals(false, chk.validEmail(""));
		assertEquals(false, chk.validEmail(null));

	}

	@Test
	public void testValidDate() {
		Check chk = new Check();

		assertEquals(false, chk.validDate("00-11-2000"));
		assertEquals(false, chk.validDate("01-00-2000"));
		assertEquals(false, chk.validDate("01-11-0000"));
		assertEquals(false, chk.validDate("00-13-1900"));
		assertEquals(false, chk.validDate("11-1900"));
		assertEquals(false, chk.validDate("-11-1900"));
		assertEquals(false, chk.validDate("0-11-1900"));
		assertEquals(false, chk.validDate(" -11-1900"));
		assertEquals(false, chk.validDate("31-11-2012"));

		assertEquals(false, chk.validDate(" - - "));
		assertEquals(false, chk.validDate(null));

		assertEquals(true, chk.validDate("11-11-2012"));
		assertEquals(true, chk.validDate("01-01-2012"));
		assertEquals(true, chk.validDate("30-12-2012"));
		assertEquals(true, chk.validDate("11-11-2012"));
		

	}

	@Test
	public void testIp() {
		Ip ip = new Ip();

		assertEquals(true, ip.getIp().equals(ip.getIp()));

	}
	
	@Test
	public void testMail() {
		Mail mail = new Mail();

		assertEquals(true, mail.sendEmail("fabiouds@hotmail.com", "teste"));
		assertEquals(false, mail.sendEmail("@.com", "teste"));

	}
	@Test
	public void testMessageLogs() {
		MessageLogs message = MessageLogs.getInstance();

		assertEquals(true, message.entrouSistema("usernameTeste"+";"+true+";"+ armazem));
		assertEquals(true, message.saiuSistema("usernameTeste"+";"+true+";"+ armazem));
		assertEquals(true, message.adicionaUser("usernameTeste"+";"+ true+";"+armazem ,"nifTeste","nomeTeste"));
		assertEquals(true, message.removeUser("usernameTeste"+";"+ true+";"+armazem ,"nomeTeste"));
		assertEquals(true, message.editaUser("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "novoNomeTeste"));
		assertEquals(true, message.adicionaMaquina("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "numeroTeste"));
		assertEquals(true, message.removeMaquina("usernameTeste"+";"+true+";"+armazem,"nomeTeste", "numeroTeste"));
		assertEquals(true, message.editaMaquina("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "numeroTeste", "novoNomeTeste", "novoNumeroTeste"));
		assertEquals(true, message.adicionaFuncionario("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "nifTeste"));
		assertEquals(true, message.removeFuncionario("usernameTeste"+";"+true+";"+armazem,"nomeTeste", "nifTeste"));
		assertEquals(true, message.editaFuncionario("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "numeroTeste", "novoNomeTeste", "novoNumeroTeste"));
		assertEquals(true, message.adicionaFornecedor("usernameTeste"+";"+ true+";"+armazem,"nomeTeste"));
		assertEquals(true, message.removeFornecedor("usernameTeste"+";"+ true+";"+armazem,"nomeTeste"));
		assertEquals(true, message.editaFornecedor("usernameTeste"+";"+ true+";"+armazem,"nomeTeste","novoNomeTeste"));
		assertEquals(true, message.adicionaCategoria("usernameTeste"+";"+ true+";"+armazem,"categoriaTeste", "subCategoriaTeste"));
		assertEquals(true, message.removeCategoria("usernameTeste"+";"+ true+";"+armazem,"categoriaTeste", "subCategoriaTeste"));
		assertEquals(true, message.editaBaseDados("usernameTeste"+";"+ true+";"+armazem,"userTeste;urlTeste;passTeste", "userTesteNova;urlTesteNova;passTesteNova"));
		assertEquals(true, message.reportaAvaria("usernameTeste"+";"+ true+";"+armazem,"nomeTeste","idTeste", "random"));
		assertEquals(true, message.corrigeAvaria("usernameTeste"+";"+ true+";"+armazem,"nomeTeste", "idTeste"));
		assertEquals(true, message.reportaDefeito("usernameTeste"+";"+ true+";"+armazem,"skuTeste"));
		assertEquals(true, message.enviaProduto("usernameTeste"+";"+ true+";"+armazem,"skuTeste", "24/12/2019"));
		assertEquals(true, message.receberProduto("usernameTeste"+";"+ true+";"+armazem,"teste;teste;teste;teste;teste"));
		assertEquals(true, message.mudarArmazem("usernameTeste"+";"+ true+";"+"warehouse1","warehouse1;porto;teste;teste"));


	}


}
