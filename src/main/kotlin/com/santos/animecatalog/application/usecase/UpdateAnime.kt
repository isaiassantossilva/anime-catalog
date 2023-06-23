package com.santos.animecatalog.application.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import com.santos.animecatalog.domain.validation.NotFoundException
import com.santos.animecatalog.domain.validation.Notification
import com.santos.animecatalog.domain.validation.Error

class UpdateAnime(private val animeRepository: AnimeRepository) {
    fun execute(input: UpdateAnimeInput): UpdateAnimeOutput {
        val animeFromDb = animeRepository.findById(input.id)
            .orElseThrow { NotFoundException("Anime does not exists", Notification().append(Error("Could not find anime with id ${input.id}"))) }
        val name = input.name.ifEmpty { animeFromDb.name }
        val description = input.description.ifEmpty { animeFromDb.description }
        val imageUrl = input.imageUrl.ifEmpty { animeFromDb.imageUrl }
        val animeUpdated = Anime(input.id, name, description, imageUrl)
        this.animeRepository.save(animeUpdated)
        return UpdateAnimeOutput(animeUpdated.id, animeUpdated.name)
    }
}

class UpdateAnimeInput(
    var id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = ""
)

class UpdateAnimeOutput(
    val id: String,
    val name: String
)