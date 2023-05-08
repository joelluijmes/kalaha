package dev.joell.kalaha.game;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.board.Board;
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

    public GameDto findById(int id) {
        return this.mapper.entityToDto(
                this.repository.findById(id).orElseThrow());
    }

    public String getPrettyFormattedForId(int id) {
        Board board = this.findBoardById(id);
        return board.asciiFormatString();
    }

    public List<GameDto> findAll() {
        return this.repository.findAll().stream()
                .map(this.mapper::entityToDto)
                .toList();
    }

    public GameDto create(CreateGameDto game) {
        Board board = new Board(game.cupsPerPlayer(), game.stonesPerCup());
        GameEntity entity = this.repository.save(
                this.mapper.boardToEntity(board));

        return this.findById(entity.getId());
    }

    public GameDto performMove(int id, int cup) {
        GameEntity entity = this.repository.findById(id).orElseThrow();
        System.out.println(entity.getIdxCurrentPlayer());
        Board board = this.mapper.entityToBoard(entity);
        System.out.println(board.getIdxCurrentPlayer());
        board.makeMove(cup);

        GameEntity updatedEntity = this.mapper.boardToEntity(board);
        updatedEntity.setId(id);

        this.repository.save(updatedEntity);

        return this.findById(id);
    }

    private Board findBoardById(int id) {
        GameDto game = this.findById(id);
        return this.mapper.dtoToBoard(game);
    }
}
