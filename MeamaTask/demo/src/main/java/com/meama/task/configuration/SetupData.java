package com.meama.task.configuration;

import com.meama.task.data.entity.Privilege;
import com.meama.task.data.entity.Role;
import com.meama.task.data.entity.User;
import com.meama.task.repository.PrivilegeRepository;
import com.meama.task.repository.RoleRepository;
import com.meama.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;

@Component
public class SetupData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    boolean setUp = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(setUp) return;

        Privilege createPrivilege
                = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
        Privilege deletePrivilege
                = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        Privilege updatePrivilege
                = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");


        List<Privilege> adminPrivileges = Arrays.asList(createPrivilege,deletePrivilege,updatePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(updatePrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = userRepository.findByUsername("test@test.com");
        if(user == null){
            user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setUsername("test@test.com");
            user.setPassword(passwordEncoder.encode("test"));
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);
        }

    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}

