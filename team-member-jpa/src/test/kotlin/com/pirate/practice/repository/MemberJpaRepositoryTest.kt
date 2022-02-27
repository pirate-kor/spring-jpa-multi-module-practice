package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.assertj.core.api.Assertions.assertThat
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
}