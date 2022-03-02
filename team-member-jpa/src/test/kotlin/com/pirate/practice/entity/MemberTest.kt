package com.pirate.practice.entity

import com.pirate.practice.repository.MemberRepository
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
    private val em: EntityManager,
    private val memberRepository: MemberRepository
) {

    @Test
    fun testEntity() {
        val teamA = Team("teamA")
        val teamB = Team("teamB")

        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member("member1", 10, teamA)
        val member2 = Member("member2", 15, teamA)
        val member3 = Member("member3", 17, teamB)
        val member4 = Member("member4", 18, teamB)

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

    @Test
    fun JpaEventBaseEntity() {
        val member = Member("member1")
        memberRepository.save(member)

        Thread.sleep(100)
        member.username = "member2"

        em.flush()
        em.clear()

        val findMember = memberRepository.findById(member.id!!).get()
        println("member.createdDate = ${findMember.createdDate}")
        println("member.updatedDate = ${findMember.lastModifiedDate}")
        println("member.createdBy = ${findMember.createdBy}")
        println("member.updatedBy = ${findMember.lastModifiedBy}")
    }

}