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
     * @param boardName the name of the new board
     * @return the response entity
     */
    @Operation(summary = "Add a new board")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addBoard(@RequestParam String boardName) {
        Board newBoard = new Board();
        newBoard.setBoardName(boardName);
        return new ResponseEntity<>(boardRepository.save(newBoard), HttpStatus.OK);
    }


    //todo: what is dis?
    /**
     * Replace board response entity.
     *
     * @param boardName the board name
     * @param id        the id
     * @return the response entity
     */
    @Operation(summary = "Change board name")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/{id}")
    public ResponseEntity replaceBoard(@RequestParam String boardName, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Board> board = boardRepository.findById(id);
        Board b;
        try {
            if(board.isPresent()) {
                Board temp = board.get();
                temp.setBoardName(boardName);
                b = boardRepository.save(temp);
                response = new ResponseEntity(b, HttpStatus.OK);
            } else {
                Board newBoard = new Board();
                newBoard.setId(id);
                b = boardRepository.save(newBoard);
                response = new ResponseEntity(b, HttpStatus.OK);
            }
        }
        catch (Exception e){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Const.IN_SERVER_ERR);
        }

        return response;
    }

    /**
     * Delete board response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Delete a board")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
