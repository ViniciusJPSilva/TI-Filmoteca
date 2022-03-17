package vjps.filmoteca.arquivo;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Classe responsável por manipular arquivos de texto. 
 * @author Vinícius José Pires Silva
 *
 */

public class Arquivo {

	/**
	 * Abre o arquivo texto, lê e retorna o seu conteúdo.
	 */
	public static String abrir(String nomeArquivo) throws FileNotFoundException, IOException {
		try(TextFile textFile = new TextFile(nomeArquivo)){
			return textFile.read();
		}
	}

	/**
	 * Verifica se o texto da string possui extensão e retorna a mesma. 
	 */
	public static String obterExtensao(String nomeArquivo) {
		try {
			return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
		} catch (Exception e) {
			return null;
		}
	}
	
}// class Arquivo
