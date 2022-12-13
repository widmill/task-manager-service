package com.example.taskmanagerservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.HttpURLConnection
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler() : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundHandler(ex: EntityNotFoundException): ResponseEntity<MutableMap<String, Any>> {
        val body: MutableMap<String, Any> = LinkedHashMap()

        body["message"] = ex.localizedMessage
        body["timestamp"] = LocalDateTime.now()
        body["code"] = HttpURLConnection.HTTP_NOT_FOUND

        return ResponseEntity<MutableMap<String, Any>>(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MissingDataException::class)
    fun missingDataHandler(ex: MissingDataException): ResponseEntity<MutableMap<String, Any>> {
        val body: MutableMap<String, Any> = LinkedHashMap()

        body["message"] = ex.localizedMessage
        body["timestamp"] = LocalDateTime.now()
        body["code"] = HttpURLConnection.HTTP_BAD_REQUEST

        return ResponseEntity<MutableMap<String, Any>>(body, HttpStatus.BAD_REQUEST)
    }
}