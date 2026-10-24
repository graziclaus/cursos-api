package br.edu.pucpr.cursos_api.cursos

import br.edu.pucpr.cursos_api.common.SortDir
import br.edu.pucpr.cursos_api.cursos.dto.CursoRequest
import br.edu.pucpr.cursos_api.matriculas.MatriculaRepository
import br.edu.pucpr.cursos_api.professores.Professor
import br.edu.pucpr.cursos_api.professores.ProfessorService
import br.edu.pucpr.cursos_api.professores.exception.ProfessorInativoException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CursoService(

    val repository: CursoRepository,
    val professorService: ProfessorService,
    val matriculaRepository: MatriculaRepository,

    ) {

    private val log = LoggerFactory.getLogger(CursoService::class.java)

    private val sortFields = setOf("nome", "cargaHoraria", "vagas")

    fun insert(req: CursoRequest): Curso {

        val professor = findProfessor(req.professorId!!)

        return repository.save(
            Curso(
                nome = req.nome!!,
                descricao = req.descricao,
                cargaHoraria = req.cargaHoraria!!,
                vagas = req.vagas!!,
                professor = professor,
            )
        ).also { log.info("Curso criado: id={}, professorId={}", it.id, professor.id) }
    }

    fun search(professor: String?, nome: String?, sortBy: String?, dir: SortDir): List<Curso> {

        val field = sortBy ?: "nome"

        if (field !in sortFields)

            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Campo de ordenação inválido. Use: ${sortFields.joinToString(", ")}"
            )

        val sort = if (dir == SortDir.ASC) Sort.by(field) else Sort.by(field).descending()
        return repository.search(professor?.ifBlank { null }, nome?.ifBlank { null }, sort)
    }

    fun findByIdOrNull(id: Long) = repository.findByIdOrNull(id)

    fun update(id: Long, req: CursoRequest): Curso {

        val curso = findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Curso $id não encontrado")
        val professor = findProfessor(req.professorId!!)

        curso.nome = req.nome!!
        curso.descricao = req.descricao
        curso.cargaHoraria = req.cargaHoraria!!
        curso.vagas = req.vagas!!
        curso.professor = professor

        return repository.save(curso).also { log.info("Curso atualizado: id={}", id) }
    }

    fun deleteById(id: Long) {

        if (!repository.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Curso $id não encontrado")

        if (matriculaRepository.existsByCursoId(id)) throw ResponseStatusException(HttpStatus.CONFLICT, "Curso possui matrículas e não pode ser excluído")

        repository.deleteById(id)
        log.info("Curso excluído: id={}", id)
    }

    private fun findProfessor(id: Long): Professor {

        val professor = professorService.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Professor $id não encontrado")

        if (!professor.ativo) throw ProfessorInativoException()

        return professor
    }
}