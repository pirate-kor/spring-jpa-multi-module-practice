package com.pirate.practice.repository

import com.pirate.practice.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long>