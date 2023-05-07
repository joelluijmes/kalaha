package dev.joell.kalaha.player;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.player.dtos.*;


@Service
public class PlayerService {
    private final PlayerRepository repository;

    private PlayerMapper mapper;

    public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlayerDto create(CreatePlayerDto player) {
        PlayerEntity entity = this.repository.saveAndFlush(mapper.createDtoToEntity(player));
        
        // TODO: how to force read query such that createdAt field is populated? Current behavior is that it is null.
        return this.findById(entity.getId());
    }

    public PlayerDto findById(int id) {
        PlayerEntity entity = this.repository.findById(id).orElseThrow();
        return mapper.entityToDto(entity);
    }

    public List<PlayerDto> findAll() {
        return this.repository.findAll()
            .stream()
            .map(entity -> mapper.entityToDto(entity))
            .toList();
    }
}
