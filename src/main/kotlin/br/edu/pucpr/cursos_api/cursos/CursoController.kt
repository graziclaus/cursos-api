package br.edu.pucpr.cursos_api.cursos

import br.edu.pucpr.cursos_api.common.SortDir
import br.edu.pucpr.cursos_api.cursos.dto.CursoRequest
import br.edu.pucpr.cursos_api.cursos.dto.CursoResponse
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
@RequestMapping("/cursos")
@Validated
class CursoController(private val service: CursoService) {

    @GetMapping
    fun list(

        @RequestParam professor: String?,
        @RequestParam nome: String?,
        @RequestParam sortBy: String?,
        @RequestParam sortDir: String?,

    ) = SortDir.findOrThrow(sortDir ?: "ASC")
        .let { service.search(professor, nome, sortBy, it) }
        .map { CursoResponse(it) }
        .let { ResponseEntity.ok(it) }

    @PostMapping
    fun insert(@Valid @RequestBody req: CursoRequest) =
        CursoResponse(service.insert(req)).let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping("/{id}")
    fun getById(@Min(1) @PathVariable id: Long) =
        service.findByIdOrNull(id)
            ?.let { CursoResponse(it) }
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}")
    fun update(@Min(1) @PathVariable id: Long, @Valid @RequestBody req: CursoRequest) =
        CursoResponse(service.update(id, req))
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    fun deleteById(@Min(1) @PathVariable id: Long): ResponseEntity<Void> =
        service.deleteById(id).let { ResponseEntity.ok().build() }
}