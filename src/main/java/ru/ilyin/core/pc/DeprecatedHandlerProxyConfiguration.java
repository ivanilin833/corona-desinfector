package ru.ilyin.core.pc;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

public class DeprecatedHandlerProxyConfiguration implements ProxyConfiguration {
    @Override
    @SneakyThrows
    public Object replaceWithProxyIfNeeded(Object t, Class<?> implClass) {
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            if (implClass.getInterfaces().length == 0) {
                try (var dynamicClass = new ByteBuddy()
                        .subclass(implClass)
                        .method(isDeclaredBy(implClass))
                        .intercept(MethodDelegation.to(new MyInterceptor(t)))
                        .make()) {
                    return dynamicClass
                            .load(implClass.getClassLoader())
                            .getLoaded()
                            .getDeclaredConstructor()
                            .newInstance();
                }
            }
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), ((proxy, method, args) -> {
                System.out.println("******** что ты делаешь урод!!!!");
                return method.invoke(t, args);
            }));
        } else
            return t;
    }

    public static class MyInterceptor {
        private final Object target; // Оригинальный объект

        public MyInterceptor(Object target) {
            this.target = target;
        }

        @RuntimeType
        public Object intercept(@Origin Method method, @AllArguments Object[] args) throws InvocationTargetException, IllegalAccessException {
            System.out.println("******** что ты делаешь урод!!!!");
            return method.invoke(target, args); // Вызов оригинального метода
        }
    }
}
