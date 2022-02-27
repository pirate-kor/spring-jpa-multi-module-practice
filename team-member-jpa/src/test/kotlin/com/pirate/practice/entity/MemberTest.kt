package com.pirate.practice.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberTest @Autowired constructor(
    @PersistenceContext
    private val em: EntityManager
) {

    @Test
    fun testEntity() {
        val teamA = Team()
        teamA.name = "teamA"

        val teamB = Team()
        teamB.name = "teamB"

        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member()
        member1.username = "member1"
        member1.age = 10
        member1.team = teamA

        val member2 = Member()
        member2.username = "member2"
        member2.age = 15
        member2.team = teamA

        val member3 = Member()
        member3.username = "member3"
        member3.age = 17
        member3.team = teamB

        val member4 = Member()
        member4.username = "member4"
        member4.age = 18
        member4.team = teamB

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        em.flush()
        em.clear()

        val members = em.createQuery("select m from Member m", Member::class.java).resultList

        for (mem in members) {
            println("member = $mem")
            println("member.team = ${mem.team}")
        }
    }

}