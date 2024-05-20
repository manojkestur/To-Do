package org.springapp.strategy;

import org.springapp.entites.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrioritySort implements SortByStrategy{
    @Override
    public List<Task> sort(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return Integer.compare(task2.getPriority().getPriority(), task1.getPriority().getPriority());
            }
        });
        return tasks;
    }
}
