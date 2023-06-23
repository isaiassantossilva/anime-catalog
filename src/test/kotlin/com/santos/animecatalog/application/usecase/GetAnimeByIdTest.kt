package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import com.santos.animecatalog.domain.validation.NotFoundException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
@ExtendWith(MockKExtension::class)
class GetAnimeByIdTest {
    @InjectMockKs
    private lateinit var getAnimeById: GetAnimeById
    @MockK
    private lateinit var animeRepository: AnimeRepository

    @Test
    fun `Deve obter um anime pelo id`() {
        every { animeRepository.findById(any()) } returns Optional.of(Anime("1", "DBZ", "Description", "ImageUrl"))

        val output = this.getAnimeById.execute("1")
        assertEquals("1", output.id)
        assertEquals("DBZ", output.name)
        assertEquals("Description", output.description)
        assertEquals("ImageUrl", output.imageUrl)

        verify(exactly = 1) { animeRepository.findById(any()) }
    }

    @Test
    fun `Deve lançar uma NotFoundException quando não encontrar o anime`() {
        every { animeRepository.findById(any()) } returns Optional.empty()

        val exception = assertThrows<NotFoundException> {
            this.getAnimeById.execute("1")
        }

        assertEquals("Anime does not exists", exception.message)
        assertEquals("Could not find anime with id 1", exception.notification.errors[0].message)

        verify(exactly = 1) { animeRepository.findById(any()) }
    }
}