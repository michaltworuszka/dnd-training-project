package com.tworuszka.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class PlayerDTO {

    private long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    @Size(min = 5)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 32)
    private String password;

    private Date createdAt;

    private List<String> roles = new ArrayList<>();

}
