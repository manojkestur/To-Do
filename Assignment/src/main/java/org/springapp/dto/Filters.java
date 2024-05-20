package org.springapp.dto;

import lombok.Data;
import org.springapp.enums.Priority;

import java.time.LocalDate;

@Data
public class Filters {
    LocalDate dueDate;
    Priority priority;
}
