package com.meama.task.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaskDto {

    private String name;

    private String shortDescription;

    private String description;

    private String assignedUsername;

}
