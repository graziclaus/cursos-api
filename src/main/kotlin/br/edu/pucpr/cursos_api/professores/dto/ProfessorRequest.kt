package br.edu.pucpr.cursos_api.professores.dto

import br.edu.pucpr.cursos_api.professores.Professor
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ProfessorRequest(

    @field:NotBlank
    var nome: String?,

    @field:NotBlank
    @field:Email
    var email: String?,

    var titulacao: String?,
    var areaAtuacao: String?,
    var ativo: Boolean?,
) {
    fun toProfessor() = Professor(
        nome = nome!!,
        email = email!!,
        titulacao = titulacao,
        areaAtuacao = areaAtuacao,
        ativo = ativo ?: true,
    )
}