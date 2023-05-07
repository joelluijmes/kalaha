package dev.joell.kalaha.player.dtos;

import org.mapstruct.Mapper;

import dev.joell.kalaha.player.PlayerEntity;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto entityToDto(PlayerEntity entity);
    PlayerEntity createDtoToEntity(CreatePlayerDto dto);
}
