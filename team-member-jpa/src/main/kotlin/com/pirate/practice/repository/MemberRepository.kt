package com.pirate.practice.repository

import com.pirate.practice.dto.MemberDto
import com.pirate.practice.entity.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    // @Query(name = "Member.findByUsername")
    // 관례상 namedQuery를 먼저 찾기 때문에 name 지정을 안해줘도 됨
    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): List<Member>

    @Query("select m.username from Member m")
    fun findUsernames(): List<String>

    @Query("select new com.pirate.practice.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    fun findMemberDto(): List<MemberDto>

    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: List<String>): List<Member>

    @Query(
        value = "select m from Member m left join m.team t",
        countQuery = "select count(m) from Member m"
    )
    fun findByAge(age: Int, pageable: Pageable): Page<Member>
    fun findSliceByAge(age: Int, pageable: Pageable): Slice<Member>

}