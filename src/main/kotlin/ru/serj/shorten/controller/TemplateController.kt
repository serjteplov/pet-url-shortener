package ru.serj.shorten.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
class TemplateController {

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
    fun submitform(@ModelAttribute("url1") url1: String): ModelAndView {
        println("######### url = $url1")
        val modelAndView = ModelAndView("short", "url1", url1)
        modelAndView.model["message"] = "Done!"
        return modelAndView
    }

    @GetMapping("/error")
    fun error(model: MutableMap<String?, Any?>): ModelAndView? {
        return ModelAndView("error", model)
    }
}