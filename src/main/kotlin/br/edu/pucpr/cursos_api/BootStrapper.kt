package br.edu.pucpr.cursos_api

import br.edu.pucpr.cursos_api.alunos.Aluno
import br.edu.pucpr.cursos_api.alunos.AlunoRepository
import br.edu.pucpr.cursos_api.cursos.Curso
import br.edu.pucpr.cursos_api.cursos.CursoRepository
import br.edu.pucpr.cursos_api.professores.Professor
import br.edu.pucpr.cursos_api.professores.ProfessorRepository
import br.edu.pucpr.cursos_api.users.User
import br.edu.pucpr.cursos_api.users.UserRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class BootStrapper(
    val professorRepository: ProfessorRepository,
    val alunoRepository: AlunoRepository,
    val cursoRepository: CursoRepository,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
) : ApplicationListener<ContextRefreshedEvent> {

    private val log = LoggerFactory.getLogger(BootStrapper::class.java)

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        val maria = professorRepository.findByEmail("maria@pucpr.br")
            ?: professorRepository.save(
                Professor(
                    nome = "Maria Souza",
                    email = "maria@pucpr.br",
                    titulacao = "Mestre",
                    areaAtuacao = "Programação",
                )
            ).also { log.info("Professor inicial criado: {}", it.email) }

        if (alunoRepository.findByEmail("joao@pucpr.br") == null) {

            alunoRepository.save(
                Aluno(
                    nome = "João Silva",
                    email = "joao@pucpr.br",
                    ra = "2024001",
                )
            )
            log.info("Aluno inicial criado: joao@pucpr.br")
        }

        if (cursoRepository.count() == 0L) {

            cursoRepository.save(
                Curso(
                    nome = "Desenvolvimento de Serviços",
                    descricao = "Backend com Kotlin e Spring Boot",
                    cargaHoraria = 60,
                    vagas = 30,
                    professor = maria,
                )
            )
            log.info("Curso inicial criado")
        }

        if (userRepository.findByEmail("admin@pucpr.br") == null) {

            userRepository.save(
                User(
                    name = "Administrador",
                    email = "admin@pucpr.br",
                    password = passwordEncoder.encode("admin123")!!,
                )
            )
            log.info("Usuário admin inicial criado")
        }
    }
}