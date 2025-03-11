package Board.BoardApp.Services;

import Board.BoardApp.Model.Board;
import Board.BoardApp.Model.BoardColumn;
import Board.BoardApp.Model.Card;
import Board.BoardApp.Repository.BoardColumnRepository;
import Board.BoardApp.Repository.BoardRepository;
import Board.BoardApp.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;


    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getAllBoardsWithColumnsAndCards() {
        List<Board> boards = boardRepository.findAll();

        // Para cada Board, carrega as BoardColumns e seus Cards
        for (Board board : boards) {
            List<BoardColumn> boardColumns = boardColumnRepository.findByBoardId(board.getId());
            for (BoardColumn boardColumn : boardColumns) {
                List<Card> cards = cardRepository.findByBoardColumnId(boardColumn.getId());
                boardColumn.setCards(cards); // Setando os cards na BoardColumn
            }
            board.setBoardColumns(boardColumns); // Setando as BoardColumns na Board
        }

        return boards;
    }

}


