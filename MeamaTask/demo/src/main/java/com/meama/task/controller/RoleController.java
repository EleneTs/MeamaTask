package com.meama.task.controller;


import com.meama.task.data.dto.RoleDto;
import com.meama.task.service.RoleService;
import com.meama.task.util.exception.MyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    private final RoleService roleService;


    @PostMapping
    @Operation(summary = "Creating role", responses = {
            @ApiResponse(responseCode = "200",description = "Successfully created"),
            @ApiResponse(responseCode = "403", description = "Not Allowed"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public void addRole(@RequestBody RoleDto roleDto) throws MyException {
        roleService.addRole(roleDto);
    }


}
