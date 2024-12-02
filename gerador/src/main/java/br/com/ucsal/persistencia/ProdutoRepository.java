package br.com.ucsal.persistencia;

import java.util.List;

public interface ProdutoRepository<T, ID> {
    void adicionar(T entidade);
    void remover(ID id);
    List<T> listar();
    void atualizar(T entidade);
    T obterPorID(ID id);
}
