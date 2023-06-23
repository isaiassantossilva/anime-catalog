package com.santos.animecatalog.infra.repository

import com.santos.animecatalog.application.repository.AnimeRepository
import com.santos.animecatalog.domain.entity.Anime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Profile("dev-pg")
@Repository
class AnimePgRepository(
    private val animeJpaRepository: AnimeJpaRepository
) : AnimeRepository {
    override fun save(anime: Anime) {
        this.animeJpaRepository.save(AnimeJpaEntity(anime))
    }

    override fun findById(id: String): Optional<Anime> {
        return this.animeJpaRepository.findById(id).map { it.toAnime() }
    }

    override fun existsById(id: String): Boolean {
        return this.animeJpaRepository.existsById(id)
    }

    override fun findAll(): List<Anime> {
        return this.animeJpaRepository.findAll().map { it.toAnime() }
    }
}

interface AnimeJpaRepository : JpaRepository<AnimeJpaEntity, String>

@Entity(name = "Anime")
@Table(name = "anime")
data class AnimeJpaEntity(
    @Id
    @Column(name = "id")
    val id: String = "",

    @Column(name = "name", nullable = false)
    val name: String = "",

    @Column(name = "description", nullable = false)
    val description: String = "",

    @Column(name = "image_url", nullable = false)
    val imageUrl: String = ""
) {
    constructor(anime: Anime) : this(anime.id, anime.name, anime.description, anime.imageUrl)

    fun toAnime(): Anime {
        return Anime(id, name, description, imageUrl)
    }
}
