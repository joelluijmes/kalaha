package dev.joell.kalaha.player;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String passwordHash;

    // TODO: Figure out how to use @CreatedDate?
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public PlayerEntity() {
    }

    public PlayerEntity(String name) {
        this.username = name;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String hash) {
        this.passwordHash = hash;
    }
}
