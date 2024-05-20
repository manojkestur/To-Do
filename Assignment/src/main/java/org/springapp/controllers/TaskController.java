package org.springapp.controllers;


import org.springapp.dto.Filters;
import org.springapp.dto.ResponseDto;
import org.springapp.dto.TaskDTO;
import org.springapp.enums.Priority;
import org.springapp.enums.SortBy;
import org.springapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    // This API adds new task to the list.
    @PostMapping(value = "add")
    public ResponseEntity<ResponseDto<TaskDTO>> addNewTask(@RequestBody TaskDTO taskDTO) {
        ResponseDto<TaskDTO>response = new ResponseDto<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            response.setData(taskService.addTask(taskDTO));
            response.setErrors(null);
        } catch (IllegalArgumentException exception){
            // If any validation fails add it to responseDTO.
            response.setData(null);
            response.setErrors(List.of(exception.getMessage()));
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    // This API mark task as done.
    @PatchMapping(value = "done/{id}")
    public ResponseEntity<ResponseDto<TaskDTO>> markTaskDone(@PathVariable int id, @RequestBody boolean markDone) {
        ResponseDto<TaskDTO>response = new ResponseDto<>();
        HttpStatus status = HttpStatus.OK;
        try {
            response.setData(taskService.markTaskDone(id, markDone));
            response.setErrors(null);
        } catch (Exception exception){
            // If any validation fails add it to responseDTO.
            response.setData(null);
            response.setErrors(List.of(exception.getMessage()));
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(response, status);
    }

    // API to mark task cancelled.
    @PatchMapping(value = "cancel/{id}")
    public ResponseEntity<ResponseDto<TaskDTO>> markTaskCancelled(@PathVariable int id, @RequestBody boolean markCancelled) {
        ResponseDto<TaskDTO>response = new ResponseDto<>();
        HttpStatus status = HttpStatus.OK;
        try {
            response.setData(taskService.markTaskCancelled(id, markCancelled));
            response.setErrors(null);
        } catch (Exception exception){
            response.setData(null);
            response.setErrors(List.of(exception.getMessage()));
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    // API to edit/update the task.
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseDto<TaskDTO>> updateTaskDetails(@PathVariable int id, @RequestBody TaskDTO taskDTO){
        ResponseDto<TaskDTO>response = new ResponseDto<>();
        HttpStatus status = HttpStatus.OK;
        try {
            response.setData(taskService.updateTask(id, taskDTO));
            response.setErrors(null);
        } catch (IllegalArgumentException exception){
            response.setData(null);
            response.setErrors(List.of(exception.getMessage()));
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(response, status);
    }

    // API to get the tasks based on filter and sortBy.
    @GetMapping(value = "get")
    public ResponseEntity<ResponseDto<List<TaskDTO>>> getTasks(@RequestParam(required = false) Priority priority,
                                                               @RequestParam(required = false) String dueDate,
                                                               @RequestParam(required = false) SortBy sortBy){
        ResponseDto<List<TaskDTO>> response = new ResponseDto<>();
        HttpStatus status = HttpStatus.OK;
        try {
            Filters filters = new Filters();
            filters.setPriority(priority);
            if(dueDate!=null && !dueDate.isEmpty()) {
                filters.setDueDate(LocalDate.parse(dueDate));
            }
            response.setData(taskService.getTasks(filters, sortBy));
            response.setErrors(null);
        } catch (Exception exception){
            response.setData(null);
            response.setErrors(List.of(exception.getMessage()));
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

}
