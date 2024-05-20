package org.springapp.strategy;

import org.springapp.dto.Filters;
import org.springapp.entites.Task;

import java.util.List;
import java.util.stream.Collectors;

public class PriorityFilter implements FilterStrategy{
    @Override
    public List<Task> filter(List<Task> tasks, Filters filters) {
        tasks = tasks.stream().filter(task -> task.getPriority().equals(filters.getPriority())).collect(Collectors.toList());
        return tasks;
    }
}
