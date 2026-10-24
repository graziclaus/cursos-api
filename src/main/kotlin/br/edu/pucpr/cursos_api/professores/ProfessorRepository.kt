package br.edu.pucpr.cursos_api.professores

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfessorRepository : JpaRepository<Professor, Long> {

    fun findByEmail(email: String): Professor?

}