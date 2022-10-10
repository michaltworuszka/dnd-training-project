package com.tworuszka.players.repository;

import com.tworuszka.players.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByUsernameIgnoreCase(String userName);
}
