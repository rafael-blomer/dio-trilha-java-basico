package br.com.rafaelblomer.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.rafaelblomer.persistence.config.ConnectionConfig;
import br.com.rafaelblomer.persistence.entity.BoardColumnEntity;
import br.com.rafaelblomer.persistence.entity.BoardColumnKindEnum;
import br.com.rafaelblomer.persistence.entity.BoardEntity;
import br.com.rafaelblomer.service.BoardQueryService;
import br.com.rafaelblomer.service.BoardService;

public class MainMenu {

	private final Scanner sc = new Scanner(System.in);

	public void execute() throws SQLException{
		System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada");
		var option = -1;
		while (true) {
			System.out.println("1 - criar novo board");
			System.out.println("2 - selecionar um board existente");
			System.out.println("3 - excluir um board");
			System.out.println("4 - sair");
			option = sc.nextInt();
			switch (option) {
			case 1:
				createBoard();
				break;
			case 2:
				selectBoard();
				break;
			case 3:
				deleteBoard();
				break;
			case 4:
				System.exit(0);
			default:
				System.out.println("Opção inválida. Digite uma opção do menu.");
			}
		}
	}

	private void createBoard() throws SQLException{
		var entity = new BoardEntity();
		System.out.println("Informe o nome do seu board");
		entity.setName(sc.next());
		
		System.out.println("Seu board terá colunas além das 3 padrões? se sim, informe quantas, se não digite 0");
		var additionalColumns = sc.nextInt();
		
		List<BoardColumnEntity> columns = new ArrayList<BoardColumnEntity>();
		
		System.out.println("Informe o nome da coluna inicial do board");
		var initialColumnName = sc.next();
		var initialColumn = createColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
		columns.add(initialColumn);
		
		for(int i = 0; i< additionalColumns; i++) {
			System.out.println("Informe o nome da coluna de tarefa pendente do board");
			var pendingColumnName = sc.next();
			var pendingColumn = createColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
			columns.add(pendingColumn);
		}
		
		System.out.println("Informe o nome da coluna final do board");
		var finalColumnName = sc.next();
		var finalColumn = createColumn(finalColumnName, BoardColumnKindEnum.FINAL, additionalColumns + 1);
		columns.add(finalColumn);
		
		System.out.println("Informe o nome da coluna de cancelamento do board");
		var cancelColumnName = sc.next();
		var cancelColumn = createColumn(cancelColumnName, BoardColumnKindEnum.CANCEL, additionalColumns + 2);
		columns.add(cancelColumn);
		
		entity.setBoardColumns(columns);
		try(var connection = ConnectionConfig.getConnection()){
			var service = new BoardService(connection);
			service.insert(entity);
		}
	}

	private void selectBoard() throws SQLException{
		System.out.println("Informe o ID do Board que deseja selecionar");
		var id = sc.nextLong();
		try( var connection = ConnectionConfig.getConnection()){
			var queryService = new BoardQueryService(connection);
			var optional = queryService.findById(id);
			optional.ifPresentOrElse(
					b -> new BoardMenu(b).execute(),
					() -> System.out.printf("Board com ID = %s não encontrado\n", id)
				);
		}

	}

	private void deleteBoard() throws SQLException{
		System.out.println("Informe o ID do Board que deseja excluir");
		var id = sc.nextLong();
		try(var connection = ConnectionConfig.getConnection()){
			var service = new BoardService(connection);
			if(service.delete(id))
				System.out.printf("Board %s excluido\n", id);
			else
				System.out.printf("Board com ID = %s não encontrado\n", id);
		}
	}

	private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order) {
		var boardColumn = new BoardColumnEntity();
		boardColumn.setName(name);
		boardColumn.setKind(kind);
		boardColumn.setOrder(order);
		return boardColumn;
	}
}
