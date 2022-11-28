package ru.serj.shorten.repository

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import ru.serj.shorten.domain.entity.UrlEntity
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

@Component
class UrlDao(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
    fun persist(urlEntity: UrlEntity): UrlEntity {
        val source = MapSqlParameterSource("urlLong", urlEntity.urlLong)
        source.addValue("urlShort", urlEntity.urlShort)
        source.addValue("created", Timestamp.from(Instant.now()))
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(INSERT_SHORTEN_URL, source, keyHolder)
        println("keyHolder id = ${keyHolder.keyList[0]["id"]}")
        return urlEntity.copy(
            id = keyHolder.keyList[0]["id"] as UUID
        )
    }

    companion object {
        const val INSERT_SHORTEN_URL =
            """
                INSERT INTO url_entity (url_long, url_short, created)
                VALUES (:urlLong, :urlShort, :created)
            """
    }
}