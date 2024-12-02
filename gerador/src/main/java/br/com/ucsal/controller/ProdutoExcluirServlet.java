package br.com.ucsal.controller;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.util.Injector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Rota(value = "/excluirProdutos")
public class ProdutoExcluirServlet implements Command {

    private static final Logger logger = Logger.getLogger(ProdutoExcluirServlet.class.getName());

    @Inject
    private ProdutoService produtoService;

    public ProdutoExcluirServlet() {
        Injector.injectDependencies(this);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            Integer id = Integer.parseInt(idParam);

            // Verifique se o produto existe antes de tentar remover
            produtoService.obterProdutoPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            // Chama o método de remoção (não há necessidade de checar retorno booleano)
            produtoService.removerProduto(id);

            response.sendRedirect(request.getContextPath() + "/listarProdutos");
        } catch (IllegalArgumentException e) {
            // Caso o produto não exista
            logger.log(Level.WARNING, "Produto não encontrado", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao excluir produto", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
