package com.meama.task.repository;

import com.meama.task.data.entity.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Modifying
    @Query("update Task t set " +
            "t.description = (case when :#{#task.description} is not null then :#{#task.description} else t.description end)," +
            "t.shortDescription = (case when :#{#task.shortDescription} is not null then :#{#task.shortDescription} else t.shortDescription end)," +
            "t.assignedUser = (case when :#{#task.assignedUser} is not null then :#{#task.assignedUser} else t.assignedUser end)" +
            "where t.id = :#{#task.id}")
    void updateTask(Task task);

    void deleteById(Long id);
}
