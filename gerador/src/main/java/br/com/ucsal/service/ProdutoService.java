package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Singleton;
import br.com.ucsal.model.Produto;
import br.com.ucsal.persistencia.PersistenciaFactory;
import br.com.ucsal.persistencia.ProdutoRepository;

@Singleton
public class ProdutoService {
	
	@Inject
	private ProdutoRepository<Produto, Integer> produtoRepository;

	public ProdutoService(ProdutoRepository<Produto, Integer> produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
 
	@SuppressWarnings("unchecked")
	public ProdutoService() {
		// Aqui você pode inicializar com uma fábrica ou outro mecanismo de injeção.
		this.produtoRepository = (ProdutoRepository<Produto, Integer>) PersistenciaFactory.getProdutoRepository(PersistenciaFactory.MEMORIA); // Exemplo de uso da fábrica
	}

	public void adicionarProduto(String nome, double preco) {
		Produto produto = new Produto(null, nome, preco);
		produtoRepository.adicionar(produto);
	}

	public void removerProduto(Integer id) {
		produtoRepository.remover(id);
	}

	public Produto obterProdutoPorId(Integer id) {
		return produtoRepository.obterPorID(id);
	}
 
	public void atualizarProduto(Produto p) {
		produtoRepository.atualizar(p);
	}

	public List<Produto> listarProdutos() {
		return produtoRepository.listar();
	}
}

