package ru.ilyin.core.oc;

import lombok.SneakyThrows;
import ru.ilyin.core.ApplicationContext;
import ru.ilyin.core.annotations.InjectProperty;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> propertiesMap;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        URI path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("application.properties")).toURI();
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            propertiesMap = lines.map(line -> line.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
        }
    }

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        Class<?> implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty injectProperty = field.getAnnotation(InjectProperty.class);
            if (injectProperty != null) {
                String value = injectProperty.value().isEmpty() ? propertiesMap.get(field.getName()) : propertiesMap.get(injectProperty.value());
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
