package br.com.rafaelblomer.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import br.com.rafaelblomer.persistence.converter.OffsetDateTimeConverter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockDAO {
	
	private final  Connection connection;
	
	public void block(final Long cardId, final String reason) throws SQLException {
		var sql = "INSERT INTO BLOCKS(blocked_at, block_reason, card_id) VALUES (?, ?, ?);";
		try (var statement = connection.prepareStatement(sql)) {
			statement.setTimestamp(1, OffsetDateTimeConverter.toTimeStamp(OffsetDateTime.now()));
			statement.setString(2, reason);
			statement.setLong(3, cardId);
			statement.executeUpdate();
		}
	}

	public void unblock(Long id, String reason) throws SQLException{
		var sql = "UPDATE BLOCKS SET unblocked_at = ?, unblock_reason = ? WHERE card_id = ? AND unblock_reason IS NULL;";
		try (var statement = connection.prepareStatement(sql)) {
			statement.setTimestamp(1, OffsetDateTimeConverter.toTimeStamp(OffsetDateTime.now()));
			statement.setString(2, reason);
			statement.setLong(3, id);
			statement.executeUpdate();
		}
	}
}
