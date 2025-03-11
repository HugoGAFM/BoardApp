package Board.BoardApp.Controllers.example;

import Board.BoardApp.Model.Block;
import Board.BoardApp.Services.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping
    public List<Block> getAllBlocks() {
        return blockService.getAllBlocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlockById(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);
        return ResponseEntity.ok(block);
    }

    @PostMapping
    public ResponseEntity<Block> createBlock(@RequestBody Block block) {
        Block savedBlock = blockService.createBlock(block);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBlock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.noContent().build();
    }
}

