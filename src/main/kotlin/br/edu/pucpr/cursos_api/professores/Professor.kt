package br.edu.pucpr.cursos_api.professores

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Professor(

    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false, unique = true)
    var email: String,

    var titulacao: String? = null,
    var areaAtuacao: String? = null,

    @Column(nullable = false)
    var ativo: Boolean = true,
)