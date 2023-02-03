package palbp.laboratory.essays.testability.repl

data class Token(val container: String, val range: IntRange) {
    constructor(container: String, startsAt: Int) : this(container, startsAt until container.length)
    init {
        require(range.first >= 0 && range.last <= container.length)
        require(container.isNotBlank())
    }
    val value = container.substring(range.first, range.last)
}

fun tokenize(input: String): List<Token> = coRecursiveTokenize(input)

fun iterativeTokenize(input: String): List<Token> {
    var tokenStart = -1
    val tokens = mutableListOf<Token>()
    input.forEachIndexed { index, c ->
        if (c.isWhitespace()) {
            if (tokenStart != -1) {
                tokens.add(Token(input, tokenStart..index))
                tokenStart = -1
            }
        } else {
            if (tokenStart == -1)
                tokenStart = index
        }
    }

    if (tokenStart != -1) {
        tokens.add(Token(input, tokenStart..input.length))
    }

    return tokens
}

fun recursiveTokenize(input: String, startIndex: Int = 0): List<Token> =
    nextToken(input, inputIndex = startIndex).let {
        if (it == null) emptyList()
        else listOf(it) + recursiveTokenize(input, startIndex = it.range.last)
    }

tailrec fun coRecursiveTokenize(input: String, startIndex: Int = 0, acc: List<Token> = emptyList()): List<Token> {
    val nextToken = nextToken(input, inputIndex = startIndex)
    return if (nextToken == null) acc
    else coRecursiveTokenize(input, startIndex = nextToken.range.last, acc = acc + nextToken)
}

private fun nextToken(input: String, inputIndex: Int): Token? {

    tailrec fun skipToTokenStart(currIndex: Int): Int =
        if (currIndex >= input.length || !input[currIndex].isWhitespace()) currIndex
        else skipToTokenStart(currIndex + 1)

    tailrec fun buildToken(startIndex: Int, currIndex: Int): Token =
        if (currIndex == input.length || input[currIndex].isWhitespace()) Token(input, startIndex..currIndex)
        else buildToken(startIndex, currIndex + 1)

    val nextTokenStart = skipToTokenStart(inputIndex)
    return if (nextTokenStart >= input.length) null
    else buildToken(nextTokenStart, nextTokenStart)
}
