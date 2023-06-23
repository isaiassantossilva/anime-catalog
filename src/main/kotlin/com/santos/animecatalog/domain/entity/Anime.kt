package com.santos.animecatalog.domain.entity

import com.santos.animecatalog.domain.validation.Error
import com.santos.animecatalog.domain.validation.Notification
import com.santos.animecatalog.domain.validation.NotificationException

class Anime(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
) {
    init {
        this.validate()
    }

    private fun validate() {
        val notification = Notification().apply {
            if (id.isEmpty()) append(Error("Id can not be empty"))
            if (name.isEmpty()) append(Error("Name can not be empty"))
            if (description.isEmpty()) append(Error("Description can not be empty"))
            if (imageUrl.isEmpty()) append(Error("ImageUrl can not be empty"))
        }
        if (notification.hasErrors)
            throw NotificationException("Failed to create Anime", notification)
    }
}