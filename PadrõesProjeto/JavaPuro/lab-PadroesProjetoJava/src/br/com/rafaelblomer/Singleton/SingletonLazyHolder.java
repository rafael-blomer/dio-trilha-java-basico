package br.com.rafaelblomer.Singleton;

public final class SingletonLazyHolder {

    private static class Holder{
        public static final SingletonLazyHolder instancia = new SingletonLazyHolder();
    }

    private SingletonLazyHolder() {
    }

    public static SingletonLazyHolder getInstance() {
        return Holder.instancia;
    }
}
