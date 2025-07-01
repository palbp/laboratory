package palbp.laboratory.demos.synch

interface Promise<T> {
    fun then(fulfilled: (T) -> Unit)
}

class PromiseImpl<T> : Promise<T> {
    override fun then(fulfilled: (T) -> Unit) {
        TODO("Not yet implemented")
    }
}

fun doSomethingThatTakesALongTime(input: Int): Promise<Int> {
    val promise = PromiseImpl<Int>()
    TODO()
    return promise
}
