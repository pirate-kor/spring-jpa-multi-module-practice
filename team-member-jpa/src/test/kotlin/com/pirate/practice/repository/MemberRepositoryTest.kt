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

    @Test
    fun basicCRUD() {
        val member1 = Member()
        member1.username = "member1"
        val member2 = Member()
        member1.username = "member2"

        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMember1 = memberRepository.findById(member1.id ?: 0).get()
        val findMember2 = memberRepository.findById(member2.id ?: 0).get()
        Assertions.assertThat(findMember1).isEqualTo(member1)
        Assertions.assertThat(findMember2).isEqualTo(member2)

        val all = memberRepository.findAll()
        Assertions.assertThat(all.size).isEqualTo(2)

        val count = memberRepository.count()
        Assertions.assertThat(count).isEqualTo(2)

        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        Assertions.assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val memberA = Member()
        memberA.username = "AAA"
        memberA.age = 10
        val memberB = Member()
        memberA.username = "AAA"
        memberA.age = 20

        memberRepository.save(memberA)
        memberRepository.save(memberB)

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

        Assertions.assertThat(result.size).isEqualTo(1)
        Assertions.assertThat(result[0].username).isEqualTo("AAA")
        Assertions.assertThat(result[0].age).isEqualTo(20)
    }

}
