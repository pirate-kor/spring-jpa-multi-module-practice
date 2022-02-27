package com.pirate.practice.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Team(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String = ""
) {

    constructor(name: String): this(null, name)

    @OneToMany(mappedBy = "team")
    var members: MutableList<Member> = mutableListOf()

    override fun toString(): String =
        "id: $id, name: $name"
}