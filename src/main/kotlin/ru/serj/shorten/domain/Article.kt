package ru.serj.shorten.domain

import java.time.Instant

data class Article (
    private val title: String,
    private val publishDate: Instant,
    private val author: String,
    private val body: String
)
