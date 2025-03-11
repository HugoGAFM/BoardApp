package Board.BoardApp.Repository;

import Board.BoardApp.Model.Block;
import Board.BoardApp.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findByCard(Card card);

}
