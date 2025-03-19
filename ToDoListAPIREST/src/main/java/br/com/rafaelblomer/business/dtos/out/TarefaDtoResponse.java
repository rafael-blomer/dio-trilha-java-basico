package br.com.rafaelblomer.business.dtos.out;

import lombok.Builder;

public record TarefaDtoResponse(String titulo, String descricao, Boolean concluida) {

}
