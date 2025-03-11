package Board.BoardApp.Controllers.example;

import Board.BoardApp.Model.BoardColumn;
import Board.BoardApp.Model.ColumnKind;
import Board.BoardApp.Services.BoardColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board-columns")
public class BoardColumnController {

    @Autowired
    private BoardColumnService boardColumnService;

    @GetMapping
    public List<BoardColumn> getAllColumns() {
        return boardColumnService.getAllColumns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardColumn> getColumnById(@PathVariable Long id) {
        Optional<BoardColumn> column = boardColumnService.getColumnById(id);
        return column.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BoardColumn> createColumn
            (@RequestParam Long boardId, @RequestParam String columnName,
             @RequestParam ColumnKind kind) {

        BoardColumn createdColumn = boardColumnService.createColumn(boardId, columnName, kind);
        return ResponseEntity.ok(createdColumn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        boardColumnService.deleteColumn(id);
        return ResponseEntity.noContent().build();
    }
}

