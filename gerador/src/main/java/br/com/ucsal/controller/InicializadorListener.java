package br.com.ucsal.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.reflections.Reflections;

import br.com.ucsal.anotacoes.Rota;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebListener
public class InicializadorListener implements ServletContextListener {

    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando rotas...");

        try {
            Reflections reflections = new Reflections("br.com.ucsal.controller");
            Set<Class<?>> classesAnotadas = reflections.getTypesAnnotatedWith(Rota.class);

            if (classesAnotadas.isEmpty()) {
                System.out.println("Nenhuma classe anotada com @Rota foi encontrada.");
            }

            for (Class<?> classe : classesAnotadas) {
                System.out.println("Classe anotada encontrada: " + classe.getName());
                Rota rota = classe.getAnnotation(Rota.class);

                if (!Command.class.isAssignableFrom(classe)) {
                    throw new IllegalArgumentException("A classe " + classe.getName() + " não implementa Command.");
                }

                Command commandInstance = (Command) classe.getDeclaredConstructor().newInstance();
                commands.put(rota.value(), commandInstance);
                System.out.println("Rota registrada: " + rota.value() + " -> " + classe.getName());
            }

            // Armazena o mapa de comandos no contexto da aplicação
            sce.getServletContext().setAttribute("commands", commands);
            System.out.println("Rotas registradas com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar rotas: " + e.getMessage(), e);
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        commands.clear();
        System.out.println("Aplicação encerrada. Rotas limpas.");
    }
}
