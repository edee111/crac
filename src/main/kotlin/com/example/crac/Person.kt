package com.example.crac

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Person(
    @Id
    @GeneratedValue
    var id: Int? = null,
    var firstName: String
)