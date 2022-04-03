package com.meama.task.service;

import com.meama.task.data.dto.TaskDto;
import com.meama.task.data.entity.Task;
import com.meama.task.data.entity.User;
import com.meama.task.repository.TaskRepository;
import com.meama.task.repository.UserRepository;
import com.meama.task.util.exception.ExceptionFactory;
import com.meama.task.util.exception.MyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public List<Task> getAllTasks(){
        List<Task> taskList = new ArrayList<Task>();
        taskRepository.findAll().iterator().forEachRemaining(taskList::add);
        return taskList;
    }

    public Task getTask(Long id){
        if(taskRepository.existsById(id)){
            return taskRepository.findById(id).get();
        }
        return null;
    }

    public void createTask(TaskDto taskDto, MultipartFile[] uploadFiles) throws MyException {
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setShortDescription(taskDto.getShortDescription());
        task.setName(taskDto.getName());
        User assignedUser = userRepository.findByUsername(taskDto.getAssignedUsername());
        if(assignedUser != null)
            task.setAssignedUser(assignedUser);
         else throw ExceptionFactory.userNotFound();

        if(assignedUser.getAssignedTasks() == null)
            assignedUser.setAssignedTasks(Arrays.asList(task));
         else
            assignedUser.getAssignedTasks().add(task);

        if(uploadFiles != null){
            List<byte[]> files = new ArrayList<>();
            for(MultipartFile file : uploadFiles){
                try {
                    files.add(file.getBytes());
                    System.out.println(file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            task.setFileData(files);
        }
        taskRepository.save(task);
    }
    public void updateTask(Long id, TaskDto taskDto) throws MyException{
        if(!taskRepository.existsById(id)){
            throw ExceptionFactory.taskNotFound();
        }
        Task task = taskRepository.findById(id).get();
        if(taskDto.getName() != null){
            task.setName(taskDto.getName());
        }
        if(taskDto.getDescription()!= null){
            task.setDescription(taskDto.getDescription());
        }
        if(taskDto.getShortDescription() != null){
            task.setShortDescription(taskDto.getShortDescription());
        }
        if(taskDto.getAssignedUsername() != null){
            User assignedUser = userRepository.findByUsername(taskDto.getAssignedUsername());
            if(assignedUser != null)
                task.setAssignedUser(assignedUser);
            else throw ExceptionFactory.userNotFound();
        }
        taskRepository.updateTask(task);

    }

    public void deleteTask(Long id) throws MyException{
        if(!taskRepository.existsById(id)){
            throw ExceptionFactory.taskNotFound();
        }
        Task task = taskRepository.findById(id).get();
        User assignedUser = task.getAssignedUser();
        Collection<Task> removed = assignedUser.getAssignedTasks();
        removed.remove(task);
        assignedUser.setAssignedTasks(removed);
        task.setAssignedUser(null);

        //taskRepository.deleteById(id);
        taskRepository.delete(task);
    }


}
