package br.edu.pucpr.cursos_api.professores.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ProfessorInativoException :

    RuntimeException("Professor inativo não pode receber cursos")