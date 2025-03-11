package Board.BoardApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BoardColumn {



    // Construtor com parâmetros para criação manual
    public BoardColumn(String name, ColumnKind kind, int columnOrder, Board board) {
        this.name = name;
        this.kind = kind;
        this.columnOrder = columnOrder;
        this.board = board;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ColumnKind kind;

    private int columnOrder;


    // Relacionamento ManyToOne com Board
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    // Relacionamento OneToMany com Card
    @OneToMany(mappedBy = "boardColumn", fetch = FetchType.EAGER)
    private List<Card> cards;
}