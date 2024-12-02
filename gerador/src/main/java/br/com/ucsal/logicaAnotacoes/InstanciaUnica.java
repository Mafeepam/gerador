package br.com.ucsal.logicaAnotacoes;

import br.com.ucsal.anotacoes.Singleton;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InstanciaUnica {

    private static final Logger logger = Logger.getLogger(InstanciaUnica.class.getName());

    @SuppressWarnings("unchecked")
    public static <T> T carregarSingleton(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            try {
                return (T) clazz.getMethod("getInstancia").invoke(null);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao carregar Singleton para a classe: " + clazz.getName(), e);
                throw new RuntimeException("Erro ao carregar Singleton para a classe: " + clazz.getName(), e);
            }
        }
        throw new IllegalArgumentException("A classe não está anotada com @Singleton: " + clazz.getName());
    }
}
