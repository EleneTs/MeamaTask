package com.meama.task.repository;

import com.meama.task.data.entity.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long> {

    Privilege findByName(String name);

}
