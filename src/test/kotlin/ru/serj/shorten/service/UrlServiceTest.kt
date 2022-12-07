package ru.serj.shorten.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.serj.shorten.domain.entity.UrlEntity
import ru.serj.shorten.repository.UrlDao
import java.time.Instant

@SpringBootTest
internal class UrlServiceTest {

    @MockkBean
    lateinit var urlDao: UrlDao

    @Autowired
    lateinit var urlService: UrlService

    @Test
    fun getLongUrl() {
        // given
        every { urlDao.getByShorten(any()) } returns listOf(
            UrlEntity(
                urlLong = "urlLong",
                urlShort = "urlShort",
                created = Instant.now()
            )
        )

        // when
        val longUrl = urlService.getLongUrl("urlShort")

        // then
        assertEquals("urlLong", longUrl, "long url differentiates ")
    }
}