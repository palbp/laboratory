package pt.isel.pdm.quote.utils

import com.google.gson.GsonBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import pt.isel.pdm.quote.hypermedia.MediaType

class MediaTypeTypeAdapterTests {

    private val sut = GsonBuilder()
        .registerTypeAdapter(MediaType::class.java, MediaTypeTypeAdapter())
        .create()

    @Test
    fun `toJson on MediaType instance produces quoted toString result`() {
        val mediaType = MediaType(type = "type", subType = "subtype")
        val expected = "\"$mediaType\""
        assertEquals(expected, sut.toJson(mediaType))
    }

    @Test
    fun `toJson on null produces a the null literal string`() {
        val mediaType: MediaType? = null
        val expected = "null"
        assertEquals(expected, sut.toJson(mediaType))
    }

    @Test
    fun `fromJson with a valid string produces a proper MediaType instance`() {
        val expected = MediaType(type = "type", subType = "subtype")
        val actual = sut.fromJson("\"$expected\"", MediaType::class.java)
        assertEquals(expected, actual)
    }

    @Test
    fun `fromJson with a null literal string returns the null value`() {
        val mediaType = sut.fromJson("null", MediaType::class.java)
        assertNull(mediaType)
    }
}