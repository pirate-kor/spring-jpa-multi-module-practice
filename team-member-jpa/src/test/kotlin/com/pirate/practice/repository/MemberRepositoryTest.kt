package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import com.pirate.practice.entity.Team
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository
) {
    @Test
    fun testMember() {
        val member = Member("memberA")

        val savedMember = memberRepository.save(member)
        val findMember = memberRepository.findById(savedMember.id ?: 0).get()

        assertThat(findMember.id).isEqualTo(savedMember.id)
        assertThat(findMember.username).isEqualTo(savedMember.username)
        assertThat(findMember).isEqualTo(savedMember)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member("member1")
        val member2 = Member("member2")

        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMember1 = memberRepository.findById(member1.id ?: 0).get()
        val findMember2 = memberRepository.findById(member2.id ?: 0).get()
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        val all = memberRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        val count = memberRepository.count()
        assertThat(count).isEqualTo(2)

        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        assertThat(deletedCount).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val memberA = Member("AAA", 10)
        val memberB = Member("AAA", 20)

        memberRepository.save(memberA)
        memberRepository.save(memberB)

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].username).isEqualTo("AAA")
        assertThat(result[0].age).isEqualTo(20)
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUsername("AAA")
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun testQuery() {
        val member1 = Member()
        member1.username = "AAA"
        member1.age = 10

        val member2 = Member()
        member2.username = "BBB"
        member2.age = 20

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUser("AAA", 10)
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun findUsernames() {
        val member1 = Member()
        member1.username = "AAA"
        member1.age = 10

        val member2 = Member()
        member2.username = "BBB"
        member2.age = 20

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUsernames()
        assertThat(result[0]).isEqualTo(member1.username)
        assertThat(result[1]).isEqualTo(member2.username)
    }

    @Test
    fun findMemberDto() {
        val team = Team()
        team.name = "teamA"

        teamRepository.save(team)

        val member1 = Member()
        member1.username = "AAA"
        member1.age = 10
        member1.team = team

        memberRepository.save(member1)

        val result = memberRepository.findMemberDto()
        assertThat(result[0].username).isEqualTo(member1.username)
        assertThat(result[0].teamName).isEqualTo(member1.team?.name)
    }

    @Test
    fun findByNames() {
        val member1 = Member()
        member1.username = "AAA"
        member1.age = 10

        val member2 = Member()
        member2.username = "BBB"
        member2.age = 20

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByNames(listOf("AAA", "BBB"))
        assertThat(result[0]).isEqualTo(member1)
        assertThat(result[1]).isEqualTo(member2)
    }

}
