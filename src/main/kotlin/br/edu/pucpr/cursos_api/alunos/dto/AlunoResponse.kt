package br.edu.pucpr.cursos_api.alunos.dto

import br.edu.pucpr.cursos_api.alunos.Aluno
import java.time.LocalDate

data class AlunoResponse(

    var id: Long,
    var email: String,
    var nome: String,
    var ra: String?,
    var dataNascimento: LocalDate?,
) {
    constructor(aluno: Aluno) : this(
        id = aluno.id!!,
        email = aluno.email,
        nome = aluno.nome,
        ra = aluno.ra,
        dataNascimento = aluno.dataNascimento,
    )
}