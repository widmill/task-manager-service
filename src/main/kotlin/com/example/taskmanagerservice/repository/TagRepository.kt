package com.example.taskmanagerservice.repository

import com.example.taskmanagerservice.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import javax.transaction.Transactional

@Transactional
interface TagRepository : JpaRepository<Tag, UUID>