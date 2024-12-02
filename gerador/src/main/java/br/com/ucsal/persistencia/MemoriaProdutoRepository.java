package br.com.ucsal.persistencia;

import br.com.ucsal.anotacoes.Singleton;
import br.com.ucsal.model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Singleton
public class MemoriaProdutoRepository implements ProdutoRepository<Produto, Integer> {

    private static final Logger logger = Logger.getLogger(MemoriaProdutoRepository.class.getName());

    private final Map<Integer, Produto> produtos = new HashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(1);
    private static MemoriaProdutoRepository instancia;

    private MemoriaProdutoRepository() {}

    public static synchronized MemoriaProdutoRepository getInstancia() {
        if (instancia == null) {
            instancia = new MemoriaProdutoRepository();
        }
        return instancia;
    }

    @Override
    public void adicionar(Produto entidade) {
        int id = currentId.getAndIncrement();
        entidade.setId(id);
        produtos.put(id, entidade);
        logger.info("Produto adicionado: " + entidade);
    }

    @Override
    public void remover(Integer id) {
        if (produtos.remove(id) != null) {
            logger.info("Produto removido com ID: " + id);
        }
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public void atualizar(Produto entidade) {
        if (produtos.containsKey(entidade.getId())) {
            produtos.put(entidade.getId(), entidade);
        }
    }

    @Override
    public Produto obterPorID(Integer id) {
        return produtos.get(id);
    }
}
