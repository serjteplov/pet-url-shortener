package ru.serj.shorten.it

import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import ru.serj.shorten.controller.UseCaseController
import ru.serj.shorten.service.UrlService

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncomingControllerIntegrationTest(
) {
    @Autowired
    lateinit var useCaseController: UseCaseController

    @SpykBean
    lateinit var urlService: UrlService

    @Autowired
    lateinit var context: WebApplicationContext

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(useCaseController) // alternate: bindToApplicationContext(context)
            .build()
    }

    @Test
    fun shortUrl_shouldBeRedirected() {
        every {
            urlService.getLongUrl("111222")
        } returns "https://www.yandex.ru"

        webTestClient
            .get()
            .uri("/111222")
            .exchange()
            .expectStatus().is3xxRedirection
            .expectBody().isEmpty
    }
}