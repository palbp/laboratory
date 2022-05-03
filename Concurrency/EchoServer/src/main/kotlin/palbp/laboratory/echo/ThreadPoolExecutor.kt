package palbp.laboratory.echo

import java.util.LinkedList
import java.util.concurrent.Executor
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


class ThreadPoolExecutor(private val threadCount: Int = 2) : Executor {

    class ThreadPoolWorker(private val executor: ThreadPoolExecutor) : Thread() {
        override fun run() {
            while (true) {
                val action = executor.takeExecutionRequest()
                action.run()
            }
        }
    }

    private val executionRequests = LinkedList<Runnable>()
    private val mLock = ReentrantLock()
    private val mCondition = mLock.newCondition()

    init {
        repeat(threadCount) {
            ThreadPoolWorker(this).start()
        }
    }

    private fun takeExecutionRequest(): Runnable {
        mLock.withLock {
            if (executionRequests.isNotEmpty())
                return executionRequests.removeFirst()

            while (true) {
                mCondition.await()

                if (executionRequests.isNotEmpty())
                    return executionRequests.removeFirst()
            }
        }
    }

    private fun putExecutionRequest(action: Runnable) {
        mLock.withLock {
            executionRequests.addLast(action)
            mCondition.signal()
        }
    }

    override fun execute(action: Runnable) {
        putExecutionRequest(action)
    }
}

