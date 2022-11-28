package ru.serj.shorten.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.serj.shorten.domain.request.UrlRequest
import ru.serj.shorten.service.UrlService

@RestController
class TemplateController(
    private val urlService: UrlService
) {

    @GetMapping
    fun index(model: MutableMap<String?, Any?>): ModelAndView {
        model["title"] = "title"
        return ModelAndView("index", model)
    }

    @GetMapping("/short")
    fun displayUsers(model: MutableMap<String?, Any?>): ModelAndView? {
        return ModelAndView("short", model)
    }

    @PostMapping("/submitform")
    fun submitform(@ModelAttribute("url1") request: UrlRequest): ModelAndView {
        val response = urlService.process(request)
        val modelAndView = ModelAndView("short", "url1", response.urlShort)
        modelAndView.model["message"] = "Done!"
        return modelAndView
    }

    @GetMapping("/error")
    fun error(model: MutableMap<String?, Any?>): ModelAndView? {
        return ModelAndView("error", model)
    }
}