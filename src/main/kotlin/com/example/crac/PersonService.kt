package com.example.crac

import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.lang.Thread.sleep

@Component
internal class PersonService(
    private val personRepository: PersonRepository,

) {

    @PostConstruct
    fun initialize() {
        repeat(10) {
            println("Initializing PersonService $it (it simulates spring context initialization)...")
            sleep(100)
        }
    }

    @Transactional
    fun getHello(): String {
        personRepository.save(Person(firstName = "Johny${System.nanoTime()}"))
        val persons = personRepository.findAll()
        return "Hello ${persons.joinToString { it.firstName }}!"
    }
}

@Repository
interface PersonRepository : JpaRepository<Person, Int>