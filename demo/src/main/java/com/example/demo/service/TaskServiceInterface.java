package com.example.demo.service;

import com.example.demo.bo.Estatut;
import com.example.demo.bo.Task;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exception.TaskDoesNotExist;

import java.util.List;

public interface TaskServiceInterface {
    TaskDTO saveTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasks();
    void deleteById(int id) throws TaskDoesNotExist;
    void updateTaskStatus(int id, Estatut status) throws TaskDoesNotExist;
    List<TaskDTO> getTasksByStatus(Estatut status);
}
