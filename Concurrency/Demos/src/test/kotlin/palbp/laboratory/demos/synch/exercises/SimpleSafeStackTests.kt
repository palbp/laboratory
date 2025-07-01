package palbp.laboratory.demos.synch.exercises

import kotlin.test.Test

class SimpleSafeStackTests {
    @Test
    fun `push on an empty stack adds the element to it`() {
        val stack = SimpleSafeStack<Int>()

        stack.push(1)

        assert(stack.pop() == 1)
        assert(stack.pop() == null)
    }

    @Test
    fun `push on a non-empty stack adds the element to the top`() {
        val stack = SimpleSafeStack<Int>()

        stack.push(1)
        stack.push(2)

        assert(stack.pop() == 2)
        assert(stack.pop() == 1)
        assert(stack.pop() == null)
    }

    @Test
    fun `pop on an empty stack returns null`() {
        val stack = SimpleSafeStack<Int>()

        assert(stack.pop() == null)
    }
}
