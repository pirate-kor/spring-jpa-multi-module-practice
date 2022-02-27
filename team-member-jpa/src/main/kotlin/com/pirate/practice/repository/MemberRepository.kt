package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>