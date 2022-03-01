package com.pirate.practice.repository

import com.pirate.practice.dto.MemberDto
import com.pirate.practice.entity.Member
import com.pirate.practice.entity.Team
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class MemberRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
    private val entityManager: EntityManager
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
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUser("AAA", 10)
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun findUsernames() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUsernames()
        assertThat(result[0]).isEqualTo(member1.username)
        assertThat(result[1]).isEqualTo(member2.username)
    }

    @Test
    fun findMemberDto() {
        val team = Team("teamA")
        teamRepository.save(team)

        val member1 = Member("AAA", 10, team)
        memberRepository.save(member1)

        val result = memberRepository.findMemberDto()
        assertThat(result[0].username).isEqualTo(member1.username)
        assertThat(result[0].teamName).isEqualTo(member1.team?.name)
    }

    @Test
    fun findByNames() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByNames(listOf("AAA", "BBB"))
        assertThat(result[0]).isEqualTo(member1)
        assertThat(result[1]).isEqualTo(member2)
    }

    @Test
    fun paging() {
        memberRepository.save(Member("member1", 10))
        memberRepository.save(Member("member2", 10))
        memberRepository.save(Member("member3", 10))
        memberRepository.save(Member("member4", 10))
        memberRepository.save(Member("member5", 10))
        memberRepository.save(Member("member6", 10))

        val age = 10
        val pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "username")

        val pages = memberRepository.findByAge(age, pageRequest)
        val toMap = pages.map { member -> MemberDto(member.id ?: 0, member.username, "") }
        val contents = pages.content

        assertThat(contents.size).isEqualTo(3)
        assertThat(pages.totalElements).isEqualTo(6)
        assertThat(pages.number).isEqualTo(0)
        assertThat(pages.totalPages).isEqualTo(2)
        assertThat(pages.isFirst).isTrue
        assertThat(pages.hasNext()).isTrue

        val slices = memberRepository.findSliceByAge(age, pageRequest)
        val sliceContents = slices.content

        assertThat(sliceContents.size).isEqualTo(3)
        assertThat(slices.number).isEqualTo(0)
        assertThat(slices.isFirst).isTrue
        assertThat(slices.hasNext()).isTrue
    }

    @Test
    fun bulkUpdate() {
        memberRepository.save(Member("member1", 10))
        memberRepository.save(Member("member2", 19))
        memberRepository.save(Member("member3", 20))
        memberRepository.save(Member("member4", 21))
        memberRepository.save(Member("member5", 40))

        val resultCount = memberRepository.bulkAgePlus(20)
        // 벌크연산 이후에 영속성 컨텍스트의 내용을 DB에 반영
        // 아니면, clearAutomatically를 true로 설정
        entityManager.clear()

        val result = memberRepository.findByUsername("member5")
        val member5 = result[0]
        // 영속성 컨텍스트를 제거하지 않은 상태로 값을 가져오면, 40 살로 가져옴 (캐시에서 값 읽어옴)

        assertThat(resultCount).isEqualTo(3)
    }

    @Test
    fun findMemberLazy() {
        val teamA = Team("teamA")
        val teamB = Team("teamB")
        teamRepository.save(teamA)
        teamRepository.save(teamB)

        val member1 = Member("member1", 10, teamA)
        val member2 = Member("member2", 10, teamB)
        val member3 = Member("member1", 10, teamB)
        memberRepository.save(member1)
        memberRepository.save(member2)
        memberRepository.save(member3)

        entityManager.flush()
        entityManager.clear()

        // N + 1 problem
        val members = memberRepository.findAll()
        for (member in members) {
            println("member = ${member.username}")
            println("member.teamClass = ${member.team?.javaClass}")
            println("member.team = ${member.team?.name}")
        }

        // fetch join
        val fetchJoinMembers = memberRepository.findMemberFetchJoin()
        for (member in fetchJoinMembers) {
            println("member = ${member.username}")
            println("member.teamClass = ${member.team?.javaClass}")
            println("member.team = ${member.team?.name}")
        }
        // named entity graph
        val namedEntityGraphMembers = memberRepository.findEntityGraphByUsername("member1")
        for (member in namedEntityGraphMembers) {
            println("member = ${member.username}")
            println("member.teamClass = ${member.team?.javaClass}")
            println("member.team = ${member.team?.name}")
        }
    }

    @Test
    fun queryHint() {
        val member1 = Member("member1", 10)
        memberRepository.save(member1)
        entityManager.flush()
        entityManager.clear()

        val findMember = memberRepository.findReadOnlyByUsername("member1")
        findMember.username = "member2"

        entityManager.flush()
    }

    @Test
    fun lock() {
        val member1 = Member("member1", 10)
        memberRepository.save(member1)
        entityManager.flush()
        entityManager.clear()

        // for update
        val findMembers = memberRepository.findLockByUsername("member1")

        entityManager.flush()
    }

    @Test
    fun callCustom() {
        val result = memberRepository.findCustomMember()
    }
}
