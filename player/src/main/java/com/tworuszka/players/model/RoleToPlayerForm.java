package com.tworuszka.players.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Michał Tworuszka on 11.10.2022
 * @project dnd-training-project
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleToPlayerForm {
    private String username;
    private String rolename;
}
