package br.edu.pucpr.cursos_api.alunos

import br.edu.pucpr.cursos_api.alunos.dto.AlunoRequest
import br.edu.pucpr.cursos_api.common.SortDir
import br.edu.pucpr.cursos_api.common.exception.EmailJaCadastradoException
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AlunoService(val repository: AlunoRepository) {

    private val log = LoggerFactory.getLogger(AlunoService::class.java)

    fun insert(aluno: Aluno): Aluno {

        if (repository.findByEmail(aluno.email) != null) throw EmailJaCadastradoException("aluno")

        if (aluno.ra?.let { repository.findByRa(it) } != null) throw ResponseStatusException(HttpStatus.CONFLICT, "Já existe aluno com este RA")

        return repository.save(aluno).also { log.info("Aluno criado: id={}", it.id) }
    }

    fun findAll(dir: SortDir): List<Aluno> =
        if (dir == SortDir.ASC) repository.findAll(Sort.by("nome"))
        else repository.findAll(Sort.by("nome").descending())

    fun findByIdOrNull(id: Long) = repository.findByIdOrNull(id)

    fun update(id: Long, req: AlunoRequest): Aluno {

        val aluno = findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno $id não encontrado")

        val porEmail = repository.findByEmail(req.email!!)

        if (porEmail != null && porEmail.id != id) throw EmailJaCadastradoException("aluno")

        val porRa = req.ra?.let { repository.findByRa(it) }

        if (porRa != null && porRa.id != id) throw ResponseStatusException(HttpStatus.CONFLICT, "Já existe aluno com este RA")

        aluno.nome = req.nome!!
        aluno.email = req.email!!
        aluno.ra = req.ra
        aluno.dataNascimento = req.dataNascimento

        return repository.save(aluno).also { log.info("Aluno atualizado: id={}", id) }
    }

    fun deleteById(id: Long) {

        if (!repository.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno $id não encontrado")

        repository.deleteById(id)
        log.info("Aluno excluído: id={}", id)
    }
}