package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.type.BigIntegerType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
internal class MemberJpaRepositoryTest @Autowired constructor(
    private val memberJpaRepository: MemberJpaRepository
) {
    @Test
    fun testMember() {
        val member = Member()
        member.username = "memberA"

        val savedMember = memberJpaRepository.save(member)
        val findMember = memberJpaRepository.find(savedMember.id ?: 0)

        assertThat(findMember.id).isEqualTo(savedMember.id)
        assertThat(findMember.username).isEqualTo(savedMember.username)
        assertThat(findMember).isEqualTo(savedMember)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member()
        member1.username = "member1"
        val member2 = Member()
        member1.username = "member2"

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val findMember1 = memberJpaRepository.findById(member1.id ?: 0).get()
        val findMember2 = memberJpaRepository.findById(member2.id ?: 0).get()
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        val all = memberJpaRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        val count = memberJpaRepository.count()
        assertThat(count).isEqualTo(2)

        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

        val deletedCount = memberJpaRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThan() {
        val memberA = Member()
        memberA.username = "AAA"
        memberA.age = 10
        val memberB = Member()
        memberA.username = "AAA"
        memberA.age = 20

        memberJpaRepository.save(memberA)
        memberJpaRepository.save(memberB)

        val result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].username).isEqualTo("AAA")
        assertThat(result[0].age).isEqualTo(20)
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member()
        member1.username = "AAA"
        member1.age = 10

        val member2 = Member()
        member2.username = "BBB"
        member2.age = 20

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsername("AAA")
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

}