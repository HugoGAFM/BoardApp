package Board.BoardApp.Services;

import Board.BoardApp.Model.Board;
import Board.BoardApp.Model.BoardColumn;
import Board.BoardApp.Model.ColumnKind;
import Board.BoardApp.Repository.BoardColumnRepository;
import Board.BoardApp.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class BoardColumnService {

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private BoardRepository boardRepository;


    public List<BoardColumn> getAllColumns() {
        return boardColumnRepository.findAll();
    }

    public Optional<BoardColumn> getColumnById(Long id) {
        return boardColumnRepository.findById(id);
    }



    public BoardColumn createColumn(Long boardId, String columnName, ColumnKind kind) {
        // Carregar o Board pelo ID
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board não encontrado com o ID: " + boardId));

        // Buscar as colunas existentes para o Board
        List<BoardColumn> columns = boardColumnRepository.findByBoardId(boardId);

        // Calcular a ordem da coluna
        int computedOrder = columns.size();
        if (kind == ColumnKind.INICIAL) {
            computedOrder = 0;
        }

        // Criar e associar a nova coluna ao Board
        BoardColumn newColumn = new BoardColumn(columnName, kind, computedOrder, board);

        // Salvar a BoardColumn e retornar o objeto salvo
        return boardColumnRepository.save(newColumn);
    }

    public List<BoardColumn> getColumnByBoardId(Long boardId) {
        return boardColumnRepository.findByBoardId(boardId);
    }


    public void deleteColumn(Long id) {
        boardColumnRepository.deleteById(id);
    }



}
