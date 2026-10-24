package br.edu.pucpr.cursos_api.security

import br.edu.pucpr.cursos_api.users.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonSerializer
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

data class UserToken(val id: Long, val name: String)

@Component
class Jwt(@Value("\${jwt.secret}") secret: String) {

    private val log = LoggerFactory.getLogger(Jwt::class.java)
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun createToken(user: User): String =
        UserToken(id = user.id ?: -1L, name = user.name).let {
            Jwts.builder()
                .signWith(key)
                .serializeToJsonWith(JacksonSerializer())
                .setIssuedAt(utcNow().toDate())
                .setExpiration(utcNow().plusHours(EXPIRE_HOURS).toDate())
                .setIssuer(ISSUER)
                .setSubject(it.id.toString())
                .addClaims(mapOf(USER_FIELD to it))
                .compact()
        }

    fun extract(token: String): Authentication? =
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
            UsernamePasswordAuthenticationToken(claims.subject, null, emptyList())
        } catch (e: Exception) {
            log.warn("Token inválido: {}", e.message)
            null
        }

    companion object {
        private const val ISSUER = "Cursos API"
        private const val USER_FIELD = "user"
        private const val EXPIRE_HOURS = 24L

        private fun ZonedDateTime.toDate(): Date = Date.from(this.toInstant())

        private fun utcNow(): ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC)
    }
}
