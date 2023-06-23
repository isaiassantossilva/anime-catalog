package com.santos.animecatalog.domain.validation

class Notification {
    private val errorsList = mutableListOf<Error>()

    val hasErrors get() = this.errors.isNotEmpty()

    val errors get() = this.errorsList.toList()

    fun append(error: Error): Notification {
        this.errorsList.add(error)
        return this
    }
}