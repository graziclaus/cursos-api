package br.edu.pucpr.cursos_api.cursos.dto

import br.edu.pucpr.cursos_api.cursos.Curso

data class CursoResponse(

    var id: Long,
    var nome: String,
    var descricao: String?,
    var cargaHoraria: Int,
    var vagas: Int,
    var professorId: Long,
    var professorNome: String,

) {

    constructor(curso: Curso) : this(
        id = curso.id!!,
        nome = curso.nome,
        descricao = curso.descricao,
        cargaHoraria = curso.cargaHoraria,
        vagas = curso.vagas,
        professorId = curso.professor.id!!,
        professorNome = curso.professor.nome,
    )
}