package br.edu.pucpr.cursos_api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(private val jwt: Jwt) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val header = request.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            jwt.extract(header.removePrefix("Bearer ").trim())?.let {
                SecurityContextHolder.getContext().authentication = it
            }
        }
        filterChain.doFilter(request, response)
    }
}