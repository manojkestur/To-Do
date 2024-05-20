package org.springapp.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springapp.enums.Priority;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    int id;
    Priority priority;
    LocalDate dueDate;
    String taskDescription;
    boolean isDone;
    boolean isCancelled;
}
