package com.randomanimals.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.randomanimals.data.api.ApiService
import com.randomanimals.data.repository.BaseRepository.Companion.SOMETHING_WRONG
import com.randomanimals.utils.MockWebServerBaseTest
import com.randomanimals.utils.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.net.HttpURLConnection

class AnimalsRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var animalsRepository: AnimalsRepository
    private lateinit var apiService: ApiService

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        apiService = provideTestApiService()
        animalsRepository = AnimalsRepository(apiService)
    }

    @Test
    fun `Test Animal list success one element`() {
        runBlocking {
            mockHttpResponse("json/animal_response_one_item.json", HttpURLConnection.HTTP_OK)
            val apiResponse = animalsRepository.getAnimalsFromApi()

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.size, 1)
        }
    }

    @Test
    fun `Test Animal list success empty list`() {
        runBlocking {
            mockHttpResponse("json/animal_response_empty_list.json", HttpURLConnection.HTTP_OK)
            val apiResponse = animalsRepository.getAnimalsFromApi()

            assertNotNull(apiResponse)
            assertEquals(apiResponse.extractData?.size, 0)
        }
    }

    @Test
    fun `Test Animal list failure then return exception`() {
        runBlocking {
            mockHttpResponse(AnimalsRepository.GENERAL_ERROR_CODE)
            val apiResponse = animalsRepository.getAnimalsFromApi()

            assertNotNull(apiResponse)
            val expectedValue = Result.Error(Exception(SOMETHING_WRONG))
            assertEquals(expectedValue.exception.message,
                (apiResponse as Result.Error).exception.message)
        }
    }
}