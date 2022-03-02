package com.pirate.practice.repository

import com.pirate.practice.entity.Item
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ItemRepositoryTest(
    private val itemRepository: ItemRepository
) {

    @Test
    fun save() {
        val item = Item()
        itemRepository.save(item)
    }

}