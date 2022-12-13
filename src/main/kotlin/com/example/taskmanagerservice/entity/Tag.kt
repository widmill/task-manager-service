package com.example.taskmanagerservice.entity

import java.util.UUID
import javax.persistence.*

@Entity
class Tag(
    @Id
    @Column(name = "id")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "tag")
    var tag: String

) {
    override fun toString(): String {
        return "Tag(id=$id, tag='$tag')"
    }
}
