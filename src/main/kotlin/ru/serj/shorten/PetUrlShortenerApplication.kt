package ru.serj.shorten

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetUrlShortenerApplication

fun main(args: Array<String>) {
    runApplication<PetUrlShortenerApplication>(*args)
}
