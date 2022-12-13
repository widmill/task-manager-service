package com.example.taskmanagerservice.validator

import com.example.taskmanagerservice.exception.MissingDataException

object FieldValidator {

    @JvmStatic
    fun ifExist(key: String, value: Any?) {
        if (value == null) {
            throw MissingDataException("Поле \'$key\' не должно быть пустым.")
        }
    }
}