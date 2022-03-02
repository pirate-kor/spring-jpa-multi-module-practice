package com.pirate.practice.controller

import com.pirate.practice.dto.MemberDto
import com.pirate.practice.entity.Member
import com.pirate.practice.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
class MemberController(
    private val memberRepository: MemberRepository
) {

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable("id") id: Long): String {
        val member = memberRepository.findById(id).get()

        return member.username
    }

    // 도메인 클래스 컨버터 (조회용으로만 사용하도록..)
    @GetMapping("/members2/{id}")
    fun findMember(@PathVariable("id") member: Member): String {
        return member.username
    }

    @GetMapping("/members")
    fun list(@PageableDefault(size = 5) pageable: Pageable): Page<MemberDto> {
        return memberRepository.findAll(pageable)
            .map { MemberDto(it) }
    }

    @PostConstruct
    fun init() {
        for (i in 0 .. 100) {
            memberRepository.save(Member("member$i", i))
        }
    }

}