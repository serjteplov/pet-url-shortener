package ru.serj.shorten.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.serj.shorten.service.UrlService
import javax.servlet.http.HttpServletResponse

@RestController
class IncomingRequestController(
    private val urlService: UrlService
) {

    @GetMapping("/{shorten}")
    fun incoming(
        @PathVariable shorten: String,
        httpServletResponse: HttpServletResponse
    ) {
        httpServletResponse.sendRedirect(urlService.getLongUrl(shorten))
    }
}