package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.validation.NotificationException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
@ExtendWith(MockKExtension::class)
class CreateAnimeTest {
    @InjectMockKs
    private lateinit var createAnime: CreateAnime
    @MockK
    private lateinit var animeRepository: AnimeRepository

    @BeforeEach
    fun setUp() {
        every { animeRepository.save(any()) } just runs
    }

    @Test
    fun `Deve criar um anime`() {
        val name = "Dragon Ball Z Dublado"
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = "http://url-storage.png"
        val input = CreateAnimeInput(name, description, imageUrl)
        val output = this.createAnime.execute(input)
        assertTrue(output.id.isNotEmpty())
        assertEquals(name, output.name)
    }

    @Test
    fun `Não deve criar um anime com qualquer campo vazio`() {
        val name = ""
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = "http://url-storage.png"
        val input = CreateAnimeInput(name, description, imageUrl)
        val exception = assertThrows<NotificationException> {
            this.createAnime.execute(input)
        }
        assertEquals("Failed to create Anime", exception.message)
        assertEquals("Name can not be empty", exception.notification.errors[0].message)
    }

    @Test
    fun `Deve tratar uma exception quando o repository lançar um erro`() {
        every { animeRepository.save(any()) } throws IllegalStateException("Generic error")
        val name = "Dragon Ball Z Dublado"
        val description = "Goku está mais velho e se casou com Chichi..."
        val imageUrl = "http://url-storage.png"
        val input = CreateAnimeInput(name, description, imageUrl)
        val exception = assertThrows<IllegalStateException> {
            this.createAnime.execute(input)
        }
        assertEquals("Generic error", exception.message)
        verify(exactly = 1) { animeRepository.save(any()) }
    }
}