package com.tworuszka.players.controller;

import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.model.Role;
import com.tworuszka.players.model.RoleToPlayerForm;
import com.tworuszka.players.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO playerDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/player/save").toUriString());
        //return new ResponseEntity<>(playerService.save(playerDTO), HttpStatus.CREATED);
        return ResponseEntity.created(uri).body(playerService.save(playerDTO));
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> findAll() {
        //return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
        return ResponseEntity.ok().body(playerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable("id") Long id) {
        //return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
        return ResponseEntity.ok().body(playerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable("id") Long id, @RequestBody PlayerDTO playerDTO) {
        //return new ResponseEntity<>(playerService.update(id, playerDTO), HttpStatus.OK);
        return ResponseEntity.ok().body(playerService.update(id, playerDTO));
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

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
        return ResponseEntity.created(uri).body(playerService.saveRole(role));
    }

    @PostMapping("/addrole")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addRoleToPlayer(@RequestBody RoleToPlayerForm form) {
        playerService.addRoleToPlayer(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }
}
