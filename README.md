# Sobre o trabalho -- Cursos API

╰┈➤ API REST para gerenciamento de alunos, professores, cursos e matrículas,
desenvolvida com Kotlin, Spring Boot, Spring Data JPA, H2 e JWT. Foi feito para
matéria de Back-End: Web Services, do professor Vinícius Godoy de Mendonça, por 
Grazielle Claus de Almeida.

## Integrantes

⤿ Grazielle Claus de Almeida

## Requisitos

⤿ Java 21
⤿ Gradle Wrapper (já incluído no projeto)

## Configuração no Spring Initializr

⤿ Dentro do Spring Initializr, foi usado a seguinte sequência:

Project
↳ Gradle - Kotlin

Language
↳ Kotlin

Spring Boot
↳ 4.1.1

Project Metadata
↳ Group ➜ br.edu.pucpr
↳ Artifact ➜ cursos-api
↳ Package name ➜ br.edu.pucpr.cursos_api

Packaging
↳ Jar

Configuration
↳ YAML

Java
↳ 21

Dependencies
↳ Validation
↳ Spring Web
↳ SpringDoc OpenAPI
↳ Spring Data JPA
↳ Spring Security
↳ H2 Database

## Principais endpoints

⤿ A ordem de construção das entidades foi: Aluno, Professor, Curso e Matrícula.

### Alunos

```text
GET    /api/alunos
GET    /api/alunos/{id}
POST   /api/alunos
PUT    /api/alunos/{id}
DELETE /api/alunos/{id}
```

### Professores

```text
GET    /api/professores
GET    /api/professores/{id}
POST   /api/professores
PUT    /api/professores/{id}
DELETE /api/professores/{id}
```

### Cursos

```text
GET    /api/cursos
GET    /api/cursos/{id}
POST   /api/cursos
PUT    /api/cursos/{id}
DELETE /api/cursos/{id}
```

O endpoint de consulta de cursos aceita filtros e ordenação:

```text
GET /api/cursos?nome=Backend&professor=Maria&sortBy=cargaHoraria&sortDir=DESC
```

Filtros disponíveis:

- `nome`
- `professor`

Campos disponíveis para ordenação:

- `nome`
- `cargaHoraria`
- `vagas`

### Matrículas

```text
POST   /api/cursos/{cursoId}/matriculas
DELETE /api/cursos/{cursoId}/matriculas/{alunoId}
PUT    /api/cursos/{cursoId}/matriculas/{alunoId}/nota
GET    /api/cursos/{cursoId}/matriculas
GET    /api/alunos/{alunoId}/matriculas
```

## Relacionamentos

⤿ Um professor pode ministrar vários cursos. Cada curso possui um professor. (1:N)
⤿ Um aluno pode estar matricualdo em vários cursos, e um curso pode possuir
  vários alunos. (N:M com entidade intermediária).
⤿ `Matricula` é a entidade intermediária entre `Aluno` e `Curso` e armazena
  a data da matrícula e a nota. 

## Validações e regras de negócio

O projeto valida:

⤿ emails duplicados;
⤿ matrícula duplicada;
⤿ limite de vagas do curso;
⤿ professor inativo;
⤿ parâmetros de ordenação inválidos;
⤿ dados obrigatórios dos requests.

Também há logs nos principais serviços e tratamento global de exceções.

## Testes

⤿ Os endpoints foram testados manualmente com o Insomnia.

## Área com vídeo

⤿ <!-- ... -->