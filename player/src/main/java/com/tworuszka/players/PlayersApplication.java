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
//            playerService.save(new PlayerDTO(null, "Mike", "mike@op.pl", "123456789", new Date(System.currentTimeMillis()), new ArrayList<>()));
//            playerService.save(new PlayerDTO(null, "Olaf", "olaf@op.pl", "123456789", new Date(System.currentTimeMillis()), new ArrayList<>()));
//            playerService.save(new PlayerDTO(null, "Anna", "anna@op.pl", "123456789", new Date(System.currentTimeMillis()), new ArrayList<>()));
//            playerService.saveRole(new Role(null, "ROLE_USER"));
//            playerService.saveRole(new Role(null, "ROLE_MANAGER"));
//            playerService.saveRole(new Role(null, "ROLE_ADMIN"));
//            playerService.addRoleToPlayer("mike@op.pl", "ROLE_ADMIN");
//            playerService.addRoleToPlayer("olaf@op.pl", "ROLE_USER");
//            playerService.addRoleToPlayer("anna@op.pl", "ROLE_USER");
        };
    }
}
