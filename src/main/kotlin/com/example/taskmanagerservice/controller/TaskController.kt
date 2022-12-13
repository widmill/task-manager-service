package com.example.taskmanagerservice.controller

import com.example.taskmanagerservice.entity.Task
import com.example.taskmanagerservice.service.TaskService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {

    /**
     * @return Возвращает все существующие задачи.
     */
    @GetMapping
    fun findAll(): MutableList<Task> = taskService.findAll()

    /**
     * Удаляет задачу по ID.
     * @param id задачи, которую хотим удалить.
     */
    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID) {
        taskService.deleteTaskById(id)
    }

    /**
     * Создает задачу.
     * @param task задача, которую хотим создать.
     * @return Возращает созданую задачу.
     */
    @PostMapping
    fun create(@RequestBody task: Task): Task {
        return taskService.createOrUpdateTask(task)
    }

    /**
     * Сохраняет вложенный файл к задаче.
     * @param taskId задачи к которой хотим загрузить файл.
     * @param file файл который хотим загрузить.
     */
    @PostMapping("/{taskId}/upload")
    fun uploadFile(
        @PathVariable taskId: UUID,
        @RequestParam("file") file: MultipartFile
    ): Task {

        return taskService.saveFile(taskId, file)
    }
}