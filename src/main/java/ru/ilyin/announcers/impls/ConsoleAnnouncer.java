package ru.ilyin.announcers.impls;

import ru.ilyin.announcers.Announcer;
import ru.ilyin.recomendators.Recommendator;
import ru.ilyin.core.annotations.InjectByType;

public class ConsoleAnnouncer implements Announcer {
    @InjectByType
    private Recommendator recommendator;
    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
