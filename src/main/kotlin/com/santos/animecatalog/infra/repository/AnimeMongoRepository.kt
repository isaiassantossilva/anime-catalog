package com.santos.animecatalog.infra.repository

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import jakarta.persistence.Id
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Profile("dev-mongo")
@Repository
class AnimeMongoRepository(
    private val animeMongoDBRepository: AnimeMongoDbRepository
) : AnimeRepository {
    override fun save(anime: Anime) {
        this.animeMongoDBRepository.save(AnimeMongoEntity(anime))
    }

    override fun findById(id: String): Optional<Anime> {
        return this.animeMongoDBRepository.findById(id).map { it.toAnime() }
    }

    override fun existsById(id: String): Boolean {
        return this.animeMongoDBRepository.existsById(id)
    }

    override fun findAll(): List<Anime> {
        return this.animeMongoDBRepository.findAll().map { it.toAnime() }
    }
}

interface AnimeMongoDbRepository : MongoRepository<AnimeMongoEntity, String>

@Document(collection = "anime")
data class AnimeMongoEntity(
    @Id
    val id: String = "",

    val name: String = "",

    val description: String = "",

    val imageUrl: String = ""
) {
    constructor(anime: Anime) : this(anime.id, anime.name, anime.description, anime.imageUrl)

    fun toAnime(): Anime {
        return Anime(id, name, description, imageUrl)
    }
}
