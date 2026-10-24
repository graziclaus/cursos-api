package br.edu.pucpr.cursos_api.users.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:NotBlank
    @field:Email
    var email: String?,

    @field:NotBlank
    var password: String?,
)