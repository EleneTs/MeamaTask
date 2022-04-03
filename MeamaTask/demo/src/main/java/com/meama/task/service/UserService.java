package com.meama.task.service;


import com.meama.task.data.dto.UserDto;
import com.meama.task.data.entity.Role;
import com.meama.task.data.entity.User;
import com.meama.task.repository.RoleRepository;
import com.meama.task.repository.UserRepository;
import com.meama.task.util.exception.ExceptionFactory;
import com.meama.task.util.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(UserDto userDto) throws MyException {
        if(userRepository.findByUsername(userDto.getUsername()) != null){
            throw ExceptionFactory.constraintViolated("This username is already used");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        List<Role> rolesList = new ArrayList<>();
        for(String roleName: userDto.getRoles()){
            if(roleRepository.findByName(roleName) != null){
                rolesList.add(roleRepository.findByName(roleName));
            }
        }
        if(rolesList.isEmpty()){
            throw ExceptionFactory.roleNotFound();
        }
        user.setRoles(rolesList);

        userRepository.save(user);

    }

    public List<User> getAll(){
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(usersList::add);
        return usersList;
    }
}
