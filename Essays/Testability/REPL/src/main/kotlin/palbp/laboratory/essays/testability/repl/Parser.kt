package palbp.laboratory.essays.testability.repl

/*
 * Parser implementation for the following grammar:
 *
 *  expression  = operation | constant
 *  operation   = operator , expression , expression
 *  operator    = '-' | '+' | '*' | '/'
 *  constant    = [sign] , {digit}
 *  sign        = '-' | '+'
 *  digit       = '0' | '1' | '2' | '3' | '4'| '5'| '6'| '7'| '8'| '9'
 */

fun parse(input: String): Expression = expression(tokens = tokenize(input), startsAt = 0).first

class UnexpectedToken(val token: Token) : Exception()

private fun expression(tokens: List<Token>, startsAt: Int): Pair<Expression, Int> =
    if (isOperation(tokens[startsAt])) operation(tokens, startsAt)
    else constant(tokens, startsAt)

private fun isOperation(token: Token): Boolean =
    when (token.value) {
        "+", "-", "*", "/" -> true
        else -> false
    }

private fun operation(tokens: List<Token>, startsAt: Int): Pair<Expression, Int> {
    val operation = tokens[startsAt]
    val left = expression(tokens, startsAt = startsAt + 1)
    val right = expression(tokens, startsAt = left.second)
    return Pair(first = when (operation.value) {
        "+" -> Addition(left.first, right.first)
        "-" -> Subtraction(left.first, right.first)
        "*" -> Multiplication(left.first, right.first)
        "/" -> Division(left.first, right.first)
        else -> throw UnexpectedToken(operation)
    }, second = right.second)
}

private fun constant(tokens: List<Token>, startsAt: Int): Pair<Expression, Int> {
    val value = tokens[startsAt].value.toIntOrNull()
    return if (value != null) Pair(first = Constant(value), second = startsAt + 1)
    else throw UnexpectedToken(tokens[startsAt])
}
