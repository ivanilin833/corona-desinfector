package ru.ilyin.recomendators.impls;


import ru.ilyin.core.annotations.InjectProperty;
import ru.ilyin.core.annotations.Singleton;
import ru.ilyin.recomendators.Recommendator;

@Singleton
public class RecommendatorImpl implements Recommendator {
    @InjectProperty(value = "wiski")
    private String alcohol;

    public RecommendatorImpl() {
        System.out.println("Recomemndator was created");
    }


    @Override
    public void recommend() {
        System.out.println("to protect from covid 2019 drink " + alcohol);
    }
}
