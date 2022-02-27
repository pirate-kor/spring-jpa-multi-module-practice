package com.pirate.practice.repository

import com.pirate.practice.entity.Team
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class TeamJpaRepository(
    @PersistenceContext
    private val em: EntityManager
) {

    fun save(team: Team): Team {
        em.persist(team)
        return team
    }

    fun delete(team: Team) {
        em.remove(team)
    }

    fun findAll(): MutableList<Team> {
        return em.createQuery("select t from Team t", Team::class.java).resultList
    }

    fun findById(id: Long): Optional<Team> {
        val team = em.find(Team::class.java, id)
        return Optional.ofNullable(team)
    }

    fun count(): Long {
        return em.createQuery("select count(m) from Member m", Long::class.java).singleResult
    }

}