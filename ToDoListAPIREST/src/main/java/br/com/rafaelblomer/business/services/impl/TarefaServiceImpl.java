package br.com.rafaelblomer.business.services.impl;

import br.com.rafaelblomer.business.converter.TarefaConverter;
import br.com.rafaelblomer.business.dtos.in.TarefaDtoRequest;
import br.com.rafaelblomer.business.dtos.out.TarefaDtoResponse;
import br.com.rafaelblomer.business.exceptions.EntityNotFoundException;
import br.com.rafaelblomer.business.services.ITarefaService;
import br.com.rafaelblomer.infrastructure.entities.Tarefa;
import br.com.rafaelblomer.infrastructure.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements ITarefaService {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private TarefaConverter converter;


    @Override
    public TarefaDtoResponse criarTarefa(TarefaDtoRequest dtoRequest) {
        Tarefa entity = converter.converterParaTarefaEntity(dtoRequest);
        return converter.converterParaTarefaDtoResponse(repository.save(entity));
    }

    @Override
    public TarefaDtoResponse buscarUmaTarefa(Long id) {
        Tarefa entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        return converter.converterParaTarefaDtoResponse(entity);
    }

    @Override
    public List<TarefaDtoResponse> buscarTodasTarefas() {
        return repository.findAll()
                .stream()
                .map(converter::converterParaTarefaDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TarefaDtoResponse alterarStatusTarefa(Long id) {
        Tarefa entity = buscarTarefaEntity(id);
        entity.setConcluida(!entity.getConcluida());
        return converter.converterParaTarefaDtoResponse(repository.save(entity));
    }

    @Override
    public TarefaDtoResponse alterarDadosTarefa(Long id, TarefaDtoRequest dtoRequest) {
        Tarefa entity = buscarTarefaEntity(id);
        atualizarDadosEntity(entity, dtoRequest);
        return converter.converterParaTarefaDtoResponse(repository.save(entity));
    }

    @Override
    public void excluirTarefa(Long id) {
        repository.delete(buscarTarefaEntity(id));
    }

    private void atualizarDadosEntity(Tarefa entity, TarefaDtoRequest dtoRequest) {
        entity.setTitulo(dtoRequest.titulo());
        entity.setDescricao(dtoRequest.descricao());
    }

    private Tarefa buscarTarefaEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
    }
}
