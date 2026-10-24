package br.edu.pucpr.cursos_api.matriculas.dto

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull

data class NotaRequest(

    @field:NotNull
    @field:DecimalMin("0.0")
    @field:DecimalMax("10.0")
    var nota: Double?,
)