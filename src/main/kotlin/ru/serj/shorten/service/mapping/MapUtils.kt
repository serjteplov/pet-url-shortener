package ru.serj.shorten.service.mapping

import ru.serj.shorten.domain.entity.UrlEntity
import ru.serj.shorten.domain.request.UrlRequest
import ru.serj.shorten.domain.response.UrlResponse
import java.time.Instant

fun UrlRequest.toUrlEntity(urlShort: String): UrlEntity =
    UrlEntity(
        urlLong = urlLong,
        urlShort = urlShort,
        created = Instant.now()
    )

fun UrlEntity.toResponse(): UrlResponse =
    UrlResponse(
        urlShort = this.urlShort
    )
