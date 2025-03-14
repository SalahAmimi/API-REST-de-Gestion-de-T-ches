package com.example.demo.dto;

import com.example.demo.bo.Estatut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private int id;
   // @NotBlank(message = "Le titre est obligatoire")
    private String titre;
   // @NotNull(message = "La description  est obligatoire")
    private String description;
    private LocalDateTime dateCreation;
    private Estatut statut;
}
