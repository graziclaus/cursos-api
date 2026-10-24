package br.edu.pucpr.cursos_api.matriculas

import br.edu.pucpr.cursos_api.matriculas.dto.MatriculaRequest
import br.edu.pucpr.cursos_api.matriculas.dto.MatriculaResponse
import br.edu.pucpr.cursos_api.matriculas.dto.NotaRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class MatriculaController(private val service: MatriculaService) {

    @PostMapping("/cursos/{cursoId}/matriculas")
    fun insert(
        @Min(1) @PathVariable cursoId: Long,
        @Valid @RequestBody req: MatriculaRequest,
    ) = MatriculaResponse(service.insert(cursoId, req.alunoId!!))
        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @DeleteMapping("/cursos/{cursoId}/matriculas/{alunoId}")
    fun delete(
        @Min(1) @PathVariable cursoId: Long,
        @Min(1) @PathVariable alunoId: Long,
    ): ResponseEntity<Void> =
        service.delete(cursoId, alunoId).let { ResponseEntity.ok().build() }

    @PutMapping("/cursos/{cursoId}/matriculas/{alunoId}/nota")
    fun updateNota(
        @Min(1) @PathVariable cursoId: Long,
        @Min(1) @PathVariable alunoId: Long,
        @Valid @RequestBody req: NotaRequest,
    ) = MatriculaResponse(service.updateNota(cursoId, alunoId, req.nota!!))
        .let { ResponseEntity.ok(it) }

    @GetMapping("/cursos/{cursoId}/matriculas")
    fun listByCurso(@Min(1) @PathVariable cursoId: Long) =
        service.findByCurso(cursoId)
            .map { MatriculaResponse(it) }
            .let { ResponseEntity.ok(it) }

    @GetMapping("/alunos/{alunoId}/matriculas")
    fun listByAluno(@Min(1) @PathVariable alunoId: Long) =
        service.findByAluno(alunoId)
            .map { MatriculaResponse(it) }
            .let { ResponseEntity.ok(it) }
}