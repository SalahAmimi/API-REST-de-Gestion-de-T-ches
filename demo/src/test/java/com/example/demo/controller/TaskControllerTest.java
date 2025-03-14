package com.example.demo.controller;

import com.example.demo.bo.Estatut;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exception.TaskDoesNotExist;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }


    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO(1, "Test Task", "Description", LocalDateTime.now(), Estatut.PENDING);

        when(taskService.saveTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titre\":\"Test Task\",\"description\":\"Description\",\"statut\":\"PENDING\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titre").value("Test Task"))
                .andExpect(jsonPath("$.statut").value("PENDING"));
    }

    @Test
    public void testDeleteById_Success() throws Exception {
        Mockito.doNothing().when(taskService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("tache has been succesfully deleted"));
    }

    @Test
    public void testDeleteById_NotFound() throws Exception {
        doThrow(new TaskDoesNotExist("Task not found")).when(taskService).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));
    }


    @Test
    public void testGetAllTasks() throws Exception {
        List<TaskDTO> tasks = Arrays.asList(
                new TaskDTO(1, "Task 1", "Desc 1", LocalDateTime.now(), Estatut.PENDING),
                new TaskDTO(2, "Task 2", "Desc 2", LocalDateTime.now(), Estatut.COMPLETED)
        );

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }


    @Test
    public void testUpdateTaskStatus_Success() throws Exception {
        Mockito.doNothing().when(taskService).updateTaskStatus(1, Estatut.COMPLETED);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .param("statut", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(content().string("statut has been updated"));
    }

    @Test
    public void testUpdateTaskStatus_InvalidStatut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
                        .param("statut", "INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Statut invalide. Utilisez : PENDING, IN_PROGRESS, COMPLETED"));
    }

    @Test
    public void testGetTasksByStatus_Success() throws Exception {
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(1, "Task 1", "Desc", LocalDateTime.now(), Estatut.PENDING));

        when(taskService.getTasksByStatus(Estatut.PENDING)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/ByStatut")
                        .param("statut", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    public void testGetTasksByStatus_InvalidStatut() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/ByStatut")
                        .param("statut", "INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Statut invalide. Utilisez : PENDING, IN_PROGRESS, COMPLETED"));
    }
}
