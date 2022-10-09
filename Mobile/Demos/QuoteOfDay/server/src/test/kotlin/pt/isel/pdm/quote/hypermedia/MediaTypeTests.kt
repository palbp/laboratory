package pt.isel.pdm.quote.hypermedia

import org.junit.Test

class MediaTypeTests {

    @Test(expected = IllegalArgumentException::class)
    fun `fromString on invalid string throws IllegalArgumentException`() {
        MediaType.fromString("")
    }
}