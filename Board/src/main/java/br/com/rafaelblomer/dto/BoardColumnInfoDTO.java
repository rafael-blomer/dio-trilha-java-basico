package br.com.rafaelblomer.dto;

import br.com.rafaelblomer.persistence.entity.BoardColumnKindEnum;

public record BoardColumnInfoDTO (Long id, int order, BoardColumnKindEnum kind){

}
