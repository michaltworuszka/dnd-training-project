package com.tworuszka.players;

import com.tworuszka.players.service.PlayerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PlayersApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlayersApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PlayerService playerService) {
        return args -> {
//            playerService.saveRole(new Role(null, "ROLE_USER"));
//            playerService.saveRole(new Role(null, "ROLE_MANAGER"));
//            playerService.saveRole(new Role(null, "ROLE_ADMIN"));
//            playerService.addRoleToPlayer("mike@op.pl", "ROLE_ADMIN");
//            playerService.addRoleToPlayer("mike_2@op.pl", "ROLE_USER");
        };
    }
}
