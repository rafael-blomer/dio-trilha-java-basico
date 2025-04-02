package br.com.rafaelblomer.ui;

import java.sql.SQLException;
import java.util.Scanner;

import br.com.rafaelblomer.dto.BoardColumnInfoDTO;
import br.com.rafaelblomer.persistence.config.ConnectionConfig;
import br.com.rafaelblomer.persistence.entity.BoardColumnEntity;
import br.com.rafaelblomer.persistence.entity.BoardEntity;
import br.com.rafaelblomer.persistence.entity.CardEntity;
import br.com.rafaelblomer.service.BoardColumnQueyService;
import br.com.rafaelblomer.service.BoardQueryService;
import br.com.rafaelblomer.service.CardQueryService;
import br.com.rafaelblomer.service.CardService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {

	private final BoardEntity entity;
	private final Scanner sc = new Scanner(System.in);

	public void execute() {
		try {
			System.out.printf("Bem vindo ao board %s, selecione a operação desejada\n", entity.getId());
			var option = -1;
			while (option != 9) {
				System.out.println("1 - criar um card");
				System.out.println("2 - mover um card");
				System.out.println("3 - bloquear um card");
				System.out.println("4 - desbloquear um card");
				System.out.println("5 - cancelar um card");
				System.out.println("6 - ver board");
				System.out.println("7 - ver coluna com cards");
				System.out.println("8 - ver card");
				System.out.println("9 - voltar para o menu anterior");
				System.out.println("10 - sair");
				option = sc.nextInt();
				switch (option) {
				case 1:
					createCard();
					break;
				case 2:
					moveCardToNextColumn();
					break;
				case 3:
					blockCard();
					break;
				case 4:
					unblockCard();
					break;
				case 5:
					cancelCard();
					break;
				case 6:
					showBoard();
					break;
				case 7:
					showColumn();
					break;
				case 8:
					showCard();
					break;
				case 9:
					System.out.println("Voltando para o menu anterior");
					break;
				case 10:
					System.exit(0);
				default:
					System.out.println("Opção inválida. Digite uma opção do menu.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	private void createCard() throws SQLException{
		var card = new CardEntity();
		System.out.println("Informe o título do card");
		card.setTitle(sc.next());
		System.out.println("Informe a descrição do card");
		card.setDescription(sc.next());
		card.setBoardColumn(entity.getInitialColumn());
		try(var connection = ConnectionConfig.getConnection()) {
			new CardService(connection).insert(card);
		}
	}

	private void moveCardToNextColumn() throws SQLException {
		System.out.println("Informe o id do card que deseja mover para a próxima coluna");
		var idCard = sc.nextLong();
		var boardColumnsInfo = entity.getBoardColumns().stream()
				.map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
				.toList();
		try(var connection = ConnectionConfig.getConnection()) {
			new CardService(connection).moveToNextColumn(idCard, boardColumnsInfo);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void blockCard() throws SQLException{
		System.out.println("Informe o id do Card que será bloqueado");
		var cardId = sc.nextLong();
		System.out.println("Informe o motivo do bloqueio");
		var reason = sc.next();
		var boardColumnsInfo = entity.getBoardColumns().stream()
				.map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
				.toList();
		try(var connection = ConnectionConfig.getConnection()) {
			new CardService(connection).block(cardId, reason, boardColumnsInfo);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void unblockCard() throws SQLException{
		System.out.println("Informe o id do Card que será desbloqueado");
		var cardId = sc.nextLong();
		System.out.println("Informe o motivo do desbloqueio");
		var reason = sc.next();
		try(var connection = ConnectionConfig.getConnection()) {
			new CardService(connection).unblock(cardId, reason);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void cancelCard() throws SQLException {
		System.out.println("Informe o id do card que deseja mover para a coluna de cancelamento");
		var idCard = sc.nextLong();
		var cancelColumn = entity.getCancelColumn();
		var boardColumnsInfo = entity.getBoardColumns().stream()
				.map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
				.toList();
		try(var connection = ConnectionConfig.getConnection()) {
			new CardService(connection).cancel(idCard, cancelColumn.getId(),boardColumnsInfo);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	private void showBoard() throws SQLException {
		try (var connection = ConnectionConfig.getConnection()) {
			var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
			optional.ifPresent(b -> {
				System.out.printf("Board [%s, %s]\n", b.id(), b.name());
				b.columns().forEach(c -> System.out.printf("Coluna [%s] tipo: [%s] tem %s cards\n", c.name(), c.kind(),
						c.cardsAmount()));
			});
		}
	}

	private void showColumn() throws SQLException {
		var columnsIds = entity.getBoardColumns().stream().map(BoardColumnEntity::getId).toList();
		var selectedColumn = -1L;
		while (!columnsIds.contains(selectedColumn)) {
			System.out.printf("Escolha uma coluna do board %s\n", entity.getName());
			entity.getBoardColumns()
					.forEach(c -> System.out.printf("%s - %s - [%s]\n", c.getId(), c.getName(), c.getKind()));
			selectedColumn = sc.nextLong();
		}
		try (var connection = ConnectionConfig.getConnection()) {
			var column = new BoardColumnQueyService(connection).findByID(selectedColumn);
			column.ifPresent(co -> {
				System.out.printf("Coluna %s tipo %s\n", co.getName(), co.getKind());
				co.getCards().forEach(ca -> System.out.printf("Card %s - %s\nDescrição: %s\n", ca.getId(), ca.getTitle(),
						ca.getDescription()));
			});
		}
	}

	private void showCard() throws SQLException {
		System.out.println("Informe o id do card que deseja visualizar");
		var selectedCardId = sc.nextLong();
		try (var connection = ConnectionConfig.getConnection()) {
			new CardQueryService(connection).findByID(selectedCardId).ifPresentOrElse(c -> {
				System.out.printf("Card %s - %s.\n", c.id(), c.title());
				System.out.printf("Descrição: %s\n", c.description());
				System.out.println(c.blocked() ? "Está bloqueado. Motivo: " + c.blockReason() : "Não está bloqueado");
				System.out.printf("Já foi bloqueado %s vezes\n", c.blocksAmount());
				System.out.printf("Está no momento na coluna %s - %s\n", c.columnId(), c.columnName());
			}, 
			() -> System.out.printf("Não existe um card com o id |%s\n", selectedCardId));
		}
	}
}
