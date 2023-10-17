package com.example.task_homework.service;

import com.example.task_homework.model.Task;

import java.util.List;

public interface ITaskService {
    Task addTask(Task task);
    Task updateTask(Task task, Long id);
    Task getTaskById(Long id);
    void deleteTask(Long id);
    List<Task> getAllTasks();
    List<Task> getDailyTasks();
    List<Task> getNonDailyTasks();
}
