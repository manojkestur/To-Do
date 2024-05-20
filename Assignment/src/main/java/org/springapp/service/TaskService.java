package org.springapp.service;


import org.springapp.dto.Filters;
import org.springapp.dto.TaskDTO;
import org.springapp.entites.Task;
import org.springapp.enums.SortBy;
import org.springapp.factory.FilterFactory;
import org.springapp.factory.SortByFactory;
import org.springapp.mappers.TaskMapper;
import org.springapp.repository.TaskRepository;
import org.springapp.strategy.FilterStrategy;
import org.springapp.strategy.SortByStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    static int counter=0;

    @Autowired
    TaskRepository taskRepository;

    public TaskDTO addTask(TaskDTO taskDTO){
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        task.setId(counter++);
        if(task.getPriority()==null || task.getPriority().toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority is mandatory");
        }
        taskRepository.add(task);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO markTaskDone(int id, boolean markDone){
        int curTaskListSize = taskRepository.getTaskListSize();
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if(taskRepository.isTaskCancelled(id)){
            throw new IllegalStateException("Task is in cancelled state");
        }
        Task task = taskRepository.markDone(id, markDone);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO markTaskCancelled(int id, boolean markCancelled){
        int curTaskListSize = taskRepository.getTaskListSize();
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if(taskRepository.isTaskDone(id)){
            throw new IllegalStateException("Task is in done state");
        }
        Task task = taskRepository.markCancelled(id, markCancelled);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO updateTask(int id, TaskDTO taskDTO){
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        int curTaskListSize = taskRepository.getTaskListSize();
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if (task.getPriority()==null || task.getPriority().toString().trim().isEmpty()){
            throw new IllegalArgumentException("Priority is mandatory");
        }
        task.setId(id);
         Task task1 = taskRepository.update(id, task);
        return TaskMapper.INSTANCE.toDto(task1);
    }

    public List<TaskDTO> getTasks(Filters filters, SortBy sortBy){
        List<Task> curTaskList = new ArrayList<>(taskRepository.getTaskList());

        FilterStrategy filterStrategy =null;
        SortByStrategy sortByStrategy =null;
        if(filters!=null) {
            filterStrategy = FilterFactory.getFilterStrategyFactory(filters);
        }
        if(sortBy!=null) {
            sortByStrategy = SortByFactory.getSortByStrategyFactory(sortBy);
        }
        if(filterStrategy !=null) {
            curTaskList = filterStrategy.filter(curTaskList, filters);
        }
        if(sortByStrategy != null) {
            curTaskList = sortByStrategy.sort(curTaskList);
        }

        return curTaskList.stream().map(TaskMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
