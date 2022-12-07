package ru.serj.shorten.repository

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import ru.serj.shorten.domain.entity.UrlEntity
import java.sql.Timestamp
import java.time.Instant
import java.util.*

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
        if (keyHolder.keyList.isEmpty()) return urlEntity
        println("keyHolder id = ${keyHolder.keyList[0]["id"]}")

        return urlEntity.copy(
            id = keyHolder.keyList[0]["id"] as UUID
        )
    }

    fun getByShorten(shorten: String): List<UrlEntity> {
        val source = MapSqlParameterSource("urlShort", shorten)
        return jdbcTemplate.query(SELECT_BY_SHORTEN_URL, source) { rs, _ ->
            return@query UrlEntity(
                id = UUID.fromString(rs.getString("id")),
                urlLong = rs.getString("url_long") as String,
                urlShort = rs.getString("url_short") as String,
                created = (rs.getObject("created") as Timestamp).toInstant()
            )
        }
    }

    companion object {
        const val INSERT_SHORTEN_URL =
            """
                INSERT INTO url_entity (url_long, url_short, created)
                VALUES (:urlLong, :urlShort, :created)
            """
        const val SELECT_BY_SHORTEN_URL =
            """
                SELECT id, url_long, url_short, created 
                FROM url_entity
                WHERE url_short = :urlShort
            """
    }
}