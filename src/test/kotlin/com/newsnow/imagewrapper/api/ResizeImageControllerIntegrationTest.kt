package com.newsnow.imagewrapper.api

import com.newsnow.imagewrapper.AbstractIntegrationTest
import com.newsnow.imagewrapper.domain.Task
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import java.util.UUID.randomUUID


class ResizeImageControllerIntegrationTest : AbstractIntegrationTest() {

    @LocalServerPort
    lateinit var port: Integer

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    val smallImage = ClassPathResource("small.jpg")

    @Test
    fun `should return 404 for a non-existing task`() {
        assertEquals(
            NOT_FOUND,
            /* actual = */ restTemplate.getForEntity(
                /* url = */ "http://localhost:$port/task/{taskid}",
                /* responseType = */ String::class.java,
                /* ...urlVariables = */ randomUUID()
            ).statusCode
        )
    }

    @Test
    fun `should process image correctly post endpoint`() {

        val parameters = LinkedMultiValueMap<String, Any>()
        parameters.add("image", smallImage)
        parameters.add("height", 10)
        parameters.add("width", 100)

        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA
        val entity = HttpEntity<LinkedMultiValueMap<String, Any>>(parameters, headers)


        val response =
            restTemplate.postForEntity(/* url = */ "http://localhost:$port/task", entity, Task::class.java)


        assertEquals(OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(100, response.body!!.width)
        assertEquals(10, response.body!!.height)
        assertTrue(System.currentTimeMillis() >= response.body!!.created)
        assertEquals("http://localhost/${response.body!!.id}.png", response.body!!.url)
        assertEquals("c55c201191e0bafbe8b4bcc86f1eb9be", response.body!!.md5)
    }

    @Test
    fun `should return 400 if no image is provided`() {
    }

    @Test
    fun `should return 400 if height is over 2160`() {
    }

    @Test
    fun `should return 400 if width is over 3840`() {
    }

    @Test
    fun `Image url should provide a proper image`() {
    }
}
