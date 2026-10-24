package br.edu.pucpr.cursos_api.matriculas

import br.edu.pucpr.cursos_api.alunos.AlunoService
import br.edu.pucpr.cursos_api.cursos.CursoService
import br.edu.pucpr.cursos_api.cursos.exception.CursoLotadoException
import br.edu.pucpr.cursos_api.matriculas.exception.MatriculaDuplicadaException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MatriculaService(

    val repository: MatriculaRepository,
    val alunoService: AlunoService,
    val cursoService: CursoService,
) {

    private val log = LoggerFactory.getLogger(MatriculaService::class.java)

    fun insert(cursoId: Long, alunoId: Long): Matricula {

        val curso = cursoService.findByIdOrNull(cursoId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Curso $cursoId não encontrado")

        val aluno = alunoService.findByIdOrNull(alunoId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno $alunoId não encontrado")

        if (repository.existsByAlunoIdAndCursoId(alunoId, cursoId)) {

            log.warn("Matrícula duplicada: alunoId={}, cursoId={}", alunoId, cursoId)
            throw MatriculaDuplicadaException()
        }

        if (repository.countByCursoId(cursoId) >= curso.vagas) {

            log.warn("Curso lotado: cursoId={}", cursoId)
            throw CursoLotadoException()
        }

        return repository.save(Matricula(aluno = aluno, curso = curso)).also { log.info("Matrícula realizada: alunoId={}, cursoId={}", alunoId, cursoId) }
    }

    fun delete(cursoId: Long, alunoId: Long) {

        val matricula = repository.findByAlunoIdAndCursoId(alunoId, cursoId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada")
        repository.delete(matricula)
        log.info("Matrícula cancelada: alunoId={}, cursoId={}", alunoId, cursoId)
    }

    fun updateNota(cursoId: Long, alunoId: Long, nota: Double): Matricula {

        val matricula = repository.findByAlunoIdAndCursoId(alunoId, cursoId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada")
        matricula.nota = nota
        return repository.save(matricula).also { log.info("Nota lançada: alunoId={}, cursoId={}, nota={}", alunoId, cursoId, nota) }
    }

    fun findByCurso(cursoId: Long): List<Matricula> {

        if (cursoService.findByIdOrNull(cursoId) == null) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Curso $cursoId não encontrado")
        return repository.findByCursoId(cursoId)
    }

    fun findByAluno(alunoId: Long): List<Matricula> {

        if (alunoService.findByIdOrNull(alunoId) == null) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno $alunoId não encontrado")
        return repository.findByAlunoId(alunoId)
    }
}