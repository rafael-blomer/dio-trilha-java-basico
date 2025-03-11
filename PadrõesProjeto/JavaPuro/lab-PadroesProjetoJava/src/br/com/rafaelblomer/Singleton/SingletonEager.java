package br.com.rafaelblomer.Singleton;

public final class SingletonEager {

    private static final SingletonEager instancia = new SingletonEager();

    private SingletonEager() {
    }

    public static SingletonEager getInstance() {
        return instancia;
    }
}
