package br.edu.pucpr.cursos_api.cursos

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CursoRepository : JpaRepository<Curso, Long> {

    fun existsByProfessorId(professorId: Long): Boolean

    @Query(
        """
            SELECT c FROM Curso c
            WHERE (:professor IS NULL OR LOWER(c.professor.nome) LIKE LOWER(concat('%', :professor, '%')))
              AND (:nome IS NULL OR LOWER(c.nome) LIKE LOWER(concat('%', :nome, '%')))
        """
    )
    fun search(
        @Param("professor") professor: String?,
        @Param("nome") nome: String?,
        sort: Sort,
    ): List<Curso>
}