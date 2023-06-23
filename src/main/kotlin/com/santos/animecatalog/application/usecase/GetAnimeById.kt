package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.validation.Error
import com.santos.animecatalog.domain.validation.NotFoundException
import com.santos.animecatalog.domain.validation.Notification

class GetAnimeById(private val animeRepository: AnimeRepository) {
    fun execute(id: String): GetAnimeByIdOutput {
        val anime = this.animeRepository.findById(id).orElseThrow {
                NotFoundException(
                    "Anime does not exists",
                    Notification().append(Error("Could not find anime with id $id"))
                )
            }
        return anime.run {
            GetAnimeByIdOutput(this.id, this.name, this.description, this.imageUrl)
        }
    }
}

class GetAnimeByIdOutput(
    val id: String, val name: String, val description: String, val imageUrl: String
)