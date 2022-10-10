package com.tworuszka.players.service;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.exceptions.PlayerNotFoundException;
import com.tworuszka.players.exceptions.UsernameExistException;
import com.tworuszka.players.model.Player;
import com.tworuszka.players.repository.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
            playerDTO = convertToDTO(playerRepository.save(player));
            return playerDTO;
        }
    }

    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO findById(Long id) {
        return convertToDTO(playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id)));
    }
}
