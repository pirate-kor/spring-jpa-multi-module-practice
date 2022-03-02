package com.pirate.practice.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = [NamedAttributeNode("team")])
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null,
    var username: String = "",
    var age: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "team_id")
    var team: Team? = null,
): BaseEntity() {
    constructor(username: String): this(null, username, 10)
    constructor(username: String, age: Int): this(null, username, age)
    constructor(username: String, age: Int, team: Team?): this(null, username, age, team)

    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }

    override fun toString(): String =
        "id = $id, username = $username, age = $age"
}