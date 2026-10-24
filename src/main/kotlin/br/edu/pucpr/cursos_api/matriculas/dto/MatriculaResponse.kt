package br.edu.pucpr.cursos_api.matriculas.dto

import br.edu.pucpr.cursos_api.matriculas.Matricula
import java.time.LocalDate

data class MatriculaResponse(

    var id: Long,
    var alunoId: Long,
    var alunoNome: String,
    var cursoId: Long,
    var cursoNome: String,
    var dataMatricula: LocalDate,
    var nota: Double?,
) {
    constructor(matricula: Matricula) : this(
        id = matricula.id!!,
        alunoId = matricula.aluno.id!!,
        alunoNome = matricula.aluno.nome,
        cursoId = matricula.curso.id!!,
        cursoNome = matricula.curso.nome,
        dataMatricula = matricula.dataMatricula,
        nota = matricula.nota,
    )
}