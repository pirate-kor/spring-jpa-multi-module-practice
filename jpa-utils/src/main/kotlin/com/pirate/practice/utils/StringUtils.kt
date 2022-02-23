package com.pirate.practice.utils

infix fun String.isSpaceWithWidth(width: Int): Boolean = (this.length == width) && this.isBlank()

fun String.isNumber(): Boolean = !this.isNotNumber()

fun String.isNotNumber(): Boolean = this.any { !it.isDigit() }