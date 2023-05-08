package dev.joell.kalaha.player;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    Optional<PlayerEntity> findByUsername(String username);
}
