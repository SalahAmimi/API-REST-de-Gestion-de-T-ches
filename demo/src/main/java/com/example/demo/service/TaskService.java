package com.example.demo.service;

import com.example.demo.bo.Estatut;
import com.example.demo.bo.Task;
import com.example.demo.dao.TaskRepository;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exception.TaskDoesNotExist;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService implements TaskServiceInterface {
    private final TaskRepository taskRepository;

    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        taskDTO.setDateCreation(LocalDateTime.now());
        taskDTO.setStatut(Estatut.PENDING);
        Task savedTask = taskRepository.save(toTask(taskDTO));
        return fromTask(savedTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream().map(this::fromTask)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id) throws TaskDoesNotExist {

        if(!taskRepository.existsById(id)) {
            throw new TaskDoesNotExist("No task exists with id: "+ id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTaskStatus(int id, Estatut status) throws TaskDoesNotExist {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskDoesNotExist("No task exists with id: "+ id));
        task.setStatut(status);
        taskRepository.save(task);
    }

    @Override
    public List<TaskDTO> getTasksByStatus(Estatut status) {
        return taskRepository.findAllByStatut(status)
                .stream().map(this::fromTask)
                .collect(Collectors.toList());
    }

    private TaskDTO fromTask(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .titre(task.getTitre())
                .description(task.getDescription())
                .dateCreation(task.getDateCreation())
                .statut(task.getStatut())
                .build();
    }

    public Task toTask(TaskDTO dto){
        return Task.builder()
                .id(dto.getId())
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .dateCreation(dto.getDateCreation())
                .statut(dto.getStatut())
                .build();
    }


}
