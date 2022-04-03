package com.meama.task.data.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private Collection<String> roles;

}
