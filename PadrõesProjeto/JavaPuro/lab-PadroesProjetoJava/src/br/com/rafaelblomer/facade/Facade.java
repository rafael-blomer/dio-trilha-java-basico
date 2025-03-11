package br.com.rafaelblomer.facade;

import br.com.rafaelblomer.facade.subsistema1.crm.CrmService;
import br.com.rafaelblomer.facade.subsistema2.cep.CepApi;

public class Facade {

    public void migrarCliente(String nome, String cep) {
        String cidade = CepApi.getInstance().recuperarCidade(cep);
        String uf = CepApi.getInstance().recuperarEstado(cep);

        CrmService.gravarCliente(nome, cep, cidade, uf);
    }
}
