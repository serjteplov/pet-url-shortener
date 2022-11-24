package ru.serj.shorten.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests { auth ->
                auth.anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
//            .formLogin().loginPage("/login").permitAll().and()
            .logout().permitAll()

        return http.build()
    }

}