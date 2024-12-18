package ru.ilyin.core;

import lombok.SneakyThrows;
import ru.ilyin.core.oc.ObjectConfigurator;
import ru.ilyin.core.pc.ProxyConfiguration;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// указываем как создавать классы
public class ObjectFactory {
    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();
    private final List<ProxyConfiguration> proxyConfigurations = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfiguration> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfiguration.class)) {
            proxyConfigurations.add(aClass.getDeclaredConstructor().newInstance());
        }
    }


    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T createObject(Class<T> implClass) {
        T t = implClass.getDeclaredConstructor().newInstance();

        for (ObjectConfigurator configurator : configurators) {
            configurator.configure(t, context);
        }

        for (Method method : t.getClass().getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
        for (ProxyConfiguration proxyConfiguration : proxyConfigurations) {
            t = (T) proxyConfiguration.replaceWithProxyIfNeeded(t, implClass);
        }

        return t;
    }
}
