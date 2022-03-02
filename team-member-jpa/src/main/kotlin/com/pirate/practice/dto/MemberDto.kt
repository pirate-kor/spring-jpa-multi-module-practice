package com.pirate.practice.dto

import com.pirate.practice.entity.Member

data class MemberDto(
    val id: Long,
    val username: String,
    val teamName: String
) {
    constructor(member: Member): this(member.id ?: 0, member.username, member.team?.name ?: "")
}