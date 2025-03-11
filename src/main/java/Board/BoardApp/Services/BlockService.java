package Board.BoardApp.Services;

import Board.BoardApp.Model.Block;
import Board.BoardApp.Model.Card;
import Board.BoardApp.Repository.BlockRepository;
import Board.BoardApp.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BlockService {

        @Autowired
        private BlockRepository blockRepository;

        @Autowired
        private CardRepository cardRepository;

    public List<Block> getAllBlocks() {
        return blockRepository.findAll();


    }

    public Block getBlockById(Long id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Block com ID " + id + " não encontrado."));
    }

    public Block createBlock(Block block) {
        return blockRepository.save(block);
    }

    public void deleteBlock(Long id) {
        if (!blockRepository.existsById(id)) {
            throw new IllegalArgumentException("Block com ID " + id + " não encontrado.");
        }
        blockRepository.deleteById(id);
    }

    // Cria um bloqueio para o Card com o motivo especificado.
    public void bloquearCard(Long cardId, String motivo) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));
        Block block = new Block();
        block.setCard(card);
        block.setBlockCause(motivo);
        block.setBlockIn(OffsetDateTime.now());
        // Deixa os campos de desbloqueio nulos
        blockRepository.save(block);
    }

    // Desbloqueia o Card; para cada bloqueio ativo, define os campos de desbloqueio.
    public void desbloquearCard(Long cardId, String motivoDesbloqueio) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));
        List<Block> blocks = blockRepository.findByCard(card);
        for (Block b : blocks) {
            if (b.getBlockIn() != null && b.getUnblockIn() == null) { // Bloqueio ativo
                b.setUnblockCause(motivoDesbloqueio);
                b.setUnblockIn(OffsetDateTime.now());
                blockRepository.save(b);
            }
        }
    }

    // Verifica se o Card está bloqueado (existe pelo menos um bloqueio ativo)
    public boolean isCardBlocked(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));
        List<Block> blocks = blockRepository.findByCard(card);
        return blocks.stream().anyMatch(b -> b.getBlockIn() != null && b.getUnblockIn() == null);
    }

    // Lista todos os Blocks associados ao Card
    public List<Block> listarBloqueiosPorCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card não encontrado com ID " + cardId));
        return blockRepository.findByCard(card);
    }


}


