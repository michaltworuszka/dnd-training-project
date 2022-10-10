package com.tworuszka.players.repository;

import com.tworuszka.players.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Michał Tworuszka on 10.10.2022
 * @project dnd-training-project
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

