package br.edu.pucpr.cursos_api.matriculas.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class MatriculaDuplicadaException :

    RuntimeException("Aluno já matriculado neste curso")