package Board.BoardApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String blockCause;
    private OffsetDateTime blockIn;
    private String unblockCause;
    private OffsetDateTime unblockIn;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}

