package vjps.filmoteca.modelo;

public class Pessoa {
	
	// Profissões
	public static final String DIRETOR = "Diretor",
			AUTOR = "Autor",
			ARTISTA = "Artista";

	private String nome;
	private String profissao;
	
	public Pessoa() {
	}
	
	public Pessoa(String nome, String profissao) {
		this.nome = nome;
		this.profissao = profissao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", nome, profissao);
	}
	
} // class Pessoa
