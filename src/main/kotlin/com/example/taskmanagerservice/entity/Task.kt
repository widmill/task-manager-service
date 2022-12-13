package com.example.taskmanagerservice.entity

import org.hibernate.annotations.Type
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "task")
class Task(
    @Id
    @Column(name = "task_id")
    val taskId: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String?,

    @Column(name = "description")
    var description: String?,

    @Column(name = "creation_date")
    var creationDate: Timestamp? = Timestamp.valueOf(LocalDateTime.now()),

    @Column(name = "tag_id")
    var tagId: UUID,

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "file")
    var data: ByteArray?
) {
    override fun toString(): String {
        return "Task(task_id=$taskId, name='$name', description='$description', creationDate=$creationDate, tagId=$tagId)"
    }
}