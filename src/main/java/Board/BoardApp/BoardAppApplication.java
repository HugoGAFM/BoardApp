package Board.BoardApp;


import Board.BoardApp.Services.BlockService;
import Board.BoardApp.Services.BoardColumnService;
import Board.BoardApp.Services.BoardService;
import Board.BoardApp.Services.CardService;
import Board.BoardApp.Terminal.TerminalMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class BoardAppApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(BoardAppApplication.class, args);


		BoardService boardService = context.getBean(BoardService.class);
		BoardColumnService boardColumnService = context.getBean(BoardColumnService.class);
		CardService cardService = context.getBean(CardService.class);
		BlockService blockService = context.getBean(BlockService.class);


		TerminalMenu menu = new TerminalMenu(boardService, boardColumnService, cardService, blockService);
		menu.start();

	}

}
