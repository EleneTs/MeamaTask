package com.meama.task.controller;

import java.util.ArrayList;
import java.util.List;
import com.meama.task.data.dto.UserDto;
import com.meama.task.data.entity.Role;
import com.meama.task.data.entity.User;
import com.meama.task.service.UserService;
import com.meama.task.util.exception.MyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Creating user", responses = {
            @ApiResponse(responseCode = "200",description = "Successfully created"),
            @ApiResponse(responseCode = "403", description = "Not Allowed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public void createUser(@RequestBody UserDto userDto) throws MyException {
        userService.createUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        List<String> roles = new ArrayList<>();
        for(Role role : user.getRoles()){
            roles.add(role.getName());
        }
        userDto.setRoles(roles);
        return userDto;
    }


}
