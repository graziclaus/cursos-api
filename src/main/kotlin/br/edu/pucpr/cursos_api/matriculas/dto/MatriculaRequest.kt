package br.edu.pucpr.cursos_api.matriculas.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class MatriculaRequest(

    @field:NotNull
    @field:Min(1)
    var alunoId: Long?,
)