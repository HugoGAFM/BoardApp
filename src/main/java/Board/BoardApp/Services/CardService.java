package Board.BoardApp.Services;

import Board.BoardApp.Model.Card;
import Board.BoardApp.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BlockService blockService;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> getCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card updateCard(Long cardId, Card updatedCard) {
        Card existingCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card não encontrado com ID " + cardId));

        if (blockService.isCardBlocked(cardId)) {
            throw new IllegalStateException("Não é possível atualizar um card bloqueado. Remova o bloqueio primeiro.");
        }

        existingCard.setTitle(updatedCard.getTitle());
        existingCard.setDescription(updatedCard.getDescription());
        // Se necessário, atualize outros campos

        return cardRepository.save(existingCard);
    }

    public void deleteCard(Long cardId) {
        Card existingCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card não encontrado com ID " + cardId));

        if (blockService.isCardBlocked(cardId)) {
            throw new IllegalStateException("Não é possível deletar um card bloqueado. Remova o bloqueio primeiro.");
        }

        cardRepository.delete(existingCard);


    }




}



