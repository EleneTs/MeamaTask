package com.meama.task.service;


import com.meama.task.data.dto.RoleDto;
import com.meama.task.data.entity.Privilege;
import com.meama.task.data.entity.Role;
import com.meama.task.repository.PrivilegeRepository;
import com.meama.task.repository.RoleRepository;
import com.meama.task.util.exception.ExceptionFactory;
import com.meama.task.util.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    public void addRole(RoleDto roleDto) throws MyException {
        if(roleRepository.findByName(roleDto.getName()) != null){
            throw ExceptionFactory.constraintViolated("Role with this name already exists");
        }
        Role role = new Role();
        role.setName(roleDto.getName());
        List<Privilege> privilegeList = new ArrayList<>();
        for(String privilegeName : roleDto.getPrivileges()){
            if(privilegeRepository.findByName(privilegeName) != null){
                privilegeList.add(privilegeRepository.findByName(privilegeName));
            }
        }
        role.setPrivileges(privilegeList);

        roleRepository.save(role);
    }
}
