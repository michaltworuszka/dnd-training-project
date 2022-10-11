package com.tworuszka.players.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tworuszka.dto.PlayerDTO;
import com.tworuszka.players.model.Player;
import com.tworuszka.players.model.Role;
import com.tworuszka.players.model.RoleToPlayerForm;
import com.tworuszka.players.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                //todo make an utility class to get rid of redundancy

                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("yesthisismyhandtohandencodeofsecretdonemorethanonceyesthisismyhandtohandencodeofsecretdonemorethanonceyesthisismyhandtohandencodeofsecretdonemorethanonce".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Player player = playerService.findByUsername(username);

                String accessToken = JWT.create()
                        .withSubject(player.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", player.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                log.error("Error login in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
