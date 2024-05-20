package org.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springapp.enums.Priority;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    int id;
    Priority priority;
    String taskDescription;
    LocalDate dueDate;
    boolean isDone;
    boolean isCancelled;
}
