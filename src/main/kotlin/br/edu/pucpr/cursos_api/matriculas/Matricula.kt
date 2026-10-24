package br.edu.pucpr.cursos_api.matriculas

import br.edu.pucpr.cursos_api.alunos.Aluno
import br.edu.pucpr.cursos_api.cursos.Curso
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["aluno_id", "curso_id"])])
class Matricula(

    @Id
    @GeneratedValue
    var id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    var aluno: Aluno,

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    var curso: Curso,

    @Column(nullable = false)
    var dataMatricula: LocalDate = LocalDate.now(),

    var nota: Double? = null,
)