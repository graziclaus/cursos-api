package br.edu.pucpr.cursos_api.users

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UserTable")
class User(

    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false, length = 72)
    var password: String,

    @Column(nullable = false)
    var name: String,
)