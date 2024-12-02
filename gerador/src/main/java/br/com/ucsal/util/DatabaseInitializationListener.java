package br.com.ucsal.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class DatabaseInitializationListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(DatabaseInitializationListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DatabaseUtil.initializeDatabase();
            logger.info("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao inicializar banco de dados", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DatabaseUtil.closeConnection();
            logger.info("Conexão com o banco de dados fechada.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao fechar conexão", e);
        }
    }
}