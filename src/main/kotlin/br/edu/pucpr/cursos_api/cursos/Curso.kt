package br.edu.pucpr.cursos_api.cursos

import br.edu.pucpr.cursos_api.professores.Professor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Curso(

    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    var descricao: String? = null,

    @Column(nullable = false)
    var cargaHoraria: Int,

    @Column(nullable = false)
    var vagas: Int,

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id")
    var professor: Professor,
)