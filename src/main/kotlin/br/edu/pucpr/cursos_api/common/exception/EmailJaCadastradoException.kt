package br.edu.pucpr.cursos_api.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class EmailJaCadastradoException(entidade: String) :

    RuntimeException("Já existe $entidade com este e-mail")