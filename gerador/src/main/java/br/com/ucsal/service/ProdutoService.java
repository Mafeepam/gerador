package br.com.ucsal.service;

import br.com.ucsal.anotacoes.Singleton;
import br.com.ucsal.model.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProdutoService {

    private static ProdutoService instancia;
    private final List<Produto> produtos = new ArrayList<>();
    private int contadorId = 1;

    private ProdutoService() {}

    public static ProdutoService getInstancia() {
        if (instancia == null) {
            synchronized (ProdutoService.class) {
                if (instancia == null) {
                    instancia = new ProdutoService();
                }
            }
        }
        return instancia;
    }

    public void adicionarProduto(String nome, double preco) {
        Produto produto = new Produto(contadorId++, nome, preco);
        produtos.add(produto );
    }

    public void editarProduto(Integer id, String nome, double preco) {
        Produto produto = obterProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        produto.setNome(nome);
        produto.setPreco(preco);
    }

    public Optional<Produto> obterProdutoPorId(Integer id) {
        return produtos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public void removerProduto(Integer id) {
        Produto produto = obterProdutoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        produtos.remove(produto);
    }
}