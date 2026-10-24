package br.edu.pucpr.cursos_api.alunos

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlunoRepository : JpaRepository<Aluno, Long> {

    fun findByEmail(email: String): Aluno?
    fun findByRa(ra: String): Aluno?

}