package com.santos.animecatalog.application.repository

import com.santos.animecatalog.domain.entity.Anime
import java.util.Optional

interface AnimeRepository {
    fun save(anime: Anime)
    fun findById(id: String): Optional<Anime>
    fun existsById(id: String): Boolean
    fun findAll(): List<Anime>
}
