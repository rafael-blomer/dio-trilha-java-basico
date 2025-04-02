package br.com.rafaelblomer.dto;

import br.com.rafaelblomer.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id, String name, BoardColumnKindEnum kind, int cardsAmount) {

}
