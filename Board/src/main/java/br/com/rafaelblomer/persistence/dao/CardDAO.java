package br.com.rafaelblomer.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

import br.com.rafaelblomer.dto.CardDetailsDTO;
import br.com.rafaelblomer.persistence.converter.OffsetDateTimeConverter;
import br.com.rafaelblomer.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardDAO {

	private Connection connection;
	
	public CardEntity insert(final CardEntity entity) throws SQLException {
	    var sql = "INSERT INTO CARDS(title, description, board_column_id) VALUES(?, ?, ?)";
	    try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        statement.setString(1, entity.getTitle());
	        statement.setString(2, entity.getDescription());
	        statement.setLong(3, entity.getBoardColumn().getId());
	        statement.executeUpdate();

	        try (var generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                entity.setId(generatedKeys.getLong(1));
	            }
	        }
	    }
	    return entity;
	}

	public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
	    var sql = "SELECT c.id, c.title, c.description, b.blocked_at, b.block_reason, c.board_column_id, bc.name, " +
	              "(SELECT COUNT(sub_b.id) FROM BLOCKS sub_b WHERE sub_b.card_id = c.id) AS blocks_amount " +
	              "FROM CARDS c " +
	              "LEFT JOIN BLOCKS b ON c.id = b.card_id AND b.unblocked_at IS NULL " +
	              "INNER JOIN BOARDS_COLUMNS bc ON bc.id = c.board_column_id " +
	              "WHERE c.id = ?;";
	    
	    try (var statement = connection.prepareStatement(sql)) {
	        statement.setLong(1, id);
	        try (var rs = statement.executeQuery()) {
	            if (rs.next()) {
	                var dto = new CardDetailsDTO(
	                    rs.getLong("id"),
	                    rs.getString("title"),
	                    rs.getString("description"),
	                    Objects.nonNull(rs.getString("block_reason")),
	                    OffsetDateTimeConverter.toOffsetDateTime(rs.getTimestamp("blocked_at")),
	                    rs.getString("block_reason"),
	                    rs.getInt("blocks_amount"),
	                    rs.getLong("board_column_id"),
	                    rs.getString("name")
	                );
	                return Optional.of(dto);
	            }
	        }
	    }
	    return Optional.empty();
	}
	
	public void moveToColumn(final Long columnId, final Long cardId) throws SQLException {
		var sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?;";
		try(var statement = connection.prepareStatement(sql)) {
			statement.setLong(1, columnId);
			statement.setLong(2, cardId);
			statement.executeUpdate();
		}
	}

}
