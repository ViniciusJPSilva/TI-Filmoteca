package vjps.filmoteca.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Fornece métodos de manipulação de arquivo texto.
 * 
 * <p>
 * <b>ATENÇÃO: </b>
 * Como o arquivo texto possui acesso sequencial, apenas uma operação pode ser realizada por vez, escrita ou leitura.
 * Se for leitura o arquivo deve ser aberto usando o método <code>open</code>, se for escrita use o método <code>create</code>.
 * Após o uso deve-se fechar o arquivo usando o método <code>close</code> ou use um bloco <code>try</code> com recurso ao instanciar um objeto <code>TextFile</code>,
 * pois essa classe é {@link AutoCloseable}.
 * </p>
 * 
 * @author Prof. Márlon Oliveira da Silva
 * 
 * @version 0.7
 * 
 */
public class TextFile implements AutoCloseable {
	private String nameFile;
	
	// O conteúdo do arquivo texto será lido usando um leitor de buffer.
	private BufferedReader bufferedReader;
	
	// O conteúdo do arquivo texto será escrito usando um escritor em buffer.
	private BufferedWriter bufferedWriter;

	/** 
	 * Para pemitir que um objeto <code>TextFile</code> seja criado sem atribuir um nome ao arquivo texto. O nome será definido ao 
	 * abrir ({@link #open(String)})  ou criar ({@link #create(String)}) o arquivo. 
	 */
	public TextFile() {
	}

	/**
	 * Abre um arquivo texto apenas para leitura. Considera que o conjunto de caracteres do arquivo é UTF-8.
	 * 
	 * @param nameFile nome do arquivo a ser aberto.
	 * 
	 * @throws FileNotFoundException se ocorrer um erro ao abrir o arquivo.
	 * @throws SecurityException se ocorrer uma negação de leitura do arquivo.
	 */
	public TextFile(String nameFile) throws FileNotFoundException {
		open(nameFile, StandardCharsets.UTF_8);
	}

	/**
	 * Abre um arquivo texto apenas para leitura. 
	 * 
	 * @param nameFile nome do arquivo a ser aberto;
	 * @param charSet conjunto de caracteres do arquivo.
	 * 
	 * @throws FileNotFoundException se ocorrer um erro ao abrir o arquivo.
	 * @throws SecurityException se ocorrer uma negação de leitura do arquivo.
	 */
	public TextFile(String nameFile, Charset charset) throws FileNotFoundException {
		open(nameFile, charset);
	}

	/**
	 * Obtém o nome do arquivo.
	 * 
	 * @return um <code>String</code> com o nome do arquivo.
	 */
	public String getNameFile() {
		return nameFile;
	}

	/** 
	 * Abre um arquivo texto somente para leitura.  
	 * 
	 * @param nameFile nome do arquivo a ser aberto;
	 * @param charSet conjunto de caracteres do arquivo.
	 * 
	 * @throws FileNotFoundException se ocorrer um erro ao abrir o arquivo.
	 * @throws SecurityException se ocorrer uma negação de leitura do arquivo.
	 */
	  public void open(String nameFile, Charset charSet) throws FileNotFoundException {
		  this.nameFile = nameFile;
		  
		  // Abre um arquivo para leitura usando o conjunto de caracteres especificado.
		  bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile), charSet));
	  } 

		/** 
		 * Abre um arquivo texto somente para leitura. Considera que o conjunto de caracteres do arquivo é UTF-8. 
		 * 
		 * @param nameFile nome do arquivo a ser aberto.
		 * 
		 * @throws FileNotFoundException se ocorrer um erro ao abrir o arquivo.
		 * @throws SecurityException se ocorrer uma negação de leitura do arquivo.
		 */
		  public void open(String nameFile) throws FileNotFoundException {
			  open(nameFile, StandardCharsets.UTF_8);
		  }

	  /** 
	   * Cria um arquivo texto apenas para escrita. Se o arquivo existir o seu conteúdo será apagado.  
	   * 
	   * @param nameFile nome do arquivo a ser criado;
	   * @param charSet conjunto de caracteres do arquivo.
	   * 
	   * @throws FileNotFoundException se ocorrer um erro ao criar o arquivo.
	   * @throws SecurityException se ocorrer uma negação de escrita no arquivo.
	   */
	  public void create(String nameFile, Charset charSet) throws FileNotFoundException {
		  this.nameFile = nameFile;
		  
		  // Cria o arquivo texto usando o conjunto de caracteres especificado.
		  bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nameFile), charSet));
	  } 

	  /** 
	   * Cria um arquivo texto apenas para escrita. Considera que o conjunto de caracteres do arquivo é UTF-8. Se o arquivo existir o seu conteúdo será apagado.  
	   * 
	   * @param nameFile nome do arquivo a ser criado;
	   * 
	   * @throws FileNotFoundException se ocorrer um erro ao criar o arquivo.
	   * @throws SecurityException se ocorrer uma negação de escrita no arquivo.
	   */
	  public void create(String nameFile) throws FileNotFoundException {
		  create(nameFile, StandardCharsets.UTF_8);
	  }

	  /** 
	   * Escreve uma <i>string</i>	no arquivo texto.
	   * 
	   * @param string <i>string</i> a ser escrita no arquivo texto.
	   * 
	   * @throws IOException se ocorrer um erro de E/S.
	   */
	  public void write(String string) throws IOException {
		  // Escreve a string e um caractere de nova linha ('\n') no arquivo texto.
		  bufferedWriter.write(string);
		  bufferedWriter.newLine();
	  } 
	  
	  /** 
	   * Lê o conteúdo completo do arquivo texto.
	   * 
	   * @return um <code>String</code> com o texto do arquivo.
	   *
	   * @throws IOException se ocorrer um erro de E/S.  
	   */
	  public String read() throws IOException {
		  String linha;
		  StringBuilder textoStringBuilder = new StringBuilder();
		  
		  // Lê o conteúdo completo do arquivo.
			do { linha = bufferedReader.readLine();
			
					 if (linha != null) {
						 textoStringBuilder.append(linha) ;
						 textoStringBuilder.append('\n');
					 }
					 
			} while (linha != null);
			
			return textoStringBuilder.toString();
	  } 
	  
	  /** 
	   * Lê a linha atual do arquivo texto.
	   * 
	   * @return um <code>String</code> com a linha atual do arquivo ou <code>null</code> se o fim de arquivo foi alcançado.
	   *
	   * @throws IOException se ocorrer um erro de E/S.  
	   */
	  public String readLine() throws IOException {
			return bufferedReader.readLine();
	  }
	  
	  /**
	   * Fecha os arquivos que foram criados para manipulação do arquivo texto.
	   * 
	   * @throws IOException se ocorrer algum erro ao fechar o arquivo.
	   */
	  public void close() throws IOException {
		  if (bufferedReader != null) bufferedReader.close();
		  if (bufferedWriter != null) bufferedWriter.close();
	  }
} // class TextFile