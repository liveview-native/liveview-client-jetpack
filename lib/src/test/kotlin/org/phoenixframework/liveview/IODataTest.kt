package org.phoenixframework.liveview

import org.junit.Test
import kotlin.test.assertEquals

class IODataTest {
    @Test
    fun ioDataReverseIsIodata() {
        val ioData = ioData()

        assertEquals(ioData, ioData.reverse())
    }

    @Test
    fun ioDataWithoutEmptyStringReverseIsIodataWithoutEmptyString() {
        val ioData = ioData("string")

        assertEquals(ioData, ioData.reverse())
    }

    @Test
    fun ioDataWithHeadAndTailReturnsTailHead() {
        val head = ioData("head")
        val tail = ioData("tail")

        assertEquals(ioData(tail, head), ioData(head, tail).reverse())
    }

    @Test
    fun ioDataWithComplexHeadWithComplexTailOnlyReversesTopLevelList() {
        val head = ioData(ioData("head_head"), ioData("head_tail"))
        val tail = ioData("tail")

        assertEquals(ioData(tail, head), ioData(head, tail).reverse())
    }

    @Test
    fun ioDataToStringIsEmpty() {
        assertEquals("", ioData().toString())
    }

    @Test
    fun ioDataWithEmptyStringIsIodata() {
        assertEquals(ioData(), ioData(""))
    }

    @Test
    fun ioDataWithoutEmptyStringToStringIsString() {
        val string = "string"

        assertEquals(string, ioData(string).toString())
    }

    @Test
    fun ioDataWithIodataWithIodataIsIodata() {
        assertEquals(ioData(), ioData(ioData(), ioData()))
    }

    @Test
    fun ioDataWithIodataWithoutEmptyStringWithIodataIsIodataWithoutEmptyString() {
        val head = ioData("head")

        assertEquals(head, ioData(head, ioData()))
    }

    @Test
    fun ioDataWithIodataWithIodataWithoutEmptyStringIsIodataWithoutEmptyString() {
        val tail = ioData("tail")

        assertEquals(tail, ioData(ioData(), tail))
    }

    @Test
    fun ioDataWithEmptyStringWithIodataIsIodata() {
        assertEquals(ioData(), ioData("", ioData()))
    }

    @Test
    fun ioDataWithNonEmptyStringWithIodataIsIodataWithNonEmptyString() {
        val string = "string"

        assertEquals(ioData(string), ioData(string, ioData()))
    }

    @Test
    fun ioDataWithIodataWithEmptyStringIsIodata() {
        assertEquals(ioData(), ioData(ioData(), ""))
    }

    @Test
    fun ioDataWithIodataWithoutEmptyStringWithEmptyStringIsIodataWithoutEmptyString() {
        val head = ioData("head")

        assertEquals(head, ioData(head, ""))
    }

    @Test
    fun ioDataWithIodataWithoutEmptyStringWithIodataWithoutEmptyStringToStringConcatenatedStrings() {
        assertEquals("headtail", ioData(ioData("head"), ioData("tail")).toString())
    }

    @Test
    fun ioDataWithIodataWithIodataWithEmptyStringWithIodataIsIodata() {
        assertEquals(ioData(), ioData(ioData(), "", ioData()))
    }

    @Test
    fun ioDataWithIodataWithNonEmptyStringWithEmptyStringWithIodataIsIodataWithoutEmptyString() {
        val string = "first"

        assertEquals(ioData(string), ioData(ioData(string), "", ioData()))
    }

    @Test
    fun ioDataWithIodataWithIodataWithoutEmptyStringWithIodataIsIodataWithoutEmptyString() {
        val string = "second"

        assertEquals(ioData(string), ioData(ioData(), string, ioData()))
    }

    @Test
    fun ioDataWithIoDataWithEmptyStringWithIodataWithoutEmptyStringIsIodataWithoutEmptyString() {
        val string = "tail"

        assertEquals(ioData(string), ioData(ioData(), "", ioData(string)))
    }

    @Test
    fun ioDataWithIoDataWithoutEmptyStringWithNonEmptyStringWithIodataToStringConcatenatesStrings() {
        assertEquals("firstsecond", ioData(ioData("first"), "second", ioData()).toString())
    }

    @Test
    fun ioDataWithIODataWithoutEmptyStringWithEmptyStringWithoutEmptyStringConcatenatesStrings() {
        assertEquals("firsttail", ioData(ioData("first"), "", ioData("tail")).toString())
    }
}
