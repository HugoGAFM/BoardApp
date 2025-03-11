package Board.BoardApp.Terminal;

import Board.BoardApp.Model.*;
import Board.BoardApp.Services.BlockService;
import Board.BoardApp.Services.BoardColumnService;
import Board.BoardApp.Services.BoardService;
import Board.BoardApp.Services.CardService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TerminalMenu {

    private final BoardService boardService;
    private final BoardColumnService boardColumnService;
    private final CardService cardService;
    private final BlockService blockService;
    private final Scanner scanner;

    public TerminalMenu(BoardService boardService, BoardColumnService boardColumnService, CardService cardService, BlockService blockService) {
        this.boardService = boardService;
        this.boardColumnService = boardColumnService;
        this.cardService = cardService;
        this.blockService = blockService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n==== MENU ====");
                System.out.println("1. Criar Board");
                System.out.println("2. Listar Boards resumido");
                System.out.println("3. Deletar Board");
                System.out.println("4. Criar Card");
                System.out.println("5. Atualizar Card");
                System.out.println("6. Deletar Card");
                System.out.println("7. Criar Coluna da Board");
                System.out.println("8. Listar Colunas de um Board");
                System.out.println("9. Gerenciar Blocks");
                System.out.println("10. Sair");
                System.out.print("Escolha uma opção: ");

                int options = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha

                switch (options) {
                    case 1:
                        criarBoard(); // ≤- Cria o board, caso o banco de dados não possua um board, o sistema não funcionará.
                        break;
                    case 2:
                        listarBoards(); // ≤- Lista esses boards.
                        break;
                    case 3:
                        deletarBoard(); // <- Deleta os Boards
                        break;
                    case 4:
                        criarCard();   // <- Cria os cards dos boards.
                        break;
                    case 5:
                        atualizarCard(); // Atualiza esses cards (função não disponível caso esteja desbloqueado!)
                        break;
                    case 6:
                        deletarCard();  // Deleta esses cards (também não disponível caso esteja desbloqueado!)
                        break;
                    case 7:
                        criarBoardColumn(); // Cria uma coluna dentro da board.
                        // caso seja uma coluna inicial, final ou cancelada, só pode ser criada uma dessas por board.
                        break;
                    case 8:
                        listarBoardColumns(); // lista as colunas de UMA board ao qual é inserido um ‘ID’ para detecção, diferente da função abaixo.
                        break;
                    case 9:
                        menuBlock(); // Menu dos blocks
                        break;
                    case 10:
                        System.out.println("Saindo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número válido.");
                scanner.nextLine(); // Limpa a entrada inválida
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void criarBoard() {
        System.out.print("Digite o nome do Board: ");
        String name = scanner.nextLine();


        Board board = new Board(name);


        boardService.createBoard(board);

        System.out.println("Board criado com êxito.");
    }

    private void listarBoards() {
        System.out.println("=== Lista de Boards ===");
        List<Board> boards = boardService.getAllBoardsWithColumnsAndCards();
        for (Board board : boards) {
            System.out.println(board.getId() + " - " + board.getName());

            if (board.getBoardColumns() != null && !board.getBoardColumns().isEmpty()) {
                for (BoardColumn boardColumn : board.getBoardColumns()) {
                    System.out.println("  Colunas: " + boardColumn.getName() + "\n");

                    if (boardColumn.getCards() != null && !boardColumn.getCards().isEmpty()) {
                        System.out.println("    Cards associados:");
                        boardColumn.getCards().forEach(card -> System.out.println("      - " + card.getTitle()));
                    } else {
                        System.out.println("    Nenhum card associado.");
                    }
                }
            } else {
                System.out.println("Nenhuma coluna associada.");
            }
        }
    }

    private void deletarBoard() {
        System.out.print("Digite o ID do Board a ser deletado: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        boardService.deleteBoard(id);
        System.out.println("Board deletado com sucesso!");
    }

    private void criarCard() {
        System.out.print("Digite o título do Card: ");
        String title = scanner.nextLine();
        System.out.print("Digite a descrição do Card: ");
        String description = scanner.nextLine();
        System.out.print("Digite o ID da BoardColumn onde o Card será adicionado: ");
        Long boardColumnId = scanner.nextLong();
        scanner.nextLine();


        BoardColumn boardColumn = boardColumnService.getColumnById(boardColumnId)
                .orElseThrow(() -> new IllegalArgumentException("BoardColumn com ID " + boardColumnId + " não encontrada."));


        Card card = new Card(title, description, boardColumn);


        cardService.createCard(card);

        System.out.println("Card criado com sucesso!");
    }

    private void atualizarCard() {
        System.out.print("Digite o ID do Card a ser atualizado: ");
        Long cardId = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        Optional<Card> optionalCard = cardService.getCardById(cardId);
        if (optionalCard.isEmpty()) {
            System.out.println("Card não encontrado.");
            return;
        }

        if (blockService.isCardBlocked(cardId)) {
            System.out.println("Erro: Este Card está bloqueado e não pode ser atualizado.");
            return;
        }

        Card card = optionalCard.get();
        System.out.print("Digite o novo título do Card (atual: " + card.getTitle() + "): ");
        String novoTitulo = scanner.nextLine();
        System.out.print("Digite a nova descrição do Card (atual: " + card.getDescription() + "): ");
        String novaDescricao = scanner.nextLine();

        Card updatedCard = new Card();
        updatedCard.setTitle(novoTitulo);
        updatedCard.setDescription(novaDescricao);
        updatedCard.setBoardColumn(card.getBoardColumn()); // Mantém a coluna atual

        try {
            cardService.updateCard(cardId, updatedCard);
            System.out.println("Card atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o Card: " + e.getMessage());
        }
    }

    private void deletarCard() {
        System.out.print("Digite o ID do Card a ser deletado: ");
        Long cardId = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        try {
            cardService.deleteCard(cardId);
            System.out.println("Card deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar o Card: " + e.getMessage());
        }
    }

    private void criarBoardColumn() {
        System.out.print("Digite o ID do Board onde a coluna será adicionada: ");
        Long boardId = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha


        System.out.print("Digite o nome da BoardColumn: ");
        String columnName = scanner.nextLine();

        System.out.println("Escolha o tipo da coluna:");
        System.out.println("1. INICIAL");
        System.out.println("2. PENDENTE");
        System.out.println("3. FINAL");
        System.out.println("4. CANCELADO");
        System.out.print("Digite a opção: ");
        int columnKindOption = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        ColumnKind columnKind;
        switch (columnKindOption) {
            case 1:
                columnKind = ColumnKind.INICIAL;
                break;
            case 2:
                columnKind = ColumnKind.PENDENTE;
                break;
            case 3:
                columnKind = ColumnKind.FINAL;
                break;
            case 4:
                columnKind = ColumnKind.CANCELADO;
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        // Salvar a BoardColumn
        boardColumnService.createColumn(boardId, columnName, columnKind);

        System.out.println("BoardColumn criada com sucesso!");
    }

    private void listarBoardColumns() {
        System.out.print("Digite o ID do Board para listar suas colunas: ");
        Long boardId = scanner.nextLong();
        scanner.nextLine();

        Board board = boardService.getBoardById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board com ID " + boardId + " não encontrado."));

        System.out.println("Colunas do Board " + board.getName() + ":");
        List<BoardColumn> columns = boardColumnService.getColumnByBoardId(boardId);
        for (BoardColumn bc : columns) {
            System.out.println("Coluna: " + bc.getName() + " (Tipo: " + bc.getKind() + ")");
            if (bc.getCards() != null && !bc.getCards().isEmpty()) {
                System.out.println("  Cards:");
                for (Card card : bc.getCards()) {
                    System.out.println("    - " + card.getTitle() + ": " + card.getDescription());
                }
            } else {
                System.out.println("  Nenhum card associado nesta coluna.");
            }
        }
    }



    private void menuBlock() {
        try {
            System.out.println("==== Menu de Blocks ====");
            System.out.println("1. Bloquear Card");
            System.out.println("2. Desbloquear Card");
            System.out.println("3. Informar Bloqueio de um Card");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");

            int options = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (options) {
                case 1:
                    bloquearCard();
                    break;
                case 2:
                    desbloquearCard();
                    break;
                case 3:
                    listarBloqueiosCard();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite um número válido.");
            scanner.nextLine(); // Limpa a entrada inválida para evitar loop infinito
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    private void bloquearCard() {
        System.out.print("Digite o ID do Card que deseja bloquear: ");
        Long cardId = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Optional<Card> cardOpt = cardService.getCardById(cardId);
        if (cardOpt.isEmpty()) {
            System.out.println("Erro: Card com ID " + cardId + " não existe.");
            return;
        }

        // Verificar se o card já está bloqueado
        if (blockService.isCardBlocked(cardId)) {
            System.out.println("Erro: Este Card já está bloqueado.");
            return;
        }

        System.out.print("Digite o motivo do bloqueio: ");
        String blockCause = scanner.nextLine();

        // Bloqueando o Card
        blockService.bloquearCard(cardId, blockCause);
        System.out.println("Card bloqueado com sucesso!");
    }

    private void desbloquearCard() {
        System.out.print("Digite o ID do Card para desbloquear: ");
        Long cardId = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Digite o motivo do desbloqueio: ");
        String motivo = scanner.nextLine();

        try {
            blockService.desbloquearCard(cardId, motivo);
            System.out.println("Card desbloqueado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao desbloquear o card: " + e.getMessage());
        }
    }

    private void listarBloqueiosCard() {
        System.out.print("Digite o ID do Card para listar bloqueios: ");
        Long cardId = scanner.nextLong();
        scanner.nextLine();

        try {
            List<Block> blocks = blockService.listarBloqueiosPorCard(cardId);
            if (blocks.isEmpty()) {
                System.out.println("Este card não possui bloqueios.");
            } else {
                System.out.println("Bloqueios do Card " + cardId + ":");
                for (Block block : blocks) {
                    System.out.println("- Bloqueado em: " + block.getBlockIn() + " | Motivo: " + block.getBlockCause());
                    if (block.getUnblockIn() != null) {
                        System.out.println("  Desbloqueado em: " + block.getUnblockIn() + " | Motivo: " + block.getUnblockCause());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar bloqueios: " + e.getMessage());
        }
    }






}