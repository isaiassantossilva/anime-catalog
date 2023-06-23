package com.santos.animecatalog.domain.entity

import com.santos.animecatalog.domain.validation.NotificationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
class AnimeTest {

    @Test
    fun `Deve criar um anime`() {
        val id = "1"
        val name = "Dragon Ball Z Dublado"
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = "http://url-storage.png"
        val anime = Anime(id, name, description, imageUrl)
        assertEquals(id, anime.id)
        assertEquals(name, anime.name)
        assertEquals(description, anime.description)
        assertEquals(imageUrl, anime.imageUrl)
    }

    @Test
    fun `Não deve criar um anime com campos vazios`() {
        val id = "1"
        val name = ""
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = ""
        val exception = assertThrows<NotificationException> {
            Anime(id, name, description, imageUrl)
        }
        assertEquals("Failed to create Anime", exception.message)
        val errors = exception.notification.errors
        assertEquals(2, errors.size)
        assertEquals("Name can not be empty", errors[0].message)
        assertEquals("ImageUrl can not be empty", errors[1].message)
    }
}