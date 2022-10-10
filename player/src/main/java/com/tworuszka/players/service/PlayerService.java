package com.tworuszka.players.service;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.model.Role;

import java.util.List;

/**
 * @author Michał Tworuszka on 10.10.2022
 * @project dnd-training-project
 */
public interface PlayerService {
    PlayerDTO save(PlayerDTO playerDTO);

    Role saveRole(Role role);

    void addRoleToPlayer(String username, String roleName);

    List<PlayerDTO> findAll();

    PlayerDTO findById(Long id);

    PlayerDTO update(Long id, PlayerDTO playerDTO);

    void delete(Long id);

    void deleteInactive();
}
