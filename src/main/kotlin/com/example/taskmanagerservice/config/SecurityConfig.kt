package com.example.taskmanagerservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/tasks").permitAll()
            .antMatchers(HttpMethod.GET, "/tags").permitAll()
            .antMatchers(HttpMethod.POST, "/tasks/**").hasRole("admin")
            .antMatchers(HttpMethod.POST, "/tags/**").hasRole("admin")
            .antMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("admin")
            .antMatchers(HttpMethod.DELETE, "/tags/**").hasRole("admin")

        return http.build()
    }

    @Bean
    fun userDetailService(): InMemoryUserDetailsManager {
        val user: UserDetails = User.withUsername("user")
            .password("{noop}password")
            .roles("admin")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}