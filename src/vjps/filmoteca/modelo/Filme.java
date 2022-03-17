package vjps.filmoteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Filme {
	
	public static final String CLASS_LIVRE = "Livre";
	
	private int numero;
	
	private String titulo;
	private String ano;
	private String dataLancamento;
	private String classificacao;
	private String duracao;
	private String sinopse;
	
	private float imdb;
	private int nota;
	
	private List<String> generos;
	private List<Pessoa> pessoas;
	
	private boolean bluRay;
	private boolean dvd;
	
	public Filme() {
		generos = new ArrayList<>();
		pessoas = new ArrayList<>();
	}

	public Filme(int numero, String titulo, String ano, String dataLancamento, String classificacao, String duracao,
			String sinopse, float imdb, int nota) {
		this();
		this.numero = numero;
		this.titulo = titulo;
		this.ano = ano;
		this.dataLancamento = dataLancamento;
		this.classificacao = classificacao;
		this.duracao = duracao;
		this.sinopse = sinopse;
		this.imdb = imdb;
		this.nota = nota;
	}

	//Getters e Setters

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getClassificacao() {
		if(!classificacao.equals(CLASS_LIVRE))
			return String.format("%s anos", classificacao);
		
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public float getImdb() {
		return imdb;
	}

	public void setImdb(float imdb) {
		this.imdb = imdb;
	}
	
	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public boolean isBluRay() {
		return bluRay;
	}

	public void setBluRay(boolean bluRay) {
		this.bluRay = bluRay;
	}

	public boolean isDvd() {
		return dvd;
	}

	public void setDvd(boolean dvd) {
		this.dvd = dvd;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %f\nSinopse: %s\nGêneros: %s\nPessoas: %s", titulo, ano, dataLancamento,
				classificacao, duracao, imdb, sinopse, generos, pessoas);
	}

	/**
	 * Adiciona um gênero à lista de gêneros.
	 * @param genero à ser adicionado
	 */
	public void addGenero(String genero) {
		generos.add(genero);
	}
	
	/**
	 * Adiciona uma pessoa à lista de pessoas.
	 * @param pessoa à ser adicionada.
	 */
	public void addPessoa(Pessoa pessoa) {
		pessoas.add(pessoa);
	}
	
	/**
	 * Obtém o nome do pôster do filme, seguindo o padrão 'filmeX.png' onde 'X' é o número do filme.
	 * @return poster - nome do arquivo do pôster do filme.
	 */
	public String obterPoster() {
		return String.format("filme%d.png", numero);
	}
	
} // class Filme
