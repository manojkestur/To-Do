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
        // Converts DTO to Entity Object.
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        task.setId(counter++);
        // all the nessassary validations.
        if(task.getPriority()==null || task.getPriority().toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority is mandatory");
        }
        // if everything is okay add new task to list and return the DTO.
        taskRepository.add(task);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO markTaskDone(int id, boolean markDone){
        // To check what is the range of valid id's get current size of list.
        int curTaskListSize = taskRepository.getTaskListSize();
        // All the necessary validation.
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if(taskRepository.isTaskCancelled(id)){
            throw new IllegalStateException("Task is in cancelled state");
        }
        // If everything is okay mark that particular task as done and return DTO.
        Task task = taskRepository.markDone(id, markDone);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO markTaskCancelled(int id, boolean markCancelled){
        // To check what is the range of valid id's get current size of list.
        int curTaskListSize = taskRepository.getTaskListSize();
        // All the necessary validation.
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if(taskRepository.isTaskDone(id)){
            throw new IllegalStateException("Task is in done state");
        }
        // If everything is okay mark that particular task as cancelled and return DTO.
        Task task = taskRepository.markCancelled(id, markCancelled);
        return TaskMapper.INSTANCE.toDto(task);
    }

    public TaskDTO updateTask(int id, TaskDTO taskDTO){
        // Converts DTO to Entity Object.
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        int curTaskListSize = taskRepository.getTaskListSize();
        // All the necessary validation.
        if(id > curTaskListSize){
            throw new IllegalArgumentException("Invalid id");
        } else if (task.getPriority()==null || task.getPriority().toString().trim().isEmpty()){
            throw new IllegalArgumentException("Priority is mandatory");
        }
        task.setId(id);
        // If everything is okay update the task and return DTO.
         Task task1 = taskRepository.update(id, task);
        return TaskMapper.INSTANCE.toDto(task1);
    }

    public List<TaskDTO> getTasks(Filters filters, SortBy sortBy){
        //Get current task list
        List<Task> curTaskList = new ArrayList<>(taskRepository.getTaskList());

        FilterStrategy filterStrategy =null;
        SortByStrategy sortByStrategy =null;
        // If filters is not null get which strategy to apply.
        if(filters!=null) {
            filterStrategy = FilterFactory.getFilterStrategyFactory(filters);
        }
        // If sort is not null get which strategy to apply.
        if(sortBy!=null) {
            sortByStrategy = SortByFactory.getSortByStrategyFactory(sortBy);
        }
        // Calling filter method of particular strategy to filter current list

        /*Strategy Pattern here helps to extend the filter and sort
           method if any new filter or sort is added in future without changes in here.*/

        if(filterStrategy !=null) {
            curTaskList = filterStrategy.filter(curTaskList, filters);
        }
        // Calling sort method of particular strategy to sort current list
        if(sortByStrategy != null) {
            curTaskList = sortByStrategy.sort(curTaskList);
        }
        // return list of TaskDTO
        return curTaskList.stream().map(TaskMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}
