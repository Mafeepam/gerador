package br.com.ucsal.util;

import br.com.ucsal.persistencia.PersistenciaFactory;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Iniciando o banco de dados HSQLDB...");
        DatabaseUtil.iniciarBanco();
        
        ProdutoService produtoService = new ProdutoService();
        Injector.injectDependencies(produtoService, PersistenciaFactory.MEMORIA);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicação sendo finalizada.");
    }
}