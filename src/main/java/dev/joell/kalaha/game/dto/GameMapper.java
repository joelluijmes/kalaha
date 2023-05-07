package dev.joell.kalaha.game.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;

import dev.joell.kalaha.board.Board;
import dev.joell.kalaha.game.GameEntity;

@Mapper(mappingControl = DeepClone.class)
public interface GameMapper {
    GameDto entityToDto(GameEntity entity);

    @Mapping(target = "id", ignore = true)
    GameEntity boardToEntity(Board board);

    Board dtoToBoard(GameDto game);

    Board entityToBoard(GameEntity entity);
}
