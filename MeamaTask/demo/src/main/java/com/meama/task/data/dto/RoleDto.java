package com.meama.task.data.dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDto {
    private Collection<String> privileges;

    private String name;

}
