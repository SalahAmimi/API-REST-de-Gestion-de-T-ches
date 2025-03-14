package com.example.demo.dao;

import com.example.demo.bo.Estatut;
import com.example.demo.bo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatut(Estatut statut);
}
