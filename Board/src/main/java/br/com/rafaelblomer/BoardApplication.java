package br.com.rafaelblomer;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.rafaelblomer.persistence.config.ConnectionConfig;
import br.com.rafaelblomer.persistence.migration.MigrationStrategy;
import br.com.rafaelblomer.ui.MainMenu;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) throws SQLException{
		try(var connection = ConnectionConfig.getConnection()){
			new MigrationStrategy(connection).executeMigration();
		}
		new MainMenu().execute();
		SpringApplication.run(BoardApplication.class, args);
	}

}
