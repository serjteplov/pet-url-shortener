package ru.serj.shorten.it

import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ru.serj.shorten.domain.request.UrlRequest
import ru.serj.shorten.domain.response.UrlResponse
import ru.serj.shorten.service.UrlService

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerMockMvcTest {

    lateinit var mockMvc: MockMvc

    @SpykBean
    lateinit var urlService: UrlService

    @Autowired
    lateinit var context: WebApplicationContext

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @Test
    fun shortUrl_shouldBeRedirected() {
        every {
            urlService.getLongUrl("abc")
        } returns "https://www.yandex.ru"

        mockMvc.perform(
            get("/abc")
                .with(user("1").password("1"))
        ).andExpect (
            status().is3xxRedirection
        )
    }

    @Test
    fun shortForm_shouldBeSubmitted() {
        every {
            urlService.process(UrlRequest("https://www.yandex.ru/map/emhkl34hmdhk"))
        } returns UrlResponse("shurl")

        mockMvc.perform(
            post("/submitform")
                .with(user("1").password("1"))
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("urlLong=\"https://www.yandex.ru/map/emhkl34hmdhk\"")
        ).andExpect(
            status().isOk
        ).andExpect(
            view().name("short")
        ).andExpect(
            model().attributeExists("message")
        )
    }

    @Test
    fun testMockMvc() {
        mockMvc.perform(
            formLogin()
                .user("1")
                .password("1")
        ).andExpect(
            authenticated().withUsername("1")
        )
    }
}