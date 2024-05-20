import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springapp.dto.Filters;
import org.springapp.dto.TaskDTO;
import org.springapp.entites.Task;
import org.springapp.enums.Priority;
import org.springapp.mappers.TaskMapper;
import org.springapp.repository.TaskRepository;
import org.springapp.service.*;
import org.springapp.strategy.DueDateFilter;
import org.springapp.strategy.DueDateSort;
import org.springapp.strategy.PriorityFilter;
import org.springapp.strategy.PrioritySort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TaskService.class, TaskRepository.class, PriorityFilter.class, DueDateFilter.class,
        PrioritySort.class, DueDateSort.class})
public class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskService taskService;

    @InjectMocks
    PriorityFilter priorityFilter;

    @InjectMocks
    DueDateFilter dueDateFilter;

    @InjectMocks
    PrioritySort prioritySort;

    @InjectMocks
    DueDateSort dueDateSort;

    private TaskMapper taskMapper = TaskMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addTaskSuccessfully(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskDescription("test1");
        taskDTO.setPriority(Priority.HIGH);

        TaskDTO expectedTask = new TaskDTO();
        expectedTask.setId(0);
        expectedTask.setPriority(Priority.HIGH);
        expectedTask.setTaskDescription("test1");

        TaskDTO actualResponse = taskService.addTask(taskDTO);

        Assert.assertEquals(expectedTask.getId(), actualResponse.getId());
        Assert.assertEquals(expectedTask.getPriority(), actualResponse.getPriority());
        Assert.assertEquals(expectedTask.getTaskDescription(), actualResponse.getTaskDescription());
    }

    @Test
    public void checkWhenPriorityIsEmpty(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskDescription("test1");

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, () ->{
            taskService.addTask(taskDTO);
        });
        Assert.assertEquals("Priority is mandatory", exception.getMessage());
    }

    @Test
    public void markTaskDoneSuccessfully(){
        Task task = new Task();
        task.setId(0);
        task.setTaskDescription("test1");
        task.setPriority(Priority.HIGH);
        task.setDone(true);

        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);
        Mockito.when(taskRepository.isTaskCancelled(0)).thenReturn(false);
        Mockito.when(taskRepository.markDone(0, true)).thenReturn(task);
        TaskDTO actualsResponse = taskService.markTaskDone(0, true);

        Assert.assertEquals(actualsResponse.getId(), task.getId());
        Assert.assertEquals(actualsResponse.isDone(), task.isDone());
        Assert.assertEquals(actualsResponse.getTaskDescription(), task.getTaskDescription());
    }

    @Test
    public void markTaskDoneInvalidId(){
        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, ()->{
            taskService.markTaskDone(11, true);
        });

        Assert.assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    public void markTaskDoneTaskCancelledState(){
        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);
        Mockito.when(taskRepository.isTaskCancelled(0)).thenReturn(true);

        IllegalStateException exception = Assert.assertThrows(IllegalStateException.class, ()->{
            taskService.markTaskDone(0,true);
        });

        Assert.assertEquals("Task is in cancelled state", exception.getMessage());
    }

    @Test
    public void markTaskCancelledSuccessfully(){
        Task task = new Task();
        task.setId(0);
        task.setTaskDescription("test1");
        task.setPriority(Priority.HIGH);
        task.setCancelled(true);

        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);
        Mockito.when(taskRepository.isTaskDone(0)).thenReturn(false);
        Mockito.when(taskRepository.markCancelled(0, true)).thenReturn(task);
        TaskDTO actualResponse = taskService.markTaskCancelled(0, true);

        Assert.assertEquals(actualResponse.getId(), task.getId());
        Assert.assertEquals(actualResponse.isDone(), task.isDone());
        Assert.assertEquals(actualResponse.getTaskDescription(), task.getTaskDescription());
    }

    @Test
    public void markTaskCancelledInvalidId(){
        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, ()->{
            taskService.markTaskCancelled(11, true);
        });

        Assert.assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    public void markTaskCancelledTaskDoneState(){
        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);
        Mockito.when(taskRepository.isTaskDone(0)).thenReturn(true);

        IllegalStateException exception = Assert.assertThrows(IllegalStateException.class, ()->{
            taskService.markTaskCancelled(0,true);
        });

        Assert.assertEquals("Task is in done state", exception.getMessage());
    }

    @Test
    public void updateTaskSuccessfully(){
        TaskDTO taskDTO =  new TaskDTO();
        taskDTO.setPriority(Priority.MEDIUM);
        taskDTO.setTaskDescription("test2");

        Task expectedTask = new Task();
        expectedTask.setId(0);
        expectedTask.setPriority(Priority.MEDIUM);
        expectedTask.setTaskDescription("test2");

        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);
        Mockito.when(taskRepository.update(0, expectedTask)).thenReturn(expectedTask);

        TaskDTO actualResponse = taskService.updateTask(0, taskDTO);

        Assert.assertEquals(expectedTask.getId(), actualResponse.getId());
        Assert.assertEquals(expectedTask.getPriority(), actualResponse.getPriority());
        Assert.assertEquals(expectedTask.getTaskDescription(), actualResponse.getTaskDescription());

    }

    @Test
    public void updateTaskInvalidId(){
        TaskDTO taskDTO =  new TaskDTO();
        taskDTO.setPriority(Priority.MEDIUM);
        taskDTO.setTaskDescription("test2");

        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, ()->{
            taskService.updateTask(11, taskDTO);
        });

        Assert.assertEquals("Invalid id", exception.getMessage());
    }

    @Test
    public void updateTaskWhenPriorityIsEmpty(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskDescription("test1");

        Mockito.when(taskRepository.getTaskListSize()).thenReturn(10);

        IllegalArgumentException exception = Assert.assertThrows(IllegalArgumentException.class, () ->{
            taskService.updateTask(0, taskDTO);
        });
        Assert.assertEquals("Priority is mandatory", exception.getMessage());
    }

    @Test
    public void getTasksTestPriorityFilter(){
        Task task = new Task();
        task.setId(0);
        task.setPriority(Priority.HIGH);
        task.setTaskDescription("test1");

        Task task1 = new Task();
        task1.setId(1);
        task1.setPriority(Priority.MEDIUM);
        task1.setTaskDescription("test2");

        Filters filters = new Filters();
        filters.setPriority(Priority.HIGH);

        List<Task> taskList = new ArrayList<>(List.of(task, task1));

        List<Task> actualResponse = priorityFilter.filter(taskList, filters);

        Assert.assertEquals(taskList.get(0).getPriority(), actualResponse.get(0).getPriority());
        Assert.assertEquals(taskList.get(0).getTaskDescription(), actualResponse.get(0).getTaskDescription());

    }

    @Test
    public void getTaskTestDueDateFilter(){
        Task task = new Task();
        task.setId(0);
        task.setPriority(Priority.HIGH);
        task.setTaskDescription("test1");
        task.setDueDate(LocalDate.parse("2024-05-10"));

        Task task1 = new Task();
        task1.setId(1);
        task1.setPriority(Priority.MEDIUM);
        task1.setTaskDescription("test2");
        task1.setDueDate(LocalDate.parse("2024-05-30"));

        Filters filters = new Filters();
        filters.setDueDate(LocalDate.parse("2024-05-20"));

        List<Task> taskList = new ArrayList<>(List.of(task, task1));

        List<Task> actualResponse = dueDateFilter.filter(taskList, filters);

        Assert.assertEquals(taskList.get(0).getPriority(), actualResponse.get(0).getPriority());
        Assert.assertEquals(taskList.get(0).getTaskDescription(), actualResponse.get(0).getTaskDescription());
    }

    @Test
    public void getTaskTestPrioritySort(){
        Task task = new Task();
        task.setId(0);
        task.setPriority(Priority.HIGH);
        task.setTaskDescription("test1");
        task.setDueDate(LocalDate.parse("2024-05-10"));

        Task task1 = new Task();
        task1.setId(1);
        task1.setPriority(Priority.MEDIUM);
        task1.setTaskDescription("test2");
        task1.setDueDate(LocalDate.parse("2024-05-30"));

        List<Task> inputTaskList = new ArrayList<>(List.of(task, task1));
        List<Task> expectedTaskList = new ArrayList<>(List.of(task1, task));

        List<Task> actualResponse = prioritySort.sort(inputTaskList);

        Assert.assertEquals(expectedTaskList.get(0).getId(), actualResponse.get(0).getId());

    }

    @Test
    public void getTaskTestDueDateSort(){
        Task task = new Task();
        task.setId(0);
        task.setPriority(Priority.HIGH);
        task.setTaskDescription("test1");
        task.setDueDate(LocalDate.parse("2024-05-30"));

        Task task1 = new Task();
        task1.setId(1);
        task1.setPriority(Priority.MEDIUM);
        task1.setTaskDescription("test2");
        task1.setDueDate(LocalDate.parse("2024-05-10"));

        List<Task> inputTaskList = new ArrayList<>(List.of(task, task1));
        List<Task> expectedTaskList = new ArrayList<>(List.of(task1, task));

        List<Task> actualResponse = dueDateSort.sort(inputTaskList);

        Assert.assertEquals(expectedTaskList.get(0).getId(), actualResponse.get(0).getId());

    }

}
