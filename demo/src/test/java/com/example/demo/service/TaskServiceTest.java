package com.example.demo.service;
import com.example.demo.bo.Estatut;
import com.example.demo.bo.Task;
import com.example.demo.dao.TaskRepository;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exception.TaskDoesNotExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTask() {
        TaskDTO taskDTO = TaskDTO.builder()
                .titre("Test Task")
                .description("Description test")
                .build();

        Task task = Task.builder()
                .id(1)
                .titre("Test Task")
                .description("Description test")
                .dateCreation(LocalDateTime.now())
                .statut(Estatut.PENDING)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO savedTask = taskService.saveTask(taskDTO);

        assertNotNull(savedTask);
        assertEquals("Test Task", savedTask.getTitre());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetTasksByStatus() {
        Task task = new Task();
        task.setId(1);
        task.setTitre("Test Task");
        task.setDescription("Description test");
        task.setDateCreation(LocalDateTime.now());
        task.setStatut(Estatut.PENDING);
        when(taskRepository.findAllByStatut(Estatut.PENDING)).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getTasksByStatus(Estatut.PENDING);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitre());
    }

    @Test
    void testUpdateTaskStatus_TaskExists() throws TaskDoesNotExist {
        Task task = new Task(1, "Test Task", "Desc", LocalDateTime.now(), Estatut.PENDING);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        taskService.updateTaskStatus(1, Estatut.COMPLETED); // ✅ On met bien à jour avec COMPLETED

        assertEquals(Estatut.COMPLETED, task.getStatut()); // ✅ Vérification correcte
        verify(taskRepository, times(1)).save(task);
    }


    @Test
    void testUpdateTaskStatus_TaskDoesNotExist() throws TaskDoesNotExist {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TaskDoesNotExist.class, () -> taskService.updateTaskStatus(1, Estatut.COMPLETED));
        assertEquals("No task exists with id: 1", exception.getMessage());
    }


}
