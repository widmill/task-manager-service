package com.example.taskmanagerservice.service

import com.example.taskmanagerservice.entity.Tag
import com.example.taskmanagerservice.entity.Task
import com.example.taskmanagerservice.exception.EntityNotFoundException
import com.example.taskmanagerservice.repository.TagRepository
import com.example.taskmanagerservice.validator.FieldValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class TagService(
    private val tagRepository: TagRepository,
    private val taskService: TaskService,
) {
    private val log: Logger = LoggerFactory.getLogger(TagService::class.java)


    fun findTagWithTasks(tagId: UUID): Map<Tag, List<Task>?> {
        log.info("Searching for Tag with tasks for tag id $tagId")

        val tag: Optional<Tag> = tagRepository.findById(tagId)

        if (tag.isEmpty) {
            throw EntityNotFoundException("Тег с ID $tagId не был найден.")
        }

        val tasks: List<Task>? = taskService.findTasksByTagId(tagId)

        return mapOf(tag.get() to tasks)

    }


    fun createOrUpdateTag(savingTag: Tag): Tag {
        if (tagRepository.findById(savingTag.id).isEmpty) {

            FieldValidator.ifExist("tag", savingTag.tag)
            tagRepository.save(savingTag)

        } else {

            val updatingTag: Tag = tagRepository.getReferenceById(savingTag.id)
            updatingTag.tag = savingTag.tag
            tagRepository.save(updatingTag)
            return updatingTag

        }
        return savingTag
    }


    fun findById(id: UUID): Tag {
        val tag: Optional<Tag> = tagRepository.findById(id)

        if (tag.isEmpty) {
            throw EntityNotFoundException("Тег с ID $id не был найден.")
        }
        return tag.get()
    }


    fun deleteTagByIdWithTasks(tagId: UUID) {
        taskService.deleteTasksByTagId(tagId)

        log.info("Deleting tag with id $tagId")

        findById(tagId)

        tagRepository.deleteById(tagId)
    }
}