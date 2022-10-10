package com.tworuszka.players.controller;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.save(playerDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> findAll() {
        return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable("id") Long id, @RequestBody PlayerDTO playerDTO) {
        return new ResponseEntity<>(playerService.update(id, playerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        playerService.delete(id);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInactive() {
        playerService.deleteInactive();
    }
}
