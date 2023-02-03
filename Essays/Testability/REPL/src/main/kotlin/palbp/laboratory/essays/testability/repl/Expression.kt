package palbp.laboratory.essays.testability.repl

sealed class Expression
data class Constant(val value: Int) : Expression()
data class Addition(val left: Expression, val right: Expression) : Expression()
data class Subtraction(val left: Expression, val right: Expression) : Expression()
data class Multiplication(val left: Expression, val right: Expression) : Expression()
data class Division(val divisor: Expression, val dividend: Expression) : Expression()

fun evaluate(expr: Expression): Int = when (expr) {
    is Addition -> evaluate(expr.left) + evaluate(expr.right)
    is Subtraction -> evaluate(expr.left) - evaluate(expr.right)
    is Multiplication -> evaluate(expr.left) * evaluate(expr.right)
    is Division -> evaluate(expr.dividend) / evaluate(expr.divisor)
    is Constant -> expr.value
}
