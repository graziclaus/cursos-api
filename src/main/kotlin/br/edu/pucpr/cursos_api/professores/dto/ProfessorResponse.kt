package br.edu.pucpr.cursos_api.professores.dto

import br.edu.pucpr.cursos_api.professores.Professor

data class ProfessorResponse(

    var id: Long,
    var nome: String,
    var email: String,
    var titulacao: String?,
    var areaAtuacao: String?,
    var ativo: Boolean,
) {
    constructor(professor: Professor) : this(
        id = professor.id!!,
        nome = professor.nome,
        email = professor.email,
        titulacao = professor.titulacao,
        areaAtuacao = professor.areaAtuacao,
        ativo = professor.ativo,
    )
}