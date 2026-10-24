package br.edu.pucpr.cursos_api.alunos.dto

import br.edu.pucpr.cursos_api.alunos.Aluno
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class AlunoRequest(

    @field:NotBlank
    @field:Email
    var email: String?,

    @field:NotBlank
    var nome: String?,

    var ra: String?,

    @field:Past
    var dataNascimento: LocalDate?,
) {
    fun toAluno() = Aluno(
        email = email!!,
        nome = nome!!,
        ra = ra,
        dataNascimento = dataNascimento,
    )
}