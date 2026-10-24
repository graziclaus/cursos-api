package br.edu.pucpr.cursos_api.security

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(private val jwt: Jwt) {

    private val log = LoggerFactory.getLogger(SecurityConfig::class.java)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeHttpRequests {
                authorize("/users/login", permitAll)
                authorize(HttpMethod.POST, "/**", authenticated)
                authorize(HttpMethod.PUT, "/**", authenticated)
                authorize(HttpMethod.DELETE, "/**", authenticated)
                authorize(anyRequest, permitAll)
            }
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { request, response, _ ->
                    log.warn("Acesso sem autenticação válida: {} {}", request.method, request.requestURI)
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.contentType = "application/problem+json"
                    response.writer.write(
                        """{"status":401,"title":"Unauthorized","detail":"Token ausente ou inválido"}"""
                    )
                }
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtTokenFilter(jwt))
        }
        return http.build()
    }
}