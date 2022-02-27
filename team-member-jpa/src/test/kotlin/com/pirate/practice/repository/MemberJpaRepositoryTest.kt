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
}