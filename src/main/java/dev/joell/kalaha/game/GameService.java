package dev.joell.kalaha.game;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.board.Board;
import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.common.exceptions.NotFoundApiException;
import dev.joell.kalaha.game.dto.*;
import jakarta.transaction.Transactional;

/**
 * Implements the business logic for the game. Its maps between the DTOs and the
 * entities and uses the Board class to perform the game logic.
 */
@Service
@Transactional
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Find a game by id.
     */
    public GameDto findById(int id) throws NotFoundApiException {
        return this.mapper.entityToDto(
                this.repository.findById(id).orElseThrow(() -> new NotFoundApiException("Game not found.")));
    }

    /**
     * Get a pretty formatted string for the game with the given id.
     */
    public String getPrettyFormattedForId(int id) throws ApiException {
        Board board = this.findBoardById(id);
        return board.asciiFormatString();
    }

    /**
     * Get all games.
     */
    public List<GameDto> findAll() {
        return this.repository.findAll().stream()
                .map(this.mapper::entityToDto)
                .toList();
    }

    /**
     * Create a new game.
     */
    public GameDto create(CreateGameDto game) throws ApiException {
        Board board = new Board(game.cupsPerPlayer(), game.stonesPerCup());
        GameEntity entity = this.repository.save(this.mapper.boardToEntity(board));

        return this.findById(entity.getId());
    }

    /**
     * Perform a move for the game with the given id, for the given cup.
     */
    public GameDto performMove(int id, int cup) throws ApiException {
        GameEntity entity = this.repository.findById(id).orElseThrow(() -> new NotFoundApiException("Game not found."));
        Board board = this.mapper.entityToBoard(entity);

        try {
            board.makeMove(cup);
        } catch (Exception e) {
            // Rethrow any exception as ApiException.
            // TODO: could be more specific
            throw new ApiException(e.getMessage());
        }

        GameEntity updatedEntity = this.mapper.boardToEntity(board);
        updatedEntity.setId(id);

        this.repository.save(updatedEntity);

        return this.findById(id);
    }

    private Board findBoardById(int id) throws ApiException {
        GameDto game = this.findById(id);
        return this.mapper.dtoToBoard(game);
    }
}
