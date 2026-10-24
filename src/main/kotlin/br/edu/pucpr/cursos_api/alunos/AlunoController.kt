package br.edu.pucpr.cursos_api.alunos

import br.edu.pucpr.cursos_api.alunos.dto.AlunoRequest
import br.edu.pucpr.cursos_api.alunos.dto.AlunoResponse
import br.edu.pucpr.cursos_api.common.SortDir
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/alunos")
@Validated
class AlunoController(private val service: AlunoService) {

    @GetMapping
    fun list(@RequestParam sortDir: String?) =
        SortDir.findOrThrow(sortDir ?: "ASC")
            .let { service.findAll(it) }
            .map { AlunoResponse(it) }
            .let { ResponseEntity.ok(it) }

    @PostMapping
    fun insert(@Valid @RequestBody req: AlunoRequest) =
        AlunoResponse(service.insert(req.toAluno()))
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping("/{id}")
    fun getById(@Min(1) @PathVariable id: Long) =
        service.findByIdOrNull(id)
            ?.let { AlunoResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}")
    fun update(@Min(1) @PathVariable id: Long, @Valid @RequestBody req: AlunoRequest) =
        AlunoResponse(service.update(id, req))
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    fun deleteById(@Min(1) @PathVariable id: Long): ResponseEntity<Void> =
        service.deleteById(id).let { ResponseEntity.ok().build() }
}