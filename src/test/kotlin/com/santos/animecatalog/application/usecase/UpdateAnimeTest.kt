package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import com.santos.animecatalog.domain.validation.NotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
class UpdateAnimeTest {
    private lateinit var updateAnime: UpdateAnime
    private lateinit var animeRepository: AnimeRepository

    @BeforeEach
    fun setUp() {
        this.animeRepository = object : AnimeRepository {
            private val animes = mutableMapOf(
                "1" to Anime("1", "DBZ", "description", "imageUrl"),
                "3" to Anime("3", "Naruto", "description", "imageUrl")
            )

            override fun save(anime: Anime) {
                if (anime.id == "3") throw IllegalStateException("Repository error")
                this.animes[anime.id] = anime
            }

            override fun findById(id: String): Optional<Anime> {
                return Optional.ofNullable(animes[id])
            }

            override fun existsById(id: String): Boolean {
                return this.animes.containsKey(id)
            }

            override fun findAll(): List<Anime> {
                return this.animes.values.toList()
            }
        }

        this.updateAnime = UpdateAnime(this.animeRepository)
    }

    @Test
    fun `Deve atualizar um anime completo`() {
        val id = "1"
        val name = "Dragon Ball Z Dublado Updated"
        val description = "Goku está mais velho e se casou com Chichi... Updated"
        val imageUrl = "http://url-storage.png Updated"
        val input = UpdateAnimeInput(id, name, description, imageUrl)
        val output = this.updateAnime.execute(input)
        assertEquals(id, output.id)
        assertEquals(name, output.name)
    }

    @Test
    fun `Deve atualizar um anime parcialmente`() {
        val id = "1"
        val description = "É um anime sobre Goku e seus amigos..."
        val input = UpdateAnimeInput(id = id, description = description)
        val output = this.updateAnime.execute(input)
        assertEquals(id, output.id)
        assertEquals("DBZ", output.name)
        assertEquals(description, this.animeRepository.findById(id).get().description)
    }

    @Test
    fun `Deve lançar uma exeption quando não encontrar o anime`() {
        val id = "2"
        val name = "Dragon Ball Z Dublado Updated"
        val description = "Goku está mais velho e se casou com Chichi... Updated"
        val imageUrl = "http://url-storage.png Updated"
        val input = UpdateAnimeInput(id, name, description, imageUrl)

        val exception = assertThrows<NotFoundException> {
            this.updateAnime.execute(input)
        }

        assertEquals("Anime does not exists", exception.message)
        assertEquals("Could not find anime with id 2", exception.notification.errors[0].message)
    }

    @Test
    fun `Deve lançar um erro genérico quando for persistir o anime`() {

        val id = "3"
        val name = "Dragon Ball Z Dublado Updated"
        val description = "Goku está mais velho e se casou com Chichi... Updated"
        val imageUrl = "http://url-storage.png Updated"
        val input = UpdateAnimeInput(id, name, description, imageUrl)

        val exception = assertThrows<IllegalStateException> {
            this.updateAnime.execute(input)
        }

        assertEquals("Repository error", exception.message)
    }
}