package com.tworuszka.players.repository;

import com.tworuszka.players.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Michał Tworuszka on 10.10.2022
 * @project dnd-training-project
 */

@Repository
public interface PlayerRepo extends JpaRepository<Player, Long> {
    Optional<Player> findByUsernameIgnoreCase(String username);

    long deleteByIsActive(boolean isActive);


}
