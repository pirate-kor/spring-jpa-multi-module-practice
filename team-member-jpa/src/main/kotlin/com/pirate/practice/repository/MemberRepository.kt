package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import javax.sound.midi.MetaMessage

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

}