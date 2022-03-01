package com.pirate.practice.repository

import com.pirate.practice.entity.Member

interface MemberCustomRepository {
    fun findCustomMember(): List<Member>
}