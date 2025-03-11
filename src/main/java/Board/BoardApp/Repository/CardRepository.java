package Board.BoardApp.Repository;


import Board.BoardApp.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    List<Card> findByBoardColumnId(Long boardColumnId);
}
