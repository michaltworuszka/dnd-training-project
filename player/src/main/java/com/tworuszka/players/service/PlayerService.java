package com.tworuszka.players.service;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.exceptions.PlayerNotFoundException;
import com.tworuszka.players.exceptions.UsernameExistException;
import com.tworuszka.players.model.Player;
import com.tworuszka.players.repository.PlayerRepo;
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
public class PlayerService {

    private final PlayerRepo playerRepository;
    private final ModelMapper modelMapper;

    private PlayerDTO convertToDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public PlayerDTO save(PlayerDTO playerDTO) {
        if (playerRepository.findByUsernameIgnoreCase(playerDTO.getUsername()).isPresent()) {
            throw new UsernameExistException(playerDTO.getUsername());
        } else {
            Player player = modelMapper.map(playerDTO, Player.class);
            player.setActive(true);
            playerDTO = convertToDTO(playerRepository.save(player));
            return playerDTO;
        }
    }

    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream()
                .filter(Player::isActive)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO findById(Long id) {
        Player existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        if (existingPlayer.isActive()) {
            return convertToDTO(existingPlayer);
        } else throw new PlayerNotFoundException(id);
    }

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
        return convertToDTO(playerRepository.save(player));
    }

    public void delete(Long id) {
        Player existingPlayer = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
        if (existingPlayer.isActive()) {
            existingPlayer.setActive(false);
            playerRepository.save(existingPlayer);
        } else throw new PlayerNotFoundException(id);
    }

    public void deleteInactive() {
        playerRepository.deleteByIsActive(false);
    }
}
