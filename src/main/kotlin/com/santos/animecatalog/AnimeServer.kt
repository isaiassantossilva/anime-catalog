package com.santos.animecatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnimeServer

fun main(args: Array<String>) {
    runApplication<AnimeServer>(*args)
}