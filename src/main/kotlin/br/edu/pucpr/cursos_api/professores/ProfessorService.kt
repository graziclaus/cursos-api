package br.edu.pucpr.cursos_api.professores

import br.edu.pucpr.cursos_api.common.SortDir
import br.edu.pucpr.cursos_api.cursos.CursoRepository
import br.edu.pucpr.cursos_api.common.exception.EmailJaCadastradoException
import br.edu.pucpr.cursos_api.professores.dto.ProfessorRequest
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProfessorService(

    val repository: ProfessorRepository,
    val cursoRepository: CursoRepository,

) {

    private val log = LoggerFactory.getLogger(ProfessorService::class.java)

    fun insert(professor: Professor): Professor {
        if (repository.findByEmail(professor.email) != null) throw EmailJaCadastradoException("professor")

        return repository.save(professor).also { log.info("Professor criado: id={}", it.id) }
    }

    fun findAll(dir: SortDir): List<Professor> =

        if (dir == SortDir.ASC) repository.findAll(Sort.by("nome"))

        else repository.findAll(Sort.by("nome").descending())

    fun findByIdOrNull(id: Long) = repository.findByIdOrNull(id)

    fun update(id: Long, req: ProfessorRequest): Professor {

        val professor = findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Professor $id não encontrado")

        val porEmail = repository.findByEmail(req.email!!)
        if (porEmail != null && porEmail.id != id) throw EmailJaCadastradoException("professor")

        professor.nome = req.nome!!
        professor.email = req.email!!
        professor.titulacao = req.titulacao
        professor.areaAtuacao = req.areaAtuacao
        professor.ativo = req.ativo ?: true

        return repository.save(professor).also { log.info("Professor atualizado: id={}", id) }
    }

    fun deleteById(id: Long) {
        if (!repository.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Professor $id não encontrado")

        if (cursoRepository.existsByProfessorId(id)) throw ResponseStatusException(HttpStatus.CONFLICT, "Professor possui cursos e não pode ser excluído")

        repository.deleteById(id)
        log.info("Professor excluído: id={}", id)
    }
}