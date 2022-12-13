package com.example.taskmanagerservice.service

import com.example.taskmanagerservice.entity.Task
import com.example.taskmanagerservice.exception.EntityNotFoundException
import com.example.taskmanagerservice.repository.TagRepository
import com.example.taskmanagerservice.repository.TaskRepository
import com.example.taskmanagerservice.validator.FieldValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val tagRepository: TagRepository
) {
    private val log: Logger = LoggerFactory.getLogger(TaskService::class.java)


    fun findById(id: UUID): Task {
        log.info("Searching for task with id = $id")

        return taskRepository.findById(id).orElseThrow()
            ?: throw EntityNotFoundException("Задачи с ID $id не были найдены.")
    }

    fun findAll(): MutableList<Task> = taskRepository.findAllByOrderByCreationDate()

    fun findTasksByTagId(tagId: UUID): List<Task>? {
        log.info("Searching for tasks with tag id = $tagId")

        val tasks: List<Task> = taskRepository.findAllByTagIdOrderByCreationDate(tagId)

        if (tasks.isEmpty()) {
            log.info("Tasks for tag with id $tagId were not found.")
            return null
        }

        return tasks
    }

    fun createOrUpdateTask(savingTask: Task): Task {
        if (taskRepository.findById(savingTask.taskId).isEmpty) {

            log.info("Saving task = $savingTask")

            FieldValidator.ifExist("name", savingTask.name)
            FieldValidator.ifExist("tagId", savingTask.tagId)
            ifTagExist(savingTask.tagId)

            taskRepository.save(savingTask)


        } else {
            val updatingTask: Task = taskRepository.getReferenceById(savingTask.taskId)

            log.info("Updating task = $updatingTask")

            if (savingTask.name != null) updatingTask.name = savingTask.name
            if (savingTask.description != null) updatingTask.description = savingTask.description

            taskRepository.save(updatingTask)
            return updatingTask
        }
        return savingTask
    }

    fun deleteTaskById(id: UUID) {
        if (taskRepository.findById(id).isEmpty) {
            throw EntityNotFoundException("Задача с ID $id не была найдена.")
        }
        taskRepository.deleteById(id)
    }

    fun deleteTasksByTagId(tagId: UUID) {
        log.info("Deleting all tasks with tag id $tagId")

        taskRepository.deleteAllByTagId(tagId)
    }

    fun ifTagExist(tagId: UUID) {
        if (tagRepository.findById(tagId).isEmpty) {
            throw EntityNotFoundException("Тег с ID $tagId не был найден.")
        }
    }

    fun saveFile(taskId: UUID, file: MultipartFile): Task {

        val task: Task = taskRepository.getReferenceById(taskId)
        task.data = file.bytes
        taskRepository.save(task)

        return task
    }
}
