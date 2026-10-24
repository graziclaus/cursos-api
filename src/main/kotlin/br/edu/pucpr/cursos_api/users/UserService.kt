package br.edu.pucpr.cursos_api.users

import br.edu.pucpr.cursos_api.security.Jwt
import br.edu.pucpr.cursos_api.users.dto.LoginResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(

    val repository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwt: Jwt,
) {

    private val log = LoggerFactory.getLogger(UserService::class.java)

    fun login(email: String, password: String): LoginResponse {
        val user = repository.findByEmail(email)
        if (user == null || !passwordEncoder.matches(password, user.password)) {
            log.warn("Falha de login: email={}", email)
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos")
        }
        log.info("Login realizado: id={}", user.id)
        return LoginResponse(jwt.createToken(user))
    }
}