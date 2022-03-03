package com.pirate.practice.repository

interface MemberProjection {

    fun getId(): Long
    fun getUsername(): String
    fun getTeamName(): String

}