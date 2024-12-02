package br.com.ucsal.controller;

import java.io.IOException;

import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/view/*", "/"}) // Mapeamento para capturar qualquer caminho dentro de "/view"
public class ProdutoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Map<String, Command> commands;

    @SuppressWarnings("unchecked")
	@Override
    public void init() throws ServletException {
        this.commands = (Map<String, Command>) getServletContext().getAttribute("commands");
        if (this.commands == null) {
            System.out.println("Mapa de comandos não encontrado no contexto.");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pega o caminho completo da requisição (ex: /seu-app/view/adicionarProdutos)
        String requestURI = request.getRequestURI();
        
        // Pega o contexto da aplicação (ex: /seu-app)
        String contextPath = request.getContextPath();

        // Remove o contexto da URL para obter apenas o caminho da requisição após o contexto
        String path = requestURI.substring(contextPath.length());
        
        // Se o caminho estiver vazio ou for apenas "/"
        if (path == null || path.equals("/")) {
            path = "/listarProdutos"; // Redireciona para a página de listagem
        }

        System.out.println("Caminho solicitado: " + path);

        //verifica e executa o comando com base no caminho
        @SuppressWarnings("unchecked")
		Map<String, Command> commands = (Map<String, Command>) request.getServletContext().getAttribute("commands");

        Command command = commands.get(path);

        if (command != null) {
            command.execute(request, response);
        } else {
            System.out.println("Comando não encontrado para o caminho: " + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada");
        }
    }


}

