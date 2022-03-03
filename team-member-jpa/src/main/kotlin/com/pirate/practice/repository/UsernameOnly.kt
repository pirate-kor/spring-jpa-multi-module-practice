package com.pirate.practice.repository

import org.springframework.beans.factory.annotation.Value

interface UsernameOnly {
    // open projection
    @Value("#{target.username + ' ' + target.age}")
    fun getUsername(): String  // 이것만 있으면 close projection
}