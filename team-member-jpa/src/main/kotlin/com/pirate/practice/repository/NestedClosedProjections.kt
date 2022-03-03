package com.pirate.practice.repository

interface NestedClosedProjections {

    fun getUsername(): String
    fun getTeam(): TeamInfo

    interface TeamInfo {
        fun getName(): String
    }

}