package org.springapp.strategy;

import org.springapp.dto.Filters;
import org.springapp.entites.Task;

import java.util.List;
import java.util.stream.Collectors;

public class DueDateFilter implements FilterStrategy{

    @Override
    public List<Task> filter(List<Task> tasks, Filters filters) {
        tasks = tasks.stream().filter(task -> task.getDueDate().isBefore(filters.getDueDate())).collect(Collectors.toList());
        return tasks;
    }
}
