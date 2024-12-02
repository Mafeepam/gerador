package br.com.ucsal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/view/*", "/"})
public class ProdutoController extends HttpServlet {

    private static final long serialVersionUID = 1L; // Adicionado serialVersionUID
    private static final Logger logger = Logger.getLogger(ProdutoController.class.getName());
    private Map<String, Command> commands;

    @Override
    @SuppressWarnings("unchecked") // Para suprimir o aviso de cast não verificado
    public void init() throws ServletException {
        Object commandObject = getServletContext().getAttribute("commands");
        if (commandObject instanceof Map) {
            this.commands = (Map<String, Command>) commandObject;
        } else {
            throw new ServletException("Mapa de comandos inválido ou não encontrado no contexto.");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            path = "/listarProdutos"; // Rota padrão
        }

        Command command = commands.get(path);
        if (command != null) {
            try {
                command.execute(request, response);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao executar comando", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
            }
        } else {
            logger.warning("Rota não encontrada: " + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada.");
        }
    }
}