package com.santos.animecatalog.infra.http

import com.santos.animecatalog.application.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/anime")
class AnimeController(
    private val createAnime: CreateAnime,
    private val getAnimeById: GetAnimeById,
    private val updateAnime: UpdateAnime,
    private val getAllAnime: GetAllAnime
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAnime(@RequestBody body: CreateAnimeInput): CreateAnimeOutput {
        return this.createAnime.execute(body)
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateAnime(@PathVariable id: String, @RequestBody body: UpdateAnimeInput): UpdateAnimeOutput {
        body.id = id
        return this.updateAnime.execute(body)
    }

    @GetMapping("/{id}")
    fun getAnimeById(@PathVariable id: String): GetAnimeByIdOutput {
        return this.getAnimeById.execute(id)
    }

    @GetMapping
    fun getAllAnime(): GetAllAnimeOutput {
        return this.getAllAnime.execute()
    }
}