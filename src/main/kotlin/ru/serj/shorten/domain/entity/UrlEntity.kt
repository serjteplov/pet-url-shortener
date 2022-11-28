package ru.serj.shorten.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("url_entity")
data class UrlEntity (
    @Id
    val id: UUID? = null,
    val urlLong: String,
    val urlShort: String,
    val created: Instant,
    val updated: Instant? = null
)