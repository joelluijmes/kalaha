package dev.joell.kalaha.player.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.joell.kalaha.player.PlayerEntity;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto entityToDto(PlayerEntity entity);

    @Mapping(target = "passwordHash", expression = "java(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(dto.password()))")
    PlayerEntity createDtoToEntity(CreatePlayerDto dto);
}
