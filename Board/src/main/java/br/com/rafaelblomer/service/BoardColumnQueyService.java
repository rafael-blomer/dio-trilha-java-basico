package br.com.rafaelblomer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.rafaelblomer.persistence.dao.BoardColumnDAO;
import br.com.rafaelblomer.persistence.entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnQueyService {

	private final Connection connection;
	
	public Optional<BoardColumnEntity> findByID(final Long id) throws SQLException {
		var dao = new BoardColumnDAO(connection);
		return dao.findById(id);
	}
}
