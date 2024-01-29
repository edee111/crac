package com.example.crac

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SomeController(
    private val someSpringBean: SomeSpringBean,
) {

    @GetMapping
    fun hello() = someSpringBean.getHello()
}