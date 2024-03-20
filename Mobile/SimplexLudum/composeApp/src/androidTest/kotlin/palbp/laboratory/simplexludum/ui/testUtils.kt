package palbp.laboratory.simplexludum.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherTestRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestRule {
    override fun apply(test: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                try {
                    Dispatchers.setMain(dispatcher)
                    test.evaluate()
                }
                finally {
                    Dispatchers.resetMain()
                }
            }
        }
}
