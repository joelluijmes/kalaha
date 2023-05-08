package dev.joell.kalaha.player;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.player.dtos.*;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    private PlayerMapper mapper;

    public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlayerDto create(CreatePlayerDto player) throws ApiException {
        Optional<PlayerEntity> existingPlayer = this.repository.findByUsername(player.username());
        if (existingPlayer.isPresent()) {
            throw new ApiException("Player with name " + player.username() + " already exists");
        }

        // TODO: how to force read query such that createdAt field is populated? Current
        // behavior is that it is null.
        PlayerEntity entity = this.repository.save(mapper.createDtoToEntity(player));
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
