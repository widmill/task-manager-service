package com.example.taskmanagerservice.repository

import com.example.taskmanagerservice.entity.Task
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import javax.transaction.Transactional

@Transactional
interface TaskRepository : JpaRepository<Task, UUID> {
    fun findAllByTagIdOrderByCreationDate(id: UUID): MutableList<Task>

    fun findAllByOrderByCreationDate(): MutableList<Task>

    fun deleteAllByTagId(id: UUID)
}