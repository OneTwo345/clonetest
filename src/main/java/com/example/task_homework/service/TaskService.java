package com.example.task_homework.service;

import com.example.task_homework.error.TaskNotFoundException;
import com.example.task_homework.model.Task;
import com.example.task_homework.model.enums.ETaskStatus;
import com.example.task_homework.model.enums.ETaskType;
import com.example.task_homework.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }


    @Override
    public Task updateTask(Task task, Long id) {
        return taskRepository.findById(id)
                .map(ta -> {
                    ta.setTitle(task.getTitle());
                    ta.setDescription(task.getDescription());
                    ta.setStart(task.getStart());
                    ta.setEnd(task.getEnd());
                    ta.setStatus(task.getStatus());
                    ta.setType(task.getType());
                    return taskRepository.save(ta);
                }).orElseThrow(()-> new TaskNotFoundException("Sorry, this task could be not found"));
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Sorry, this task could be not found with the id: "+id));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Sorry, this task could be not found with the id: "+id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getDailyTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getType().equals(ETaskType.DAILY))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getNonDailyTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getType().equals(ETaskType.NONE_DAILY))
                .collect(Collectors.toList());
    }

}
