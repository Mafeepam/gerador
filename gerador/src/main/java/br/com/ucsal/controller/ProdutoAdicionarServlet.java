package br.com.ucsal.controller;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.util.Injector;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Rota("/adicionarProdutos")
public class ProdutoAdicionarServlet implements Command {

    private static final Logger logger = Logger.getLogger(ProdutoAdicionarServlet.class.getName());

    @Inject
    private ProdutoService produtoService;

    public ProdutoAdicionarServlet() {
        Injector.injectDependencies(this);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            doPost(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método não suportado.");
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp");
        dispatcher.forward(request, response);
    }

 private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            String precoStr = request.getParameter("preco");
            if (nome == null || nome.isBlank() || precoStr == null || precoStr.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome e preço são obrigatórios.");
                return;
            }
            double preco = Double.parseDouble(precoStr);
            produtoService.adicionarProduto(nome, preco);
            response.sendRedirect(request.getContextPath() + "/listarProdutos");
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Preço inválido.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Preço inválido.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao adicionar produto.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao adicionar produto.");
        }
    }
}