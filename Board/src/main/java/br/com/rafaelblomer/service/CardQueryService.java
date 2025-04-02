package br.com.rafaelblomer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.rafaelblomer.dto.CardDetailsDTO;
import br.com.rafaelblomer.persistence.dao.CardDAO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardQueryService {

private final Connection connection;
	
	public Optional<CardDetailsDTO> findByID(final Long id) throws SQLException {
		var dao = new CardDAO(connection);
		return dao.findById(id);
	}
}
