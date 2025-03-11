package Board.BoardApp.Repository;

import Board.BoardApp.Model.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {


    List<BoardColumn> findByBoardId(Long boardId);
}
