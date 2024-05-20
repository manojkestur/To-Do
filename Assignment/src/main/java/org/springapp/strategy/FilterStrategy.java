package org.springapp.strategy;

import org.springapp.dto.Filters;
import org.springapp.entites.Task;

import java.util.List;

public interface FilterStrategy {

    List<Task> filter(List<Task> tasks, Filters filters);
}
