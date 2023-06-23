package com.santos.animecatalog.infra.http

import com.santos.animecatalog.domain.validation.Error
import com.santos.animecatalog.domain.validation.NotFoundException
import com.santos.animecatalog.domain.validation.NotificationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotificationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNotificationException(exception: NotificationException): ExceptionResponse {
        return ExceptionResponse(exception.message, exception.notification.errors)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(exception: NotFoundException): ExceptionResponse {
        return ExceptionResponse(exception.message, exception.notification.errors)
    }
}

class ExceptionResponse(
    val message: String? = "",
    val errors: List<Error>
)