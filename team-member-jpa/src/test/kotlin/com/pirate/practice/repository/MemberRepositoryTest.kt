package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    @Test
    fun testMember() {
        val member = Member()
        member.username = "memberA"

        val savedMember = memberRepository.save(member)
        val findMember = memberRepository.findById(savedMember.id ?: 0).get()

        Assertions.assertThat(findMember.id).isEqualTo(savedMember.id)
        Assertions.assertThat(findMember.username).isEqualTo(savedMember.username)
        Assertions.assertThat(findMember).isEqualTo(savedMember)
    }
}