package pt.isel.pdm.quote.daily

import org.junit.Test

class QuoteTests {

    @Test(expected = IllegalArgumentException::class)
    fun `create quote with blank author throws`() {
        Quote(id = 0, text = "A quote", author = " \n  \t ")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create quote with blank text throws`() {
        Quote(id = 0, text = "\t   \n", author = "The Author")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create quote with empty text throws`() {
        Quote(id = 0, text = "", author = "The Author")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `create quote with empty author throws`() {
        Quote(id = 0, text = "A quote", author = "")
    }

    @Test
    fun `create quote with non empty text and author succeeds`() {
        Quote(id = 0, text = "A quote", author = "The Author")
    }
}

