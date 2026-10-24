package br.edu.pucpr.cursos_api.cursos.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero

data class CursoRequest(

    @field:NotBlank
    var nome: String?,

    var descricao: String?,

    @field:NotNull
    @field:Positive
    var cargaHoraria: Int?,

    @field:NotNull
    @field:PositiveOrZero
    var vagas: Int?,

    @field:NotNull
    @field:Min(1)
    var professorId: Long?,
)