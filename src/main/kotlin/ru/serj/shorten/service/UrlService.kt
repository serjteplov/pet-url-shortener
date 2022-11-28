package ru.serj.shorten.service

import org.springframework.stereotype.Component
import ru.serj.shorten.domain.request.UrlRequest
import ru.serj.shorten.domain.response.UrlResponse
import ru.serj.shorten.repository.UrlDao
import ru.serj.shorten.service.mapping.toResponse
import ru.serj.shorten.service.mapping.toUrlEntity
import java.security.SecureRandom

@Component
class UrlService(
    private val urlDao: UrlDao
) {
    fun process(urlRequest: UrlRequest): UrlResponse {
        val secureRandom = SecureRandom()
        secureRandom.setSeed(urlRequest.urlLong.toByteArray())
        val urlShort = secureRandom
            .ints(48, 122)
            .filter { i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
            .limit(10)
            .collect({ StringBuilder() }, { s, i -> s.appendCodePoint(i) }, { a, b -> a.append(b) })
            .toString().prependIndent("https://bit.ly/")
        val urlEntity = urlRequest.toUrlEntity(urlShort)
        return urlDao.persist(urlEntity).toResponse()
    }
}

