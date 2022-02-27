package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.sound.midi.MetaMessage

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    // @Query(name = "Member.findByUsername")
    // 관례상 namedQuery를 먼저 찾기 때문에 name 지정을 안해줘도 됨
    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): List<Member>

}