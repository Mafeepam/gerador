package br.com.ucsal.util;

import br.com.ucsal.anotacoes.Singleton;

public class InstanciaUnica {
	

    @SuppressWarnings("unchecked")
    public static <T> T carregarSingleton(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            try {
                return (T) clazz.getMethod("getInstancia").invoke(null);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao carregar Singleton", e);
            }
        }
        throw new IllegalArgumentException("A classe não está anotada com @Singleton");
    }

}
