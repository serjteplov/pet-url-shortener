package ru.serj.shorten.it

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import ru.serj.shorten.controller.MustacheController

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MustacheControllerIntegrationTest(
) {
    @Autowired
    lateinit var mustacheController: MustacheController

    @Autowired
    lateinit var context: WebApplicationContext

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindToController(mustacheController) // alternate: bindToApplicationContext(context)
            .build()
    }

    @Test
    fun submitForm_shouldBeSubmitted() {
        webTestClient
            .post()
            .uri("/submitform")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue("urlLong=\"https://www.yandex.ru/map/emhkl34hmdhk\"")
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }
}