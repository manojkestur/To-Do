package org.springapp.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springapp.dto.TaskDTO;
import org.springapp.entites.Task;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "taskDescription", target = "taskDescription")
    @Mapping(source = "done", target = "done")
    @Mapping(source = "dueDate", target = "dueDate")
    @Mapping(source = "cancelled", target = "cancelled")
    Task toEntity(TaskDTO taskDTO);
    TaskDTO toDto(Task task);
}
