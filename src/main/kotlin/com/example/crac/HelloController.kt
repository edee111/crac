package com.example.crac

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class HelloController(
    private val personService: PersonService,
) {

    @GetMapping
    fun hello() = personService.getHello()
}