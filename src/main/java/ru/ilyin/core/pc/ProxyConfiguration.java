package ru.ilyin.core.pc;

public interface ProxyConfiguration {
    Object replaceWithProxyIfNeeded(Object t, Class<?> implClass);
}
