package com.tworuszka.players.exceptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsernameExistException extends RuntimeException {
    public UsernameExistException(@NotEmpty @Size(min = 5) String username) {
        super("Username: " + username + " already in use");
    }
}
