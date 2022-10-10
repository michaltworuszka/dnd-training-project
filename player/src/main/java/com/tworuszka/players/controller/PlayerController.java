package com.tworuszka.players.controller;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public PlayerDTO create(@RequestBody PlayerDTO playerDTO) {
        return playerService.save(playerDTO);
    }

    @GetMapping
    public List<PlayerDTO> findAll() {
        return playerService.findAll();
    }

    @GetMapping("/{id}")
    public PlayerDTO findById(@PathVariable("id") Long id) {
        return playerService.findById(id);
    }
}
