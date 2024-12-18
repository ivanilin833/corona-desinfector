package ru.ilyin.policemans.impls;

import ru.ilyin.recomendators.Recommendator;
import ru.ilyin.core.annotations.InjectByType;
import ru.ilyin.policemans.Policeman;

import javax.annotation.PostConstruct;

public class AngryPoliceman implements Policeman {
    @InjectByType
    private Recommendator recommendator;
    @PostConstruct
    public void init(){
        System.out.println(recommendator.getClass().getName());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Kill all!! Leave room");
    }
}
