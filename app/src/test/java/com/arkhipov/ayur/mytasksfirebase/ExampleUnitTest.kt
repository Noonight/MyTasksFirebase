package com.arkhipov.ayur.mytasksfirebase

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkNumber() {
        assertEquals(instance(), 2)
    }

    fun instance(): Int {
        return 2
    }

    fun instance2() = 2

    fun sort(array: ArrayList<Int>) {
        return array.sort()
    }
}
