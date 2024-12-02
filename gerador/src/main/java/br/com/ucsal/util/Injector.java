package br.com.ucsal.util;

import java.lang.reflect.Field;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.persistencia.PersistenciaFactory;

public class Injector {
	
	public static void injectDependencies(Object target, int repositoryType) {
	    Class<?> clazz = target.getClass();
	    for (Field field : clazz.getDeclaredFields()) {
	        if (field.isAnnotationPresent(Inject.class)) {
	            field.setAccessible(true);
	            try {
	                // Define o repositório a ser injetado
	                Object repository = PersistenciaFactory.getProdutoRepository(repositoryType);
	                field.set(target, repository);
	                System.out.println("Dependência injetada: " + field.getName());
	            } catch (IllegalAccessException e) {
	                throw new RuntimeException("Erro ao injetar dependências", e);
	            }
	        }
	    }
	}
}
