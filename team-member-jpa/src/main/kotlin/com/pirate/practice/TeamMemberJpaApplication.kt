package com.pirate.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TeamMemberJpaApplication

fun main(args: Array<String>) {
    runApplication<TeamMemberJpaApplication>(*args)
}