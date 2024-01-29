package com.example.crac

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.lang.Thread.sleep

@Component
internal class SomeSpringBean {

    @PostConstruct
    fun initialize() {
        repeat(10) {
            println("Initializing SomeSpringBean $it...")
            sleep(100)
        }
    }

    fun getHello() = "Hello"
}