package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import java.util.UUID

class CreateAnime(private val animeRepository: AnimeRepository) {
    fun execute(input: CreateAnimeInput): CreateAnimeOutput {
        val id = UUID.randomUUID().toString()
        val anime = input.run {
            Anime(id, name, description, imageUrl)
        }
        this.animeRepository.save(anime)
        return anime.run {
            CreateAnimeOutput(id, name)
        }
    }
}

class CreateAnimeInput(
    val name: String,
    val description: String,
    val imageUrl: String
)

class CreateAnimeOutput(
    val id: String,
    val name: String
)