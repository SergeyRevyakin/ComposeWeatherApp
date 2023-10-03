@file:OptIn(ExperimentalCoroutinesApi::class)

package ru.serg.common

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class NetworkResultUnitTest {
    @Test
    fun `network result unit test`() = runTest {
        flow {
            emit(1)
            throw Exception("Test Done")
        }
            .asResult()
            .test {
                assertEquals(NetworkResult.Loading, awaitItem())
                assertEquals(NetworkResult.Success(1), awaitItem())

                when (val errorResult = awaitItem()) {
                    is NetworkResult.Error -> assertEquals(
                        "Test Done",
                        errorResult.message,
                    )

                    NetworkResult.Loading,
                    is NetworkResult.Success,
                    -> throw IllegalStateException(
                        "The flow should have emitted an Error Result",
                    )
                }

                awaitComplete()
            }
    }
}