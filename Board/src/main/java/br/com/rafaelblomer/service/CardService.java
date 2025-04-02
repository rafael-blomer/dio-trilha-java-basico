package br.com.rafaelblomer.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.rafaelblomer.dto.BoardColumnInfoDTO;
import br.com.rafaelblomer.exception.CardBlockedException;
import br.com.rafaelblomer.exception.CardFinishedException;
import br.com.rafaelblomer.exception.EntityNotFoundException;
import br.com.rafaelblomer.persistence.dao.BlockDAO;
import br.com.rafaelblomer.persistence.dao.CardDAO;
import br.com.rafaelblomer.persistence.entity.BoardColumnKindEnum;
import br.com.rafaelblomer.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardService {

	private final Connection connection;
	
	public CardEntity insert(final CardEntity entity) throws SQLException {
		try {
			var dao = new CardDAO(connection);
			dao.insert(entity);
			connection.commit();
			return entity;
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}
	
	public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			var dao = new CardDAO(connection);
			var optional = dao.findById(cardId);
			var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
			if(dto.blocked()) 
				throw new CardBlockedException("O card %s está bloqueado, é necessário desbloquea-lo para mover".formatted(cardId));
			var currentColumn = boardColumnsInfo.stream()
					.filter(bc -> bc.id().equals(dto.columnId()))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("O card informado pertence a outro Board"));
			if (currentColumn.kind().equals(BoardColumnKindEnum.FINAL)) 
				throw new CardFinishedException("O card já foi finalizado");
			var nextColumn = boardColumnsInfo.stream()
				.filter(bc -> bc.order() == currentColumn.order() + 1)
				.findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
			dao.moveToColumn(nextColumn.id(), cardId);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}
	
	public void cancel(final Long cardId, final Long cancelColumnId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			var dao = new CardDAO(connection);
			var optional = dao.findById(cardId);
			var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
			if(dto.blocked()) 
				throw new CardBlockedException("O card %s está bloqueado, é necessário desbloquea-lo para mover".formatted(cardId));
			var currentColumn = boardColumnsInfo.stream()
					.filter(bc -> bc.id().equals(dto.columnId()))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("O card informado pertence a outro Board"));
			if (currentColumn.kind().equals(BoardColumnKindEnum.FINAL)) 
				throw new CardFinishedException("O card já foi finalizado");
			boardColumnsInfo.stream()
				.filter(bc -> bc.order() == currentColumn.order() + 1)
				.findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
			dao.moveToColumn(cancelColumnId, cardId);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}
	
	public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
		try {
			var dao = new CardDAO(connection);
			var optional = dao.findById(id);
			var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id)));
			if(dto.blocked()) 
				throw new CardBlockedException("O card %s já está bloqueado".formatted(id));
			var currentColumn = boardColumnsInfo.stream()
					.filter(bc -> bc.id().equals(dto.columnId()))
					.findFirst().orElseThrow();
			if(currentColumn.kind().equals(BoardColumnKindEnum.FINAL) || currentColumn.kind().equals(BoardColumnKindEnum.CANCEL))
				throw new IllegalStateException("O card está em uma coluna do tipo %s e não pode ser bloqueado".formatted(currentColumn.kind()));
			var blockDAO = new BlockDAO(connection);
			blockDAO.block(id, reason);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}
	
	public void unblock(final Long id, final String reason) throws SQLException {
		try {
			var dao = new CardDAO(connection);
			var optional = dao.findById(id);
			var dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id)));
			if(!dto.blocked()) 
				throw new CardBlockedException("O card %s já está desbloqueado".formatted(id));
			var blockDAO = new BlockDAO(connection);
			blockDAO.unblock(id, reason);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}
}
