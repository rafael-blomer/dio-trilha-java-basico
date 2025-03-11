package br.com.rafaelblomer;

import br.com.rafaelblomer.Singleton.SingletonEager;
import br.com.rafaelblomer.Singleton.SingletonLazy;
import br.com.rafaelblomer.Singleton.SingletonLazyHolder;
import br.com.rafaelblomer.Strategy.*;
import br.com.rafaelblomer.facade.Facade;

public class Main {
    public static void main(String[] args) {

        //Singleton
        System.out.println("\nSingleton\n");

        SingletonLazy lazy = SingletonLazy.getInstance();
        System.out.println(lazy);
        lazy = SingletonLazy.getInstance();
        System.out.println(lazy);
        
        SingletonEager eager = SingletonEager.getInstance();
        System.out.println(eager);
        eager = SingletonEager.getInstance();
        System.out.println(eager);

        SingletonLazyHolder holder = SingletonLazyHolder.getInstance();
        System.out.println(holder);
        holder = SingletonLazyHolder.getInstance();
        System.out.println(holder);

        System.out.println("\n================================");

        //Strategy
        System.out.println("\nStrategy\n");

        Comportamento normal = new ComportamentoNormal();
        Comportamento agressivo = new ComportamentoAgressivo();
        Comportamento defensivo = new ComportamentoDefensivo();

        Robo robo = new Robo();
        robo.setStrategy(normal);
        robo.mover();
        robo.setStrategy(agressivo);
        robo.mover();
        robo.setStrategy(defensivo);
        robo.mover();

        System.out.println("\n================================");

        //Facade
        System.out.println("\nFacade\n");

        Facade facade = new Facade();
        facade.migrarCliente("Rafael", "14902600");
    }
}