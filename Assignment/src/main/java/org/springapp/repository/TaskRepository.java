package org.springapp.repository;

import org.springapp.entites.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository {

    // In-memory list of task to store.
    List<Task> taskList = new ArrayList<>();

    public void add(Task task){
        taskList.add(task);
    }

    public Task markDone(int id, boolean markDone){
        taskList.get(id).setDone(markDone);
        return taskList.get(id);
    }

    public int getTaskListSize(){
        return taskList.size();
    }

    public Task markCancelled(int id, boolean markCancelled){
        taskList.get(id).setCancelled(markCancelled);
        return taskList.get(id);
    }

    public boolean isTaskCancelled(int id) {
        return taskList.get(id).isCancelled();
    }

    public boolean isTaskDone(int id) {
        return taskList.get(id).isDone();
    }

    public Task update(int id, Task task) {
        taskList.set(id, task);
        return taskList.get(id);
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
