package ru.ilyin.core;

import lombok.Getter;
import lombok.Setter;
import ru.ilyin.core.annotations.Singleton;
import ru.ilyin.core.configurations.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// используется для загрузки настроенного класса
public class ApplicationContext {
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();
    @Setter
    private ObjectFactory factory;
    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> clazz) {
        if (cache.containsKey(clazz)) {
            return (T) cache.get(clazz);
        }
        Class<? extends T> implClass = clazz;

        if (clazz.isInterface()) {
            implClass = config.getImplClass(clazz);
        }

        T t = factory.createObject(implClass);
        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(clazz, t);
        }
        return t;
    }
}
