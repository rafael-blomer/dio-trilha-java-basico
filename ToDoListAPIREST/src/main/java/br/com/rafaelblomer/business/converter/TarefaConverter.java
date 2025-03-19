package br.com.rafaelblomer.business.converter;

import br.com.rafaelblomer.business.dtos.in.TarefaDtoRequest;
import br.com.rafaelblomer.business.dtos.out.TarefaDtoResponse;
import br.com.rafaelblomer.infrastructure.entities.Tarefa;
import org.springframework.stereotype.Component;

@Component
public class TarefaConverter {

    public Tarefa converterParaTarefaEntity(TarefaDtoRequest dtoRequest) {
        return Tarefa.builder()
                .titulo(dtoRequest.titulo())
                .descricao(dtoRequest.descricao())
                .concluida(false)
                .build();
    }

    public TarefaDtoResponse converterParaTarefaDtoResponse(Tarefa tarefa) {
        return new TarefaDtoResponse(tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getConcluida());
    }
}
