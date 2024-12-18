package ru.ilyin.core.oc;

import ru.ilyin.core.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
