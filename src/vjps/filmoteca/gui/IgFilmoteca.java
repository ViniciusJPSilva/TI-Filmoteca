package vjps.filmoteca.gui;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showInternalMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static vjps.filmoteca.arquivo.Arquivo.obterExtensao;
import static vjps.filmoteca.util.Constantes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import vjps.filmoteca.arquivo.Arquivo;
import vjps.filmoteca.modelo.Filme;
import vjps.filmoteca.modelo.FilmeList;
import vjps.filmoteca.modelo.Pessoa;

public class IgFilmoteca extends JFrame {
	
	private UIManager.LookAndFeelInfo lookAndFeelInfo[];
	
	private JTextField pesquisaTextField;
	private JTextField diretorTextField;
	private JTextField autoresTextField;
	private JTextField imdbTextField;
	
	private JLabel posterLabel;
	private JLabel tituloLabel;
	private JLabel dataLancamentoLabel;
	private JLabel classificacaoLabel;
	private JLabel duracaoLabel;
	private JLabel quantFilmesLabel;
	private JLabel generoUmLabel;
	private JLabel generoDoisLabel;
	private JLabel generoTresLabel;
	
	private JTextArea sinopseTextArea;
	
	private JSpinner avaliacaoSpinner;
	
	private JRadioButton dvdRadioButton;
	private JRadioButton bluRayRadioButton;
	
	private JPanel informacoesFilmePanel;
	
	private JButton anteriorButton;
	private JButton proximoButton;

	private JTable elencoTable;
	
	private DefaultTableModel defaultTableModel;
	
	private FilmeList filmes;
	private List<Filme> filmesSelecionados;
	
	private int filmeApresentado;
	
	private String caminho;

	/**
	 * Cria e exibe a GUI.
	 */
	public IgFilmoteca() {
		
		lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
		
		Font fontePadrao = new Font("SansSerif", Font.PLAIN, 15),
				fontePadraoBold = new Font("SansSerif", Font.BOLD, 15),
				fonteTitulo = new Font("SansSerif", Font.BOLD, 25);
		
		filmes = new FilmeList();
		final int NUMERO_LINHAS_TABELA = 30;
		
		addWindowListener(new WindowAdapter() {	
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				mudarAparencia("Nimbus");
			}
		});
		
		// ConentPane
		Container contentPane = getContentPane();
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menus
		JMenu filmotecaMenu = new JMenu(TITULO);
		JMenu ajudaMenu = new JMenu(MENU_AJUDA);
		
		filmotecaMenu.setMnemonic(KeyEvent.VK_F);
		ajudaMenu.setMnemonic(KeyEvent.VK_U);
		
		menuBar.add(filmotecaMenu);
		menuBar.add(ajudaMenu);
		
		//Menu Itens e Separadores - Menu Filmoteca
		JMenuItem obterFilmesMenuItem = new JMenuItem(MENU_OBTER_FILMES);
		JMenuItem pesquisarMenuItem = new JMenuItem(MENU_PESQUISAR);
		JMenuItem melhoresFilmesMenuItem = new JMenuItem(MENU_MELHORES_FILMES);
		JSeparator separator = new JSeparator();
		JMenuItem sairMenuItem = new JMenuItem(MENU_SAIR);
		
		obterFilmesMenuItem.setMnemonic(KeyEvent.VK_O);
		pesquisarMenuItem.setMnemonic(KeyEvent.VK_P);
		melhoresFilmesMenuItem.setMnemonic(KeyEvent.VK_M);
		sairMenuItem.setMnemonic(KeyEvent.VK_S);
		
		pesquisarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		melhoresFilmesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
		
		filmotecaMenu.add(obterFilmesMenuItem);
		filmotecaMenu.add(pesquisarMenuItem);
		filmotecaMenu.add(melhoresFilmesMenuItem);
		filmotecaMenu.add(separator);
		filmotecaMenu.add(sairMenuItem);
		
		//Menu Itens - Menu Ajuda
		JMenuItem sobreMenuItem = new JMenuItem(MENU_SOBRE);
		sobreMenuItem.setMnemonic(KeyEvent.VK_S);
		ajudaMenu.add(sobreMenuItem);
		
