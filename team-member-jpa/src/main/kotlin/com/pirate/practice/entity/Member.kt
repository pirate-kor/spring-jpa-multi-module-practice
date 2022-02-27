package com.pirate.practice.entity

import javax.persistence.*

@Entity
class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null
    var username: String = ""
    var age: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    override fun toString(): String =
        "id = $id, username = $username, age = $age"
}