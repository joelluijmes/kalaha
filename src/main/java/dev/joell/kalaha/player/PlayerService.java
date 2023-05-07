package dev.joell.kalaha.player;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.joell.kalaha.player.dtos.CreatePlayerDto;
import dev.joell.kalaha.player.dtos.PlayerDto;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public PlayerDto create(CreatePlayerDto player) {
        // repository.save doesn't return the full entity, so we have to do a second query
        PlayerEntity entity = this.repository.save(new PlayerEntity(player.name()));
        return this.findById(entity.getId());
    }

    public PlayerDto findById(int id) {
        PlayerEntity entity = this.repository.findById(id).orElseThrow();
        return new PlayerDto(entity.getId(), entity.getName(), entity.getCreatedAt());
    }

    public List<PlayerDto> findAll() {
        return this.repository.findAll()
            .stream()
            .map(entity -> new PlayerDto(entity.getId(), entity.getName(), entity.getCreatedAt()))
            .toList();
    }
}
