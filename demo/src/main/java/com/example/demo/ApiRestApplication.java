package com.example.demo;

import com.example.demo.bo.Estatut;
import com.example.demo.bo.Task;
import com.example.demo.dao.TaskRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(TaskRepository taskRepository) {
		return args -> {
			Task task = new Task();
			task.setTitre("ddd");
			task.setDescription("Description ");

			taskRepository.save(task);
		};
	}
}
