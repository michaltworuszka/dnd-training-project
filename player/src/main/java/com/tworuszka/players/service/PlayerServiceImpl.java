package com.tworuszka.players.service;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.exceptions.PlayerNotFoundException;
import com.tworuszka.players.exceptions.UsernameExistException;
import com.tworuszka.players.model.Player;
import com.tworuszka.players.model.Role;
import com.tworuszka.players.repository.PlayerRepo;
import com.tworuszka.players.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepo playerRepository;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;

    private PlayerDTO convertToDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    @Override
    public PlayerDTO save(PlayerDTO playerDTO) {
        if (playerRepository.findByUsernameIgnoreCase(playerDTO.getUsername()).isPresent()) {
            log.info("throwing an exception");
            throw new UsernameExistException(playerDTO.getUsername());
        } else {

            Player player = modelMapper.map(playerDTO, Player.class);
            player.setActive(true);
            playerDTO = convertToDTO(playerRepository.save(player));
            log.info("Saving new player {} to database", playerDTO.getUsername());
            return playerDTO;
        }
    }

    @Override
    public List<PlayerDTO> findAll() {
        log.info("Fetching all active players from database");
        return playerRepository.findAll().stream()
                .filter(Player::isActive)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDTO findById(Long id) {
        Player existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        if (existingPlayer.isActive()) {
            log.info("Fetching player {} from database", existingPlayer.getUsername());
            return convertToDTO(existingPlayer);
        } else throw new PlayerNotFoundException(id);
    }

    @Override
    public PlayerDTO update(Long id, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        player.setName(playerDTO.getName());
        log.info("player name: {} and DTO name is {}", player.getName(), playerDTO.getName());
        if (!playerDTO.getUsername().equals(player.getUsername())) {
            if (playerRepository.findByUsernameIgnoreCase(playerDTO.getUsername()).isPresent()) {
                throw new UsernameExistException(playerDTO.getUsername());
            }
        }
        player.setUsername(playerDTO.getUsername());
        player.setPassword(playerDTO.getPassword());
        log.info("Updating player {} to database", player.getUsername());
        return convertToDTO(playerRepository.save(player));
    }

    @Override
    public void delete(Long id) {
        Player existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        if (existingPlayer.isActive()) {
            existingPlayer.setActive(false);
            playerRepository.save(existingPlayer);
            log.info("Making player {} inactive and saving that to database", existingPlayer.getUsername());
        } else throw new PlayerNotFoundException(id);
    }

    @Override
    public void deleteInactive() {
        log.info("Deleting all inactive players from database");
        playerRepository.deleteByIsActive(false);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToPlayer(String username, String roleName) {
        Player existingPlayer = playerRepository.findByUsernameIgnoreCase(username).orElseThrow(() -> new PlayerNotFoundException(username));
        Role role = roleRepo.findByName(roleName);
        existingPlayer.getRoles().add(role);
        log.info("Adding role {} to player {}", role.getName(), existingPlayer.getUsername());
    }
}
