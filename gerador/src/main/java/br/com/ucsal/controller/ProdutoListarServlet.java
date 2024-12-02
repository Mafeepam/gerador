package br.com.ucsal.controller;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.model.Produto;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.util.Injector;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Rota(value = "/listarProdutos")
public class ProdutoListarServlet implements Command {

    private static final Logger logger = Logger.getLogger(ProdutoListarServlet.class.getName());

    @Inject
    private ProdutoService produtoService;

    public ProdutoListarServlet() {
        Injector.injectDependencies(this);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            if (produtos.isEmpty()) {
                logger.info("Nenhum produto encontrado.");
            }

            request.setAttribute("produtos", produtos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtolista.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao listar produtos", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao listar produtos: " + e.getMessage());
        }
    }
}