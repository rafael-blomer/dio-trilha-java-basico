package br.com.rafaelblomer.persistence.config;

import static lombok.AccessLevel.PRIVATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {
	
	public static Connection getConnection() throws SQLException {
		var url = "jdbc:mysql://localhost/board";
		var user = "root";
		var password = "rafael123";
		var connection = DriverManager.getConnection(url, user, password);
		connection.setAutoCommit(false);
		return connection;
	}

}
