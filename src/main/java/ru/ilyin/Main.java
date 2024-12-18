package ru.ilyin;

import ru.ilyin.entity.Room;
import ru.ilyin.core.ApplicationContext;
import ru.ilyin.policemans.Policeman;
import ru.ilyin.policemans.impls.AngryPoliceman;
import ru.ilyin.policemans.impls.PolicemanImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = CoronaDesinfector.Application.run("ru.ilyin", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        CoronaDesinfector coronaDesinfector = context.getObject(CoronaDesinfector.class);
        System.out.println(coronaDesinfector.getClass());
        coronaDesinfector.start(new Room());
    }
}
