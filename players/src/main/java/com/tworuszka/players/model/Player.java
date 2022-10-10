package com.tworuszka.players.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Player {

    private long id;
    private String name;
    private String username;
    private String password;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;


}
