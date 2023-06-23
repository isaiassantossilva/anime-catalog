package com.santos.animecatalog.infra.config.usecase

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.application.usecase.CreateAnime
import com.santos.animecatalog.application.usecase.GetAllAnime
import com.santos.animecatalog.application.usecase.GetAnimeById
import com.santos.animecatalog.application.usecase.UpdateAnime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig(
    private val animeRepository: AnimeRepository
) {
    @Bean
    fun createAnime() = CreateAnime(animeRepository)

    @Bean
    fun getAnimeById() = GetAnimeById(animeRepository)

    @Bean
    fun updateAnime() = UpdateAnime(animeRepository)

    @Bean
    fun getAllAnime() = GetAllAnime(animeRepository)
}