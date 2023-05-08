package dev.joell.kalaha.game;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.board.Board;
import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.common.exceptions.NotFoundApiException;
import dev.joell.kalaha.game.dto.*;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

        new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("null");
    }

    public GameDto findById(int id) throws ApiException {
        return this.mapper.entityToDto(
                this.repository.findById(id).orElseThrow(() -> new NotFoundApiException("Game not found.")));
    }

    public String getPrettyFormattedForId(int id) throws ApiException {
        Board board = this.findBoardById(id);
        return board.asciiFormatString();
    }

    public List<GameDto> findAll() {
        return this.repository.findAll().stream()
                .map(this.mapper::entityToDto)
                .toList();
    }

    public GameDto create(CreateGameDto game) throws ApiException {
        Board board = new Board(game.cupsPerPlayer(), game.stonesPerCup());
        GameEntity entity = this.repository.save(
                this.mapper.boardToEntity(board));

        return this.findById(entity.getId());
    }

    public GameDto performMove(int id, int cup) throws ApiException {
        GameEntity entity = this.repository.findById(id).orElseThrow(() -> new NotFoundApiException("Game not found."));
        Board board = this.mapper.entityToBoard(entity);

        try {
            board.makeMove(cup);
        } catch (Exception e) {
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
