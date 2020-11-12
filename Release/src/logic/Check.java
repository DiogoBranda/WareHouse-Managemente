package logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;


public class Check {

	/**
	 * Verifica se o texto está em branco, se leva espaço no inicio ou no fim ou contem ";"
	 * 
	 * @param string Texto a ser analisado
	 * @return boolean True se é um texto válido / False no caso contrario
	 */
	public boolean blankText(String string) {
		if (string == null)
			return true;
		String[] aux = string.split(";");
		if(aux.length!=1)
			return true;
		return ((string.trim().isEmpty()) || Character.isWhitespace(string.charAt(0))
				|| Character.isWhitespace(string.charAt(string.length() - 1)));
	}

	/**
	 * Verifica se as 2 passwords passados por parametro são iguais
	 * 
	 * @param string1 Password a ser analisada
	 * @param string2 Password a ser analisada
	 * @return boolean True se passwords iguais / False no caso contrario
	 */
	public boolean samePassword(String string1, String string2) {
		return string1.equals(string2);
	}

	/**
	 * Verifica a password é maior que 6 caracteres, que nao está vazia, ou que leva
	 * espaços no inicio e fim
	 * 
	 * @param string1 Password a analisar
	 * @return boolean True se é uma password válido / False no caso contrario
	 */
	public boolean validPassword(String string1) {
		if (string1 == null)
			return false;
		return (string1.length() >= 6 && !blankText(string1));
	}

	/**
	 * Verifica se vetor tem um tamanho diferente de 1
	 * 
	 * @param indexOfRow Vetor int[] a analisar
	 * @return boolean True se tamanho diferente de 1 / False no caso contrario
	 */
	public boolean multipleSelection(int[] indexOfRow) {
		if (indexOfRow == null)
			return false;
		return (indexOfRow.length != 1);
	}

	/**
	 * Verifica se texto é constituido apenas por numeros
	 * 
	 * @param string Texto a analisar
	 * @return boolean True se string for constituida so por numeros / False no caso
	 *         contrario
	 */
	public boolean onlyNumbers(String string) {
		if (string == null)
			return false;
		return (string.matches("[0-9]+"));
	}

	/**
	 * Verifica se objeto não é null
	 * 
	 * @param object Objeto a analisar
	 * @return boolean True se object nao é null / False no caso contrario
	 */
	public boolean validSelectedItem(Object object) {
		return (object != null);
	}

	/**
	 * Verifica se o texto nao contem numeros
	 * 
	 * @param string String a analisar
	 * @return boolean True se string nao tiver numeros / False no caso contrario
	 */
	public boolean textValidWithoutNumbers(String string) {
		if (string == null)
			return false;
		if (blankText(string))
			return false;
		for (char ch : string.toCharArray()) {
			if (Character.isDigit(ch)) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Verifica se o nif é valido
	 * 
	 * @param nif - Nif a analisar
	 * @return boolean True se é um nif válido / False no caso contrario
	 */
	public boolean isNifValid(String nif) {
		if (nif == null)
			return false;
		final int max = 9;
		if (!nif.matches("[0-9]+") || nif.length() != max)
			return false;
		int checkSum = 0;
		// calcula a soma de controlo
		for (int i = 0; i < max - 1; i++) {
			checkSum += (nif.charAt(i) - '0') * (max - i);
		}
		int checkDigit = 11 - (checkSum % 11);
		if (checkDigit >= 10)
			checkDigit = 0;
		return checkDigit == nif.charAt(max - 1) - '0';
	}

	/**
	 * Verifica se foi selecionado 1 categoria ou 1 sub categoria
	 * 
	 * @param indexOfCategoria    - Vetor int[] a analisar pertencente à categoria
	 * @param indexOfSubCategoria - Vetor int[] a analisar pertencente à categoria
	 * @return int<br>
	 *         0 se arrays forem null <br>
	 *         1 se selecionou categoria <br>
	 *         2 se selecionou sub categoria <br>
	 *         -1 se selecionou mais que 1 categoria e sub categorias <br>
	 *         3 se não selecionou nenhuma linha<br>
	 *         -2 se ocorreu algum problema
	 */
	public int selectedIndex(int[] indexOfCategoria, int[] indexOfSubCategoria) {
		if (indexOfCategoria == null || indexOfSubCategoria == null)
			return 0;
		if (indexOfCategoria.length > 0 && indexOfSubCategoria.length == 0) {
			return 1;
		} else if (indexOfCategoria.length == 1 && indexOfSubCategoria.length > 0) {
			return 2;
		} else if (indexOfCategoria.length == 0 && indexOfSubCategoria.length == 0) {
			return 3;
		} else if (indexOfCategoria.length > 0 && indexOfSubCategoria.length > 0) {
			return -1;
		}
		return -2;
	}

	/**
	 * Verifica se o email introduzido é válido
	 * 
	 * @param email - Email a verificar
	 * @return boolean <br>
	 *         true - se for um email valido <br>
	 *         false - se for email inválido
	 */
	public boolean validEmail(String email) {
		if (email == null)
			return false;
		String[] aux = email.split(";");
		if(aux.length!=1)
			return false;
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);

		return pat.matcher(email).matches();
	}

	/**
	 * Verifica se a data introduzida é válida
	 * @param date - Data a verificar
	 * @return boolean <br>
	 *         true - se for uma data valida <br>
	 *         false - se for data inválida
	 */
	public boolean validDate(String date) {
		if (date == null)
			return false;
		try {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			df.setLenient(false);
			df.parse(date);
		} catch (ParseException e) {
			return false;
		}
		return true;

	}
}
