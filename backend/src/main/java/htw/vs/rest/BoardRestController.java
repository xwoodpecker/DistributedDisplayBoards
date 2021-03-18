package htw.vs.rest;

import htw.vs.base.Const;
import htw.vs.data.Board;
import htw.vs.data.BoardRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Board rest controller.
 */
@Api(tags = {SpringFoxConfig.BOARD})
@RestController
@RequestMapping(path = "/boards")
public class BoardRestController {
    private BoardRepository boardRepository;

    /**
     * Instantiates a new Board rest controller.
     *
     * @param boardRepository the board repository
     */
    public BoardRestController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Gets boards.
     *
     * @return the boards
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all boards")
    @GetMapping(path = "/")
    public ResponseEntity<List<Board>> getBoards() {
        return new ResponseEntity<>(boardRepository.findAll(), HttpStatus.OK);

    }

    /**
     * Gets board.
     *
     * @param id the id
     * @return the board
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get board by given id")
    @GetMapping("/{id}")
    public ResponseEntity getBoard(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Board> board = boardRepository.findById(id);

        if(board.isPresent())
            response = new ResponseEntity(board.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_BOARD_MSG);

        return response;
    }

    /**
     * Add board response entity.
     *
     * @param board the board
     * @return the response entity
     */
    @Operation(summary = "Add a new board")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }

    /**
     * Replace board response entity.
     *
     * @param newBoard the new board
     * @param id       the id
     * @return the response entity
     */
    @Operation(summary = "Change board name")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/{id}")
    public ResponseEntity replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        Optional<Board> board = boardRepository.findById(id);
        Board b;
        if(board.isPresent()) {
            Board temp = board.get();
            if(newBoard.getBoardName() != null){
                temp.setBoardName(newBoard.getBoardName());
            }
            if(newBoard.getLocation() != null){
                temp.setLocation(newBoard.getLocation());
            }
            b = boardRepository.save(temp);
        } else {
            newBoard.setId(id);
            b = boardRepository.save(newBoard);
        }
        return new ResponseEntity(b, HttpStatus.OK);
    }

}
