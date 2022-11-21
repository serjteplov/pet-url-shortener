package ru.serj.shorten.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
            .authorizeRequests().anyRequest().permitAll()
        return http.build()
    }

//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.authorizeRequests()
//            .requestMatchers("/css/**").permitAll()
//            .requestMatchers("/user/**").hasAuthority("ROLE_USER")
//            .and()
//            .formLogin().loginPage("/log-in")
//        return http.build()
//    }

}