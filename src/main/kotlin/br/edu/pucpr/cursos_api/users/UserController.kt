package br.edu.pucpr.cursos_api.users

import br.edu.pucpr.cursos_api.users.dto.LoginRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Validated
class UserController(private val service: UserService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody req: LoginRequest) =
        service.login(req.email!!, req.password!!).let { ResponseEntity.ok(it) }
}