package com.example.demo.controller;

import com.example.demo.bo.Estatut;
import com.example.demo.dto.TaskDTO;
import com.example.demo.exception.TaskDoesNotExist;
import com.example.demo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTache(@RequestBody TaskDTO taskDTO) {
        TaskDTO savedTaskDTO = taskService.saveTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        try{
            taskService.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body("tache has been succesfully deleted");
        }catch (TaskDoesNotExist e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable int id,@RequestParam String statut) {
        try {
            Estatut enumStatut = Estatut.valueOf(statut.toUpperCase());
            taskService.updateTaskStatus(id, enumStatut);
            return ResponseEntity.status(HttpStatus.OK).body("statut has been updated");
        } catch (TaskDoesNotExist e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Statut invalide. Utilisez : PENDING, IN_PROGRESS, COMPLETED");
        }
    }

    @GetMapping("/ByStatut")
    public ResponseEntity<?> getTasksByStatus(@RequestParam String statut) {
        try {
            Estatut enumStatut = Estatut.valueOf(statut.toUpperCase()); // Convertir proprement en Enum
            return ResponseEntity.ok(taskService.getTasksByStatus(enumStatut));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Statut invalide. Utilisez : PENDING, IN_PROGRESS, COMPLETED");
        }
    }


}
