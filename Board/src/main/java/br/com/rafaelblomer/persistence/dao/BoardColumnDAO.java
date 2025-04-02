package br.com.rafaelblomer.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.rafaelblomer.dto.BoardColumnDTO;
import br.com.rafaelblomer.persistence.entity.BoardColumnEntity;
import br.com.rafaelblomer.persistence.entity.BoardColumnKindEnum;
import br.com.rafaelblomer.persistence.entity.CardEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardColumnDAO {

	private final Connection connection;
	
	public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
		var sql = "INSERT INTO BOARDS_COLUMNS(name, `order`, kind, board_id) VALUES(?, ?, ?, ?)";
		try(var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, entity.getName());
			statement.setInt(2, entity.getOrder());
			statement.setString(3, entity.getKind().name());
			statement.setLong(4, entity.getBoard().getId());
			int affectedRows = statement.executeUpdate();
	        
	        if (affectedRows > 0) {
	            try (var generatedKeys = statement.getGeneratedKeys()) {
	                if (generatedKeys.next()) 
	                    entity.setId(generatedKeys.getLong(1));
	            }
	        }
			return entity;
		}
	}

	public List<BoardColumnEntity> findByBoardId(Long boardId) throws SQLException{
		var sql = "SELECT id, name, `order`, kind FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`";
		List<BoardColumnEntity> entities = new ArrayList<BoardColumnEntity>();
		try (var statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			var resultSet = statement.getResultSet();
			while(resultSet.next()) {
				var entity = new BoardColumnEntity();
				entity.setId(resultSet.getLong("id"));
				entity.setName(resultSet.getString("name"));
				entity.setOrder(resultSet.getInt("order"));
				entity.setKind(BoardColumnKindEnum.findByName(resultSet.getString("kind")));
				entities.add(entity);
			}
			return entities;
		}
	}
	
	public List<BoardColumnDTO> findByBoardIdWithDetails(Long boardId) throws SQLException{
		var sql = "SELECT bc.id, bc.name, bc.kind, (SELECT COUNT(c.id) FROM CARDS c WHERE c.board_column_id = bc.id) cards_amount FROM BOARDS_COLUMNS bc WHERE board_id = ? ORDER BY `order`;";
		List<BoardColumnDTO> dtos = new ArrayList<BoardColumnDTO>();
		try (var statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			var resultSet = statement.getResultSet();
			while(resultSet.next()) {
				var dto = new BoardColumnDTO(
						resultSet.getLong("bc.id"),
						resultSet.getString("bc.name"),
						BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")),
						resultSet.getInt("cards_amount")
						);
				dtos.add(dto);
			}
			return dtos;
		}
	}
	
	public Optional<BoardColumnEntity> findById(Long boardId) throws SQLException{
		var sql = "SELECT bc.name, bc.kind, c.id, c.title, c.description FROM BOARDS_COLUMNS bc LEFT JOIN CARDS c ON c.board_column_id = bc.id WHERE bc.id = ?";
		try (var statement = connection.prepareStatement(sql)) {
			statement.setLong(1, boardId);
			statement.executeQuery();
			var resultSet = statement.getResultSet();
			if(resultSet.next()) {
				var entity = new BoardColumnEntity();
				entity.setName(resultSet.getString("bc.name"));
				entity.setKind(BoardColumnKindEnum.findByName(resultSet.getString("bc.kind")));
				do {
					if(Objects.isNull(resultSet.getString("c.title"))) {
						break;
					}
					var card = new CardEntity();
					card.setId(resultSet.getLong("c.id"));
					card.setTitle(resultSet.getString("c.title"));
					card.setDescription(resultSet.getString("c.description"));
					entity.getCards().add(card);
				}while(resultSet.next());
				return Optional.of(entity);
			}
			return Optional.empty();
		}
	}
}
