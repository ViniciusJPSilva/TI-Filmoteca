package vjps.filmoteca.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar uma lista de filmes.
 * @author Vinícius José Pires Silva
 *
 */
public class FilmeList {

	// Lista de filmes
	private List<Filme> filmes;
	
	// Construtor
	public FilmeList() {
		filmes = new ArrayList<>();
	}

	// Get
	public List<Filme> getFilmes() {
		return filmes;
	}

	@Override
	public String toString() {
		return "FilmeList [filmes=" + filmes + "]";
	}

	/**
	 * Adiciona um filmes à lista de filmes.
	 */
	public void addFilme(Filme filme) {
		filmes.add(filme);
	}
	
	/**
	 * Remove um filmes da lista de filmes.
	 */
	public void removeFilme(Filme filme) {
		filmes.remove(filme);
	}
	
	/**
	 * Obtém um filme da lista pelo index.
	 */
	public Filme obterFilmePorId(int index) {
		return filmes.get(index);
	}
	
	/**
	 * Obtém a quantidade de filmes cadastrados.
	 */
	public int quantidadeFilmes() {
		return filmes.size();
	}
	
	
	/**
	 * Realiza uma busca na lista de filmes, procurando: título, autor, diretor ou artistar do elenco.
	 * 
	 * @return Lista com os filmes encontrados.
	 */
	public List<Filme> pesquisarFilmes(String busca){
		
		busca = busca.toLowerCase();
		List<Filme> filmesPesquisa = new ArrayList<>();
		
		
		for(Filme filme : filmes) {
			if(filme.getTitulo().toLowerCase().contains(busca)) { // Verifica o título do Filme.
				filmesPesquisa.add(filme);
				continue;
			}
			
			for(Pessoa pessoa : filme.getPessoas()) // Verifica as pessoas do filme.
				if(pessoa.getNome().toLowerCase().contains(busca)) {
					filmesPesquisa.add(filme);
					break;
				}
		}
		
        return filmesPesquisa;
	} // pesquisarFilmes()
	
} // class FilmeList
