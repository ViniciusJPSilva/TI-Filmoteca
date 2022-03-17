package vjps.filmoteca.gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe responsável por fornecer serviços de interface gráfica que dá ao usuário acesso aos arquivos do sistema de arquivos.
 * @author Vinícius José Pires Silva
 */
public class IgArquivo {
	
	private static JFileChooser fileChooser;
	
	private static JFileChooser getFileChooser() {
		return(fileChooser == null) ? fileChooser = new JFileChooser() : fileChooser;
	}
	
	public static String dialogoAbrirArquivo(Component janela, String titulo) {
		definirPropriedades(titulo, "Abre um arquivo.", KeyEvent.VK_A);
		
		// Exibe a caixa de diálogo "Abrir Arquivo".
		int opcao = fileChooser.showDialog(janela, "Abrir");
		
		// Verifica se o usuário cancelou a operação; se não, obtém o nome do arquivo digitado ou selecionad pelo usuário na caixa de diálogo.
		try {
			return (opcao == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile().getCanonicalPath() : null; 
		}catch(Exception e) {
			return null;
		}
	}

	public static String dialogoGravarArquivo(Component janela, String titulo) {
		definirPropriedades(titulo, "Grava o texto no arquivo", KeyEvent.VK_G);
		
		// Exibe a caixa de diálogo "Abrir Arquivo".
		int opcao = fileChooser.showDialog(janela, "Gravar");
		
		// Verifica se o usuário cancelou a operação; se não, obtém o nome do arquivo digitado ou selecionad pelo usuário na caixa de diálogo.
		try {
			return (opcao == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile().getCanonicalPath() : null; 
		}catch(Exception e) {
			return null;
		}
	}

	private static void definirPropriedades(String titulo, String textoBotao, int letraMnemonica) {
		final String DIRETORIO_ATUAL = ".", ARQUIVOS_TXT = "Arquivos de texto (*.txt)", EXTENSAO_TXT = "txt";
		
		if(fileChooser == null) {
			fileChooser = getFileChooser();

			// Indica que o usuário poderá selecionar apenas nomes de arquivos.
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			// Define qual é o diretório default.
			fileChooser.setCurrentDirectory(new File(DIRETORIO_ATUAL));

			// Cria os filtros de extensão de arquivo que serão usados pelo JFileChooser para filtrar os tipos de arquivos que serão exibidos na janela.
			FileNameExtensionFilter txtExtensionFilter = new FileNameExtensionFilter(ARQUIVOS_TXT, EXTENSAO_TXT);
			
			// O último tipo de arquivo acrescentado ao JFileChooser é considerado o filtro (ou tipo) default quando se usa o método abaixo.
			fileChooser.setFileFilter(txtExtensionFilter);
			
		}
		
		// Define o título da caixa de diálogo.
		fileChooser.setDialogTitle(titulo);
		
		// Define um texto de ajuda para o botão principal.
		fileChooser.setApproveButtonToolTipText(textoBotao);
		
		// Define uma letra mnemônica texto de ajuda para o botão principal da caixa de diálogo.
		fileChooser.setApproveButtonMnemonic(letraMnemonica);
		
	}// definirPropriedades()
	
}// class IgArquivo
