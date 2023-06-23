package com.santos.animecatalog.domain.validation

class NotFoundException(message: String, val notification: Notification) : RuntimeException(message)