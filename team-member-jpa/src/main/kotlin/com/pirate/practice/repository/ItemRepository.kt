package com.pirate.practice.repository

import com.pirate.practice.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long>