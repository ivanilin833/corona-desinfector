package ru.ilyin.core.configurations;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

// указывает где и как искать реализации интерфейсов
@SuppressWarnings("unchecked")
public class JavaConfig implements Config {
    @Getter
    private final Reflections scanner;
    private final Map<Class<?>, Class<?>> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class<?>, Class<?>> ifc2ImplClass) {
        this.ifc2ImplClass = ifc2ImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return (Class<? extends T>) ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new ClassFormatError(ifc + " has 0 or more than one implementation please update her configuration");
            }
            return classes.iterator().next();
        });
    }
}