		// Pesquisar textField
		pesquisaTextField = new JTextField(STR_PESQUISA_TEXT_FIELD);
		pesquisaTextField.setFont(fontePadrao);
		contentPane.add(pesquisaTextField, BorderLayout.NORTH);
		pesquisaTextField.setColumns(10);
		
		// Tabbed Pane
		JTabbedPane abasTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(abasTabbedPane, BorderLayout.CENTER);
		
		// Painéis - Abas
		JPanel filmePanel = new JPanel();
		JPanel elencoPanel = new JPanel();
		
		filmePanel.setLayout(null);
		
		abasTabbedPane.addTab(ABA_FILME, null, filmePanel, ABA_FILME_TOOLTIP);
		abasTabbedPane.addTab(ABA_ELENCO, null, elencoPanel, ABA_ELENCO_TOOLTIP);
		elencoPanel.setLayout(new BorderLayout(0, 0));

		
		// Elenco - Paineis		
		elencoTable = new JTable();
		defaultTableModel = new DefaultTableModel(COLUNAS, NUMERO_LINHAS_TABELA);
		elencoTable.setShowVerticalLines(true);
		elencoTable.setShowHorizontalLines(true);
		elencoTable.setFont(fontePadrao);
		
		elencoTable.setEnabled(false);
		
		elencoTable.setModel(defaultTableModel);
		elencoTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		elencoTable.getColumnModel().getColumn(1).setPreferredWidth(593);
		
		// Adiciona a tabela ao painel.
		JScrollPane scrollPane = new JScrollPane(elencoTable);
		elencoPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		// Filme - Paineis
		informacoesFilmePanel = new JPanel();
		JPanel sinopsePanel = new JPanel();
		JPanel avaliacaoPanel = new JPanel();
		JPanel midiaPanel = new JPanel();
		
		informacoesFilmePanel.setBounds(282, 6, 535, 94);
		sinopsePanel.setBounds(282, 178, 535, 120);
		avaliacaoPanel.setBounds(282, 310, 310, 70);
		midiaPanel.setBounds(604, 310, 213, 70);
		
		sinopsePanel.setToolTipText(SINOPSE_TOOLTIP);
		midiaPanel.setToolTipText(MIDIA_TOOLTIP);
		
