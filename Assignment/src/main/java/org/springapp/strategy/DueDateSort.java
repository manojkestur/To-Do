package org.springapp.strategy;

import org.springapp.entites.Task;
import org.springapp.enums.SortBy;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DueDateSort implements SortByStrategy{
    @Override
    public List<Task> sort(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task1.getDueDate().compareTo(task2.getDueDate());
            }});
        return tasks;
    }
}
