package com.meama.task.controller;

import com.meama.task.data.dto.TaskDto;
import com.meama.task.data.entity.Task;
import com.meama.task.service.TaskService;
import com.meama.task.util.exception.MyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    @PostMapping
    @Operation(summary = "Creating task", responses = {
            @ApiResponse(responseCode = "200",description = "Successfully created"),
            @ApiResponse(responseCode = "403", description = "Not Allowed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public void createTask(@ModelAttribute TaskDto taskDto,@RequestPart(name = "file",required = false) MultipartFile[] uploadFiles) throws MyException {
        taskService.createTask(taskDto, uploadFiles);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE')")
    @PutMapping({"{id}"})
    @Operation(summary = "Updating task", responses = {
            @ApiResponse(responseCode = "200",description = "Successfully updated"),
            @ApiResponse(responseCode = "403", description = "Not Allowed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public void updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) throws MyException {
        taskService.updateTask(id, taskDto);
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public TaskDto getTask(@PathVariable Long id){
        return convertToDto(taskService.getTask(id));
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE')")
    @DeleteMapping({"{id}"})
    @Operation(summary = "Deleting task", responses = {
            @ApiResponse(responseCode = "200",description = "Successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Not Allowed"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public void deleteTask(@PathVariable Long id) throws MyException {
        taskService.deleteTask(id);
    }

    private TaskDto convertToDto(Task task) {
        TaskDto taskDto = modelMapper.map(task, TaskDto.class);
        taskDto.setDescription(task.getDescription());
        taskDto.setName(task.getName());
        taskDto.setShortDescription(task.getShortDescription());
        taskDto.setAssignedUsername(task.getAssignedUser().getUsername());
        return taskDto;
    }
}
