package com.example.taskmanagerservice.controller

import com.example.taskmanagerservice.entity.Tag
import com.example.taskmanagerservice.entity.Task
import com.example.taskmanagerservice.service.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/tags")
class TagController(@Autowired val tagService: TagService) {

    /**
     * @return Возвращает тег со всеми прикрепленными к нему задачами.
     * @param id тега.
     */
    @GetMapping("/{id}")
    fun findTagWithTasks(@PathVariable id: UUID): Map<Tag, List<Task>?> {
        return tagService.findTagWithTasks(id)
    }

    /**
     * Создать или обновить тег. Чтобы обновить нужно в тело запроса добавить id тега.
     * @param tag который хотим создать.
     * @return созданный тег.
     */
    @PostMapping
    fun createOrUpdateTag(@RequestBody tag: Tag): Tag {
        return tagService.createOrUpdateTag(tag)
    }

    /**
     * Удаляет тег со всеми приклепленными задачами.
     * @param tagId удаляемого тега.
     */
    @DeleteMapping("/{tagId}")
    fun deleteTagByIdWithTasks(@PathVariable tagId: UUID) {
        tagService.deleteTagByIdWithTasks(tagId)
    }
}