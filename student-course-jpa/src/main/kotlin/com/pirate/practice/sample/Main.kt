package com.pirate.practice.sample

import com.pirate.practice.utils.isNotNumber
import com.pirate.practice.utils.isNumber
import com.pirate.practice.utils.isSpaceWithWidth
import org.apache.commons.lang3.StringUtils

fun main() {
    assert("123".isNumber())
    assert("123a".isNotNumber())
    assert("   " isSpaceWithWidth 3)

}