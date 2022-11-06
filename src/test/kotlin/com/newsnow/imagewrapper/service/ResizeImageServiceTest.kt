package com.newsnow.imagewrapper.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileInputStream
import java.lang.IllegalArgumentException
import java.nio.file.Path

class ResizeImageServiceTest {

    lateinit var serviceUnderTest: ResizeImageService

    lateinit var smallImage: Path
    lateinit var mediumImage: Path
    lateinit var largeImage: Path

    @BeforeEach
    fun init() {
        serviceUnderTest = ResizeImageService()
        smallImage = Path.of(this.javaClass.classLoader.getResource("small.jpg")!!.toURI())
        mediumImage = Path.of(this.javaClass.classLoader.getResource("medium.jpg")!!.toURI())
        largeImage = Path.of(this.javaClass.classLoader.getResource("large.png")!!.toURI())
    }

    @Test
    fun `should resize small image correctly`() {
        val w = 30
        val h = 20
        val image = serviceUnderTest.resizeBlocking(
            /* inputStream = */ FileInputStream(smallImage.toFile()),
            /* width = */ w,
            /* height = */ h
        )
        assertNotNull(image)
        assertEquals(h, image.height)
        assertEquals(w, image.width)
    }

    @Test
    fun `should resize multiple images at the same time correctly`() {
        val w = 30
        val h = 100

        val small = serviceUnderTest.resizeBlocking(
            /* inputStream = */ FileInputStream(smallImage.toFile()),
            /* width = */ w,
            /* height = */ h
        )

        val medium = serviceUnderTest.resizeBlocking(
            /* inputStream = */ FileInputStream(smallImage.toFile()),
            /* width = */ w,
            /* height = */ h
        )

        val large = serviceUnderTest.resizeBlocking(
            /* inputStream = */ FileInputStream(smallImage.toFile()),
            /* width = */ w,
            /* height = */ h
        )

        assertNotNull(large)
        assertNotNull(medium)
        assertNotNull(small)
        assertEquals(h, large.height)
        assertEquals(w, large.width)
        assertEquals(h, medium.height)
        assertEquals(w, medium.width)
        assertEquals(h, small.height)
        assertEquals(w, small.width)
    }

    @Test
    fun `should throw exception if dimension overflows`() {
        val w = Integer.MAX_VALUE
        val h = Integer.MAX_VALUE

        assertThrows<IllegalArgumentException> {
            val image = serviceUnderTest.resizeBlocking(
                /* inputStream = */ FileInputStream(smallImage.toFile()),
                /* width = */ w,
                /* height = */ h
            )
            assertNotNull(image)
            assertEquals(h, image.height)
            assertEquals(w, image.width)
        }
    }
}
