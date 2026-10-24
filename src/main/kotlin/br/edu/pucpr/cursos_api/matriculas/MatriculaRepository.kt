package br.edu.pucpr.cursos_api.matriculas

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatriculaRepository : JpaRepository<Matricula, Long> {

    fun existsByAlunoIdAndCursoId(alunoId: Long, cursoId: Long): Boolean
    fun findByAlunoIdAndCursoId(alunoId: Long, cursoId: Long): Matricula?
    fun countByCursoId(cursoId: Long): Long
    fun findByCursoId(cursoId: Long): List<Matricula>
    fun findByAlunoId(alunoId: Long): List<Matricula>
    fun existsByAlunoId(alunoId: Long): Boolean
    fun existsByCursoId(cursoId: Long): Boolean
}