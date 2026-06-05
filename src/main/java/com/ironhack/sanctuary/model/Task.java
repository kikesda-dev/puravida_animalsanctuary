package com.ironhack.sanctuary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.sanctuary.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Data
@ToString(exclude = {"animal", "volunteer"})
@NoArgsConstructor
@Table(name = "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnore
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    @JsonIgnore
    private Volunteer volunteer;
}
