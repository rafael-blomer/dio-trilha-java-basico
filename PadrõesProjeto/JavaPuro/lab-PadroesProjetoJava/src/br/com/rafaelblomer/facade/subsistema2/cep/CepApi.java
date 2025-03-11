package br.com.rafaelblomer.facade.subsistema2.cep;

import br.com.rafaelblomer.Singleton.SingletonEager;

public class CepApi {
    private static final CepApi instancia = new CepApi();

    private CepApi() {
    }

    public static CepApi getInstance() {
        return instancia;
    }

    public String recuperarCidade(String cep) {
        return "Lages";
    }

    public String recuperarEstado(String cep) {
        return "SC";
    }
}
