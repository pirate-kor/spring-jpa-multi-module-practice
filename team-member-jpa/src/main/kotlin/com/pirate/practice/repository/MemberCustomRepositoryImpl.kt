package com.pirate.practice.repository

import com.pirate.practice.entity.Member
import javax.persistence.EntityManager

class MemberCustomRepositoryImpl(
    private val entityManager: EntityManager
) : MemberCustomRepository {
    override fun findCustomMember(): List<Member> {
        return entityManager.createQuery("select m from Member m", Member::class.java).resultList
    }
}