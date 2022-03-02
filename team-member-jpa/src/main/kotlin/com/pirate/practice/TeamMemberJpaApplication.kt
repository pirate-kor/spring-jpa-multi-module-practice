package com.pirate.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@EnableJpaAuditing
@SpringBootApplication
class TeamMemberJpaApplication {
    @Bean
    fun auditorProvier(): AuditorAware<String> = AuditorAware { Optional.of(UUID.randomUUID().toString()) }
}

fun main(args: Array<String>) {
    runApplication<TeamMemberJpaApplication>(*args)
}