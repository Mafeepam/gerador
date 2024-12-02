package br.com.ucsal.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.reflections.Reflections;
import br.com.ucsal.anotacoes.Rota;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class InicializadorListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(InicializadorListener.class.getName());
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Inicializando rotas...");
        try {
            Reflections reflections = new Reflections("br.com.ucsal.controller");
            Set<Class<?>> classesAnotadas = reflections.getTypesAnnotatedWith(Rota.class);

            for (Class<?> clazz : classesAnotadas) {
                Rota rota = clazz.getAnnotation(Rota.class);
                if (commands.containsKey(rota.value())) {
                    throw new IllegalArgumentException("Conflito de rotas: '" + rota.value() + "' já registrada.");
                }
                if (!Command.class.isAssignableFrom(clazz)) {
                    throw new IllegalArgumentException(clazz.getName() + " não implementa Command.");
                }
                Command commandInstance = (Command) clazz.getDeclaredConstructor().newInstance();
                commands.put(rota.value(), commandInstance);
                logger.info("Rota registrada: " + rota.value() + " -> " + clazz.getName());
            }

            sce.getServletContext().setAttribute("commands", commands);
            logger.info("Rotas registradas com sucesso.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao inicializar rotas", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        commands.clear();
        logger.info("Rotas limpas.");
    }
}
