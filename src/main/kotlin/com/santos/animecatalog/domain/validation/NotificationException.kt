package com.santos.animecatalog.domain.validation

class NotificationException(message: String, val notification: Notification) : RuntimeException(message)