package br.com.ucsal.util;

import br.com.ucsal.anotacoes.Inject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Injector {

    private static final Map<Class<?>, Object> singletons = new HashMap<>();

    public static void injectDependencies(Object instance) {
        Class<?> clazz = instance.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                try {
                    field.setAccessible(true);
                    Object dependency = createInstance(field.getType());
                    field.set(instance, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Erro ao injetar dependência", e);
                }
            }
        }
    }

    private static Object createInstance(Class<?> clazz) {
        try {
            if (singletons.containsKey(clazz)) {
                return singletons.get(clazz);
            }

            Object instance = clazz.getDeclaredConstructor().newInstance();
            singletons.put(clazz, instance);
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar instância da classe " + clazz.getName(), e);
        }
    }
}