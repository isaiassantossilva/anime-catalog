package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository

class GetAllAnime(private val animeRepository: AnimeRepository) {
    fun execute(): GetAllAnimeOutput {
        val animes = this.animeRepository.findAll()
        return GetAllAnimeOutput(animes.map { GetAllAnimeItem(it.id, it.name, it.imageUrl) })
    }
}

class GetAllAnimeOutput(
    val items: List<GetAllAnimeItem>
)

class GetAllAnimeItem(
    val id: String,
    val name: String,
    val imageUrl: String
)