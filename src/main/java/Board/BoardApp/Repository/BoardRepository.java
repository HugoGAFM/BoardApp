package Board.BoardApp.Repository;

import Board.BoardApp.Model.Board;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


}
