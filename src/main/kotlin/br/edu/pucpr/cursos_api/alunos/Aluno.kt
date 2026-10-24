package br.edu.pucpr.cursos_api.alunos

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class Aluno(

    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(unique = true)
    var ra: String? = null,

    var dataNascimento: LocalDate? = null,
    var telefone: String? = null,
)