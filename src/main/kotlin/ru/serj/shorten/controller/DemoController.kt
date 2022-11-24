package ru.serj.shorten.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
class DemoController {

    @GetMapping
    fun index(model: MutableMap<String?, Any?>): ModelAndView {
        model["title"] = "title"
        return ModelAndView("index", model)
    }

    @GetMapping("/short")
    fun displayUsers(model: MutableMap<String?, Any?>): ModelAndView? {
        model["users"] = "Paste url here:"
        return ModelAndView("short", model)
    }

    @PostMapping("/submitform")
    fun submitform(@ModelAttribute("text1") text1: String): ModelAndView {
        println("######### text1 = ${text1}")
        val modelAndView = ModelAndView("short", "text1", text1)
        modelAndView.model["users"] = "Done!\n Paste another url:"
        return modelAndView
    }

    @GetMapping("/error")
    fun error(model: MutableMap<String?, Any?>): ModelAndView? {
        return ModelAndView("error", model)
    }
}