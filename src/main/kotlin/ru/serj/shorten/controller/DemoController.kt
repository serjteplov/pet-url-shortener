package ru.serj.shorten.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.serj.shorten.domain.Article
import java.time.Instant
import java.util.stream.IntStream
import kotlin.streams.toList


@RestController
class DemoController {

    @GetMapping
    fun index(model: MutableMap<String?, Any?>): ModelAndView {
        model["title"] = "title"
        return ModelAndView("index", model)
    }

    @GetMapping("/article")
    fun displayArticle(model: MutableMap<String?, Any?>): ModelAndView? {
        val articles: List<Article> = IntStream.range(0, 1)
            .mapToObj {
                Article(
                    title = "Book",
                    publishDate = Instant.now(),
                    author = "Leo Tolstoy",
                    body = "War and Peace"
                )
            }.toList()
        model["articles"] = articles
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
}