package br.edu.pucpr.cursos_api.cursos.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class CursoLotadoException :

    RuntimeException("Curso sem vagas")