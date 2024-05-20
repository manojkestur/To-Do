package org.springapp.strategy;

import org.springapp.entites.Task;
import org.springapp.enums.SortBy;

import java.util.List;

public interface SortByStrategy {

    List<Task> sort(List<Task> tasks);
}
