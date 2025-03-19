package br.com.rafaelblomer.business.services;

import br.com.rafaelblomer.business.dtos.in.TarefaDtoRequest;
import br.com.rafaelblomer.business.dtos.out.TarefaDtoResponse;

import java.util.List;

public interface ITarefaService {

    TarefaDtoResponse criarTarefa(TarefaDtoRequest dtoRequest);

    TarefaDtoResponse buscarUmaTarefa(Long id);

    List<TarefaDtoResponse> buscarTodasTarefas();

    TarefaDtoResponse alterarStatusTarefa(Long id);

    TarefaDtoResponse alterarDadosTarefa(Long id, TarefaDtoRequest dtoRequest);

    void excluirTarefa(Long id);

}