		sinopsePanel.setBorder(new TitledBorder(null, PANEL_SINOPSE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		avaliacaoPanel.setBorder(new TitledBorder(null, PANEL_AVALIACAO, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		midiaPanel.setBorder(new TitledBorder(null, PANEL_MIDIA, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		informacoesFilmePanel.setLayout(new MigLayout("", "[][][][][][][][]", "[][][]"));
		sinopsePanel.setLayout(new BorderLayout(0, 0));
		
		filmePanel.add(informacoesFilmePanel);
		filmePanel.add(sinopsePanel);
		filmePanel.add(avaliacaoPanel);
		filmePanel.add(midiaPanel);
		
		// Filme - Labels
		posterLabel = new JLabel("");
		posterLabel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tituloLabel = new JLabel("Título do Filme");
		dataLancamentoLabel = new JLabel("Ano");
		classificacaoLabel = new JLabel("Classificação");
		duracaoLabel = new JLabel("Duração");
		generoUmLabel = new JLabel("");
		generoDoisLabel = new JLabel("");
		generoTresLabel = new JLabel("");
		JLabel diretorLabel = new JLabel(LABEL_DIRETOR);
		JLabel autoresLabel = new JLabel(LABEL_AUTORES);
		JLabel imdbLabel = new JLabel(LABEL_IMDB);
		JLabel pessoalLabel = new JLabel(LABEL_PESSOAL);
		
		tituloLabel.setFont(fonteTitulo);
		dataLancamentoLabel.setFont(fontePadraoBold);
		classificacaoLabel.setFont(fontePadraoBold);
		duracaoLabel.setFont(fontePadraoBold);
		generoUmLabel.setFont(fontePadrao);
		generoDoisLabel.setFont(fontePadrao);
		generoTresLabel.setFont(fontePadrao);
		diretorLabel.setFont(fontePadrao);
		autoresLabel.setFont(fontePadrao);
		
		posterLabel.setBounds(12, 8, 250, 371);
		diretorLabel.setBounds(282, 117, 55, 16);
		autoresLabel.setBounds(282, 150, 55, 16);
		
		tituloLabel.setToolTipText(TITULO_TOOLTIP);
		classificacaoLabel.setToolTipText(CALSSIFICACAO_TOOLTIP);
		duracaoLabel.setToolTipText(DURACAO_TOOLTIP);
		diretorLabel.setToolTipText(DIRETOR_TOOLTIP);
		autoresLabel.setToolTipText(AUTORES_TOOLTIP);
		imdbLabel.setToolTipText(IMDB_TOOLTIP);
		pessoalLabel.setToolTipText(PESSOAL_TOOLTIP);
		
		pessoalLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		
		filmePanel.add(posterLabel);
		informacoesFilmePanel.add(tituloLabel, "cell 0 0 8 1");
		informacoesFilmePanel.add(dataLancamentoLabel, "cell 1 1");
		informacoesFilmePanel.add(classificacaoLabel, "cell 2 1");
		informacoesFilmePanel.add(duracaoLabel, "cell 3 1");
		informacoesFilmePanel.add(generoUmLabel, "cell 1 2");
		informacoesFilmePanel.add(generoDoisLabel, "cell 2 2");
		informacoesFilmePanel.add(generoTresLabel, "cell 3 2");
		filmePanel.add(diretorLabel);	
		filmePanel.add(autoresLabel);
		
		
		// Filme - Text Fields
		diretorTextField = new JTextField();
		autoresTextField = new JTextField();
		imdbTextField = new JTextField();
		
		diretorTextField.setEditable(false);
		autoresTextField.setEditable(false);
		imdbTextField.setEditable(false);
		
		diretorTextField.setBounds(339, 112, 185, 28);
		autoresTextField.setBounds(339, 145, 478, 28);
		
		diretorTextField.setFont(fontePadrao);
		autoresTextField.setFont(fontePadrao);
	
		diretorTextField.setColumns(10);
		autoresTextField.setColumns(10);
		imdbTextField.setColumns(3);
		
		filmePanel.add(diretorTextField);
		filmePanel.add(autoresTextField);
		
		imdbLabel.setLabelFor(imdbTextField);
		
		avaliacaoPanel.add(imdbLabel);
		avaliacaoPanel.add(imdbTextField);
		avaliacaoPanel.add(pessoalLabel);
		
		// Filme - Text Area
		sinopseTextArea = new JTextArea();
		sinopseTextArea.setEditable(false);
		sinopseTextArea.setLineWrap(true);
		sinopseTextArea.setWrapStyleWord(true);
		sinopseTextArea.setFont(fontePadrao);
		sinopsePanel.add(new JScrollPane(sinopseTextArea), BorderLayout.CENTER);
		
		// Filme - Spinner
		avaliacaoSpinner = new JSpinner();
		avaliacaoSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		avaliacaoPanel.add(avaliacaoSpinner);
		pessoalLabel.setLabelFor(avaliacaoSpinner);
		
		// Filme - RadioButton
		bluRayRadioButton = new JRadioButton(RBUTTON_BLURAY);
		dvdRadioButton = new JRadioButton(RBUTTON_DVD);
		
		bluRayRadioButton.setMnemonic(KeyEvent.VK_B);
		dvdRadioButton.setMnemonic(KeyEvent.VK_D);
		
		midiaPanel.add(bluRayRadioButton);
		midiaPanel.add(dvdRadioButton);
		
		
		// Painel - Botões
		JPanel botoesPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) botoesPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(botoesPanel, BorderLayout.SOUTH);
		
		//Botões - Painel Botões
		anteriorButton = new JButton(BTN_ANTERIOR);
		proximoButton = new JButton(BTN_PROXIMO);
		
		anteriorButton.setMnemonic(KeyEvent.VK_A);
		proximoButton.setMnemonic(KeyEvent.VK_P);
		
		anteriorButton.setEnabled(false);
		proximoButton.setEnabled(false);
		
		// Label
		quantFilmesLabel = new JLabel("");
		
		botoesPanel.add(anteriorButton);
		botoesPanel.add(quantFilmesLabel);
		botoesPanel.add(proximoButton);
		
		// Eventos da barra de Pesquisa
		pesquisaTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER)
					buscarFilmes();
			}
		});
		
		// Ação do Spinner
		avaliacaoSpinner.addChangeListener( (e) -> alterarAvaliacaoPessoal());
		
		// Ação radiobutton
		bluRayRadioButton.addItemListener((e) -> definirMidia(bluRayRadioButton, 1));
		
		dvdRadioButton.addActionListener((e) -> definirMidia(dvdRadioButton, 2));
		
		// Ações dos Botões dos menus
		obterFilmesMenuItem.addActionListener((e) -> adicionarFilmes());
		pesquisarMenuItem.addActionListener((e) -> pesquisaTextField.requestFocus());
		melhoresFilmesMenuItem.addActionListener((e) -> melhoresFilmes());
		sobreMenuItem.addActionListener((e) -> new IgSobre(IgFilmoteca.this));
		sairMenuItem.addActionListener((e) -> System.exit(0));
		
		// Ações dos Botões próximo e anterior
		anteriorButton.addActionListener((e) -> atualizacaoCompleta(filmeApresentado-1));
		proximoButton.addActionListener((e) -> atualizacaoCompleta(filmeApresentado+1));
		
		
		setTitle(TITULO);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 549);
		setResizable(false);
		setVisible(true);
	} // Construtor
	
	
	/**
	 * Altera a propriedade mídia do filme, tanto blu-ray quanto dvd.
	 */
	private void definirMidia(JRadioButton radioButton, int tipo) {
		if(filmesSelecionados != null)
			if(tipo == 1)
				filmesSelecionados.get(filmeApresentado).setBluRay(radioButton.isSelected() ? true : false);
			else
				filmesSelecionados.get(filmeApresentado).setDvd(radioButton.isSelected() ? true : false);
		else
			radioButton.setSelected(false);
	}

	
	/**
	 * Altera a avaliação pessoal do filme selecionado.
	 */
	private void alterarAvaliacaoPessoal() {
		if(filmesSelecionados != null)
			filmesSelecionados.get(filmeApresentado).setNota(Integer.parseInt(avaliacaoSpinner.getValue().toString()));
	}


	/**
	 * Permite a adição de filmes à lista de filmes da aplicação por meio de uma caixa de diálogo.
	 */
	private void adicionarFilmes() {
		final String STR_PONTO_VIRGULA = ";",
				STR_IGUAL = "=";
		
		final char CARACTER_NOVA_LINHA = '\n';
		final int MIN_LINHAS = 7;
		
		List<String> controle = new ArrayList<>();
		int index = 0;
		
		String conteudoArquivo = abrirArquivo();
		
		if(conteudoArquivo == null)
			return;
		
		// Obtendo cada linha da String de conteúdo do arquivo.
		boolean fimArquivo = false;
		String linha = new String();
		while(linha != null) {
			try {
				linha = conteudoArquivo.substring(index, conteudoArquivo.indexOf(CARACTER_NOVA_LINHA, index)); // Obtém uma linha do arquivo.
				
				// Verificando se ocorreram duas linhas em branco consecutivas (fim do arquivo)
				if(linha.isBlank() && !fimArquivo) 
					fimArquivo = true;
				else
					if(linha.isBlank() && fimArquivo) {
						controle.remove(controle.size() - 1); // Remove a última linha em branco.
						break;
					} else
						fimArquivo = false;
				
				controle.add(linha);
				index = conteudoArquivo.indexOf(CARACTER_NOVA_LINHA, index) + 1;
			} catch (StringIndexOutOfBoundsException e) {
				linha = null;
			}
		}
		
		// Verifica se o arquivo possui o número minimo de linhas (4: titulo, serie, descrição e um par de dados)
		if(controle.size() < MIN_LINHAS)
			throw new StringIndexOutOfBoundsException();
		
		filmes = new FilmeList();
		
		try {
			// Convertendo e obtendo os demais dados.
			for(index=0; index < controle.size(); index++) {
				Filme filme = new Filme();
				String strFilme;
				
				// Imagem do Filme
				if(filmes.quantidadeFilmes() == 0)
					strFilme = controle.get(index);
				else
					try {
						while(true) {
							strFilme = controle.get(++index);
							if(strFilme.contains("##"))
								break;
						}
					}catch (IndexOutOfBoundsException e) {
						break;
					}
					
				filme.setNumero(filmes.quantidadeFilmes() + 1);
			
				// título; ano; data de lançamento; classificação indicativa; duração; avaliação IMDB
				strFilme = controle.get(++index);
				
				int anter = strFilme.indexOf(STR_PONTO_VIRGULA);
				filme.setTitulo(strFilme.substring(0, anter));
				
				int prox = strFilme.indexOf(STR_PONTO_VIRGULA, anter);
				anter = prox + 2;
				prox = strFilme.indexOf(STR_PONTO_VIRGULA, anter);
				filme.setAno(strFilme.substring(anter, prox));
				
				anter = prox + 2;
				prox = strFilme.indexOf(STR_PONTO_VIRGULA, anter);
				filme.setDataLancamento(strFilme.substring(anter, prox));
				
				anter = prox + 2;
				prox = strFilme.indexOf(STR_PONTO_VIRGULA, anter);
				filme.setClassificacao(strFilme.substring(anter, prox));
				
				anter = prox + 2;
				prox = strFilme.indexOf(STR_PONTO_VIRGULA, anter);
				filme.setDuracao(strFilme.substring(anter, prox));
				
				filme.setImdb(Float.parseFloat(strFilme.substring(prox + 1)));
				
				// Sinopse
				strFilme = controle.get(++index);
				String sinopse = new String();
				while(!strFilme.contains("gêneros=")) {
					if(strFilme.contains("sinopse="))
						sinopse = strFilme.substring(strFilme.indexOf(STR_IGUAL) + 1);
					else 
						sinopse = String.format("%s%s", sinopse, strFilme);
					
					sinopse = String.format("%s ", sinopse); // Adicionando um espaço no lugar da quebra de linha
					strFilme = controle.get(++index);
				}
				
				filme.setSinopse(sinopse);
				
				// Gêneros
				for(String gen : obterStrings(index, controle, STR_PONTO_VIRGULA))
					filme.addGenero(gen);
				
				// Diretor
				strFilme = controle.get(++index);
				filme.addPessoa(new Pessoa(strFilme.substring(strFilme.indexOf(STR_IGUAL) + 1), Pessoa.DIRETOR));
				
				//Autores
				index++;
				for(String aut : obterStrings(index, controle, STR_PONTO_VIRGULA))
					filme.addPessoa(new Pessoa(aut, Pessoa.AUTOR));
				
				//Elenco
				index++;
				for(String art : obterStrings(index, controle, STR_PONTO_VIRGULA))
					filme.addPessoa(new Pessoa(art, Pessoa.ARTISTA));
				
				//Adiciona o filme à lista.
				filmes.addFilme(filme);
			}
		
			showMessageDialog(this, MSG_FILMES_ADD_SUCESSO, MSG_ABRIR_ARQUIVO, INFORMATION_MESSAGE);
		} catch (Exception e) {
			showMessageDialog(this, ERRO_NAO_FOI_POSSIVEL_ADD, MSG_ABRIR_ARQUIVO, ERROR_MESSAGE);
			return;
		}
		
		filmesSelecionados = filmes.getFilmes();
		atualizacaoCompleta(0);
	} // adicionarFilmes()
	
	
	/**
	 * Realiza uma busca apurada na lista de filmes e atualiza a lista de filmes selecionados.
	 */
	protected void buscarFilmes() {
		String busca = pesquisaTextField.getText().toLowerCase();
		List<Filme> filmesPesquisa = new ArrayList<>();

		imdbTextField.requestFocus();
		
		if(filmes.quantidadeFilmes() == 0) { // 
			showMessageDialog(this, ERRO_NAO_HA_FILMES, MSG_PESQUISAR_FILMES, ERROR_MESSAGE);
			return;
		}
		
		if(!busca.equalsIgnoreCase(STR_PESQUISA_TEXT_FIELD)) {
			filmesPesquisa = filmes.pesquisarFilmes(busca);
				
			if(filmesPesquisa.size() == 0) {
				showMessageDialog(this, String.format("%s não cadastrado.", busca), MSG_PESQUISAR_FILMES, ERROR_MESSAGE);
				return;
			}
		} else {
			showMessageDialog(this, ERRO_DIGITE_ALGO, MSG_PESQUISAR_FILMES, ERROR_MESSAGE);
			return;
		}
		
		filmesSelecionados = filmesPesquisa;
		atualizacaoCompleta(0);
	}
		
	
	/**
	 * Apresenta uma janela para o usuário escolher mostrar os melhores filmes classificando pela nota Pessoal ou do IMDB.
	 */
	private void melhoresFilmes() {
		if(filmesSelecionados != null) {
			String opcoes[] = {"IMDB", "Pessoal"};
			
			pesquisaTextField.setText("");
			
			int op = JOptionPane.showOptionDialog(this, MSG_COMO_DESEJA_CLASSIFICAR, MSG_MELHORES_FILMES, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
		
			if(op == 0)
				ordenarPorImdb();
			else
				ordenarPorPessoal();
		} else
			showMessageDialog(this, ERRO_NAO_HA_FILMES, MSG_PESQUISAR_FILMES, ERROR_MESSAGE);
	}

	
	/**
	 * Ordena os Filmes pela nota do IMDB
	 */
	private void ordenarPorImdb() {
		Collections.sort(filmesSelecionados, new Comparator<Filme>() {
            @Override
            public int compare(Filme f1, Filme f2) {
            	return Float.compare(f1.getImdb(), f2.getImdb());
            }
        }.reversed());
		
		atualizacaoCompleta(0);
	} // ordenarPorImdb()
	
	
	/**
	 * Ordena os Filmes pela nota pessoal do usuário.
	 */
	private void ordenarPorPessoal() {
		Collections.sort(filmesSelecionados, new Comparator<Filme>() {
            @Override
            public int compare(Filme f1, Filme f2) {
            	return Float.compare(f1.getNota(), f2.getNota());
            }
        }.reversed());
		
		atualizacaoCompleta(0);
	} // ordenarPorImdb()

	
	/**
	 * Atualiza os painéis Filme e Elenco de acordo com o filme selecionado. 
	 * @param index do filme na lista filmesSelecionados.
	 */
	private void atualizacaoCompleta(int index) {
		List<Pessoa> elenco = new ArrayList<>();
		Filme filme = filmesSelecionados.get(index);
		filmeApresentado = index;
		
		// Poster
		posterLabel.setIcon(new ImageIcon(String.format("%s%s", caminho,filme.obterPoster())));
		
		// Titulo
		tituloLabel.setText(filme.getTitulo());
		
		// Ano
		dataLancamentoLabel.setText(filme.getAno());
		dataLancamentoLabel.setToolTipText(filme.getDataLancamento());
		
		// Classificação
		classificacaoLabel.setText(filme.getClassificacao());
		
		// Duração
		duracaoLabel.setText(filme.getDuracao());
		
		// Generos
		generoUmLabel.setText("");
		generoDoisLabel.setText("");
		generoTresLabel.setText("");
		
		try {
			generoUmLabel.setText(filme.getGeneros().get(0));
			generoDoisLabel.setText(filme.getGeneros().get(1));
			generoTresLabel.setText(filme.getGeneros().get(2));
		} catch (IndexOutOfBoundsException e) {
		}
		
		// Diretor, Autores e Elenco
		StringBuilder autores = new StringBuilder();
		for(Pessoa pessoa : filme.getPessoas())
			if(pessoa.getProfissao().equals(Pessoa.DIRETOR))
				diretorTextField.setText(pessoa.getNome());
			else if (pessoa.getProfissao().equals(Pessoa.AUTOR))
				autores.append(String.format("%s, ", pessoa.getNome()));
			else if(pessoa.getProfissao().equals(Pessoa.ARTISTA))
				elenco.add(pessoa);
		
		autoresTextField.setText(autores.toString().substring(0, autores.toString().lastIndexOf(",")));
		
		// Sinopse
		sinopseTextArea.setText(filme.getSinopse());

		// IMDB
		imdbTextField.setText(String.format("%.2f", filme.getImdb()));
		
		// Nota
		avaliacaoSpinner.setValue(filme.getNota() == 0 ? 1 : filme.getNota());
		
		// midia
		bluRayRadioButton.setSelected(filme.isBluRay());
		dvdRadioButton.setSelected(filme.isDvd());
		
		// Atualizando os botões
		atualizarBotoes();
		
		atualizarElenco(elenco);
	} // atualizacaoCompleta()
	
	
	/**
	 * Adiciona ou atualiza os dados da tabela de despesas.
	 */
	private void atualizarElenco(List<Pessoa> elenco) {
		int linha = 0;
		
		defaultTableModel = new DefaultTableModel(COLUNAS, elenco.size());
		
		elencoTable.setModel(defaultTableModel);
		elencoTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		elencoTable.getColumnModel().getColumn(1).setPreferredWidth(593);
		
		for(Pessoa artista : elenco) {
			defaultTableModel.setValueAt(linha + 1, linha, 0);
			defaultTableModel.setValueAt(artista.getNome(), linha, 1);
			
			linha ++;
		}
	} // atualizarDespesas()
	
	
	/**
	 * Habilita ou desabilita os botões de anterior e próximo de acordo com o resultado da pesquisa.
	 * Atualiza o campo quantFilmesLabel.
	 */
	private void atualizarBotoes() {
		quantFilmesLabel.setText(String.format("%d / %d", filmeApresentado + 1, filmesSelecionados.size()));
		anteriorButton.setEnabled((filmeApresentado == 0 || filmesSelecionados.size() <= 1) ? false : true);
		proximoButton.setEnabled((filmeApresentado == filmesSelecionados.size() - 1 || filmesSelecionados.size() <= 1) ? false : true);
	}


	/**
	 * Obtém as String individuais separadas por um divisor fornecido pelo usuário, nas linhas do texto.
	 * @return Lista com as strings individuais
	 */
	private List<String> obterStrings(int index, List<String> controle, String divisor){
		List<String> strs = new ArrayList<>();
		
		String strFilme = controle.get(index); // Obtém a string 
		strFilme = strFilme.substring(strFilme.indexOf("=") + 1); // Retira o texto antes do '='
		int aux = 0;
		boolean fim = false;
		while(!fim){
			int bkp = aux;
			String sub = strFilme.substring(aux); // Obtém uma substring a partir do index auxiliar.
			if(sub.contains(divisor)) { // Verifica se a substring contém o divisor (No caso do trabalho ';').
				strs.add(sub.substring(0, sub.indexOf(divisor))); // Adiciona à lista de strings individuais
				aux = sub.indexOf(divisor) + bkp + 2; // Atualiza o index auxiliar
				
				// Verifica se há mais de uma linha com as string individuais.
				if(sub.substring(sub.indexOf(divisor) + 1).isBlank()) {
					strFilme = controle.get(++index); // Obtém a próxima linha do texto.
					aux = 0;
				}
			}else {
				strs.add(sub); // Adiciona a última string individual
				fim = !fim; // Finaliza o loop
			}
		}
		return strs;
	}

	
	/**
	 * Exibe uma caixa de diálogo para o usuário selecionar o diretório e o nome do arquivo que ele deseja abrir.
	 * 
	 * @return String texto do arquivo ou null caso ocorra algum erro.
	 */
	private String abrirArquivo() {
		// Obtém o nome do arquivo com seu caminho absoluto.
		String nomeArquivo = IgArquivo.dialogoAbrirArquivo(this, MSG_ABRIR_ARQUIVO);
		
		// Verifica se o usuário cancelou a operação.
		if(nomeArquivo != null) {
			caminho = nomeArquivo.substring(0, nomeArquivo.lastIndexOf(File.separator) + 1);

			String extensao = obterExtensao(nomeArquivo);
			nomeArquivo = String.format("%s%s", nomeArquivo, (extensao != null) ? "":".txt");
			
			// Insere o nome do arquivo no campo de texto.
			try {
				return Arquivo.abrir(nomeArquivo);
			}catch (Exception e) {
				showMessageDialog(this, String.format("%s\n\n%s", ERRO_ABRIR_ARQUIVO, nomeArquivo), MSG_ABRIR_ARQUIVO, ERROR_MESSAGE);
				return null;
			}
		}
		
		return null;
	} // abrirArquivo()
	
	/**
	 * Muda a aparência da interface gráfica definindo um novo look-and-feel.
	 */
	public boolean mudarAparencia(String lookAndFeelName) {
		for (LookAndFeelInfo lookAndFeel : lookAndFeelInfo) 
			if (lookAndFeelName.equalsIgnoreCase(lookAndFeel.getName())) 
				try {  
					// Carrega o look-and-feel a ser usado pela GUI. 
					UIManager.setLookAndFeel(lookAndFeel.getClassName());

					// Ativa a aparência da GUI alterando o seu look-and-feel.
					SwingUtilities.updateComponentTreeUI(this);

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
					showInternalMessageDialog(getContentPane(), ERRO_LOOK_AND_FEEL, TITULO, ERROR_MESSAGE);
					return false;
				}
		return true;
	} // mudarAparencia()
	
} // class IgFilmoteca


