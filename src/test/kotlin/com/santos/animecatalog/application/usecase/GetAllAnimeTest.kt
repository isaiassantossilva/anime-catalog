package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@Suppress("SpellCheckingInspection")
@ExtendWith(MockKExtension::class)
class GetAllAnimeTest {
    @InjectMockKs
    private lateinit var getAllAnime: GetAllAnime
    @MockK
    private lateinit var animeRepository: AnimeRepository

    @BeforeEach
    fun setUp() {
        val animes = listOf(
            Anime("1", "DBZ", "Description", "ImageUrl"),
            Anime("2", "Naruto", "Description", "ImageUrl"),
            Anime("3", "One Piecie", "Description", "ImageUrl")
        )
        every { animeRepository.findAll() } returns animes
    }

    @Test
    fun `Deve retornar todos os animes cadastrados`() {
        val output = this.getAllAnime.execute()
        assertEquals(3, output.items.size)
        assertEquals("DBZ", output.items[0].name)
    }
}