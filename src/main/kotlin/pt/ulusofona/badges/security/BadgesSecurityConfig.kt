package pt.ulusofona.badges.security

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

open class BadgesSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/public", "/login", "/loginFromDEISI", "/access-denied", "/error").permitAll()
                .antMatchers(HttpMethod.GET, "/teacher/**").hasRole("TEACHER")
                .antMatchers(HttpMethod.POST, "/teacher/**").hasRole("TEACHER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied.html")

    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**")
    }
}