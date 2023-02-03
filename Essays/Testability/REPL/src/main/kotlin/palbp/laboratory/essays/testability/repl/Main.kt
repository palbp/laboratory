package palbp.laboratory.essays.testability.repl

/**
 * A basic REPL
 */
fun main() {
    while (true) {
        print(">> ")
        val line = readlnOrNull()
        if (line.isNullOrBlank()) break
        val expression = parse(line)
        val result = evaluate(expression)
        println(">> $result")
    }
}
