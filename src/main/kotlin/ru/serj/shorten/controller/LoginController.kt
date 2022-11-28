package ru.serj.shorten.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import ru.serj.shorten.domain.LoginDto

/*
* Данный контроллер создан с целью показать как при использовании mustache программируется страница логина
* в spring security. Смотри закомментаренный код в SecurityConfig.
* Полная версия тут: https://github.com/AlessioCuoccio-IA/LoginUtenteJDBCPostgres/blob
* /2cae8d3dc8ee736d8318f9bf86c4e6db1e76a142/src/main/java/LoginUtenteJDBCPostgres/HomeController.java
* */

@Controller
class LoginController {
    @GetMapping("/login")
    fun login(model: Model): String? {
        model.addAttribute("formLogin", LoginDto())
        return "login"
    }

    @PostMapping("/login")
    fun loginPost(
        @ModelAttribute("formLogin") formLogin: LoginDto?,
        // WARN: BindingResult *must* immediately follow the Command.
        // https://stackoverflow.com/a/29883178/1626026
        bindingResult: BindingResult?,
        model: Model?,
        ra: RedirectAttributes
    ): String? {

        println("Login avvenuto con successo")
        return "redirect:/loginSuccess"

    }
}