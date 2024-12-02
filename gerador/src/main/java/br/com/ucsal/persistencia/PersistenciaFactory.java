package br.com.ucsal.persistencia;

import br.com.ucsal.model.Produto;

public class PersistenciaFactory {

    public static ProdutoRepository<Produto, Integer> getProdutoRepository(int tipo) {
        switch (tipo) {
            case 0: // HSQLDB
                return new HSQLProdutoRepository();
            case 1: // Memória
                return MemoriaProdutoRepository.getInstancia();
            default:
                throw new IllegalArgumentException("Tipo de repositório desconhecido: " + tipo);
        }
    }
}