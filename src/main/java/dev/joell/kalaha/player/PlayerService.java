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
        PlayerEntity entity = this.repository.save(new PlayerEntity(player.name()));
        return new PlayerDto(entity.getId(), entity.getName());
    }

    public PlayerDto findById(int id) {
        PlayerEntity entity = this.repository.findById(id).orElseThrow();
        return new PlayerDto(entity.getId(), entity.getName());
    }

    public List<PlayerDto> findAll() {
        return this.repository.findAll()
            .stream()
            .map(entity -> new PlayerDto(entity.getId(), entity.getName()))
            .toList();
    }
}
