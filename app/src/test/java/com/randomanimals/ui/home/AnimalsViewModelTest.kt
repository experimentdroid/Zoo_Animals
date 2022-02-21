package com.randomanimals.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.randomanimals.data.model.Animal
import com.randomanimals.data.repository.AnimalsRepository
import com.randomanimals.utils.Result
import com.randomanimals.utils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class AnimalsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    private lateinit var viewModel: AnimalsViewModel

    @MockK
    private lateinit var animalsRepository: AnimalsRepository

    @MockK
    private lateinit var animalResponseObserver: Observer<Result<ArrayList<Animal>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = AnimalsViewModel(animalsRepository)
    }

    @Test
    fun `Test fetch Animals list successfully`() {
        val emptyList = arrayListOf<Animal>()
        testCoroutineRule.runBlockingTest {
            viewModel.getAnimalsLiveData().observeForever(animalResponseObserver)
            coEvery { animalsRepository.getAnimalsFromApi() } returns Result.Success(emptyList)
            viewModel.loadAnimalsData()
            assertNotNull(viewModel.getAnimalsLiveData().value)
            assertEquals(Result.Success(emptyList), viewModel.getAnimalsLiveData().value)
        }
    }

    @Test
    fun `Test fetch Animals list then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.getAnimalsLiveData().observeForever(animalResponseObserver)
            viewModel.loadAnimalsData()
            verify { animalResponseObserver.onChanged(Result.InProgress) }
        }
    }

    @Test
    fun `Test fetch Animals list fails then return an error`() {
        val exception = mockkClass(HttpException::class)
        testCoroutineRule.runBlockingTest {
            viewModel.getAnimalsLiveData().observeForever(animalResponseObserver)
            coEvery { animalsRepository.getAnimalsFromApi() } returns Result.Error(exception)
            viewModel.loadAnimalsData()
            assertNotNull(viewModel.getAnimalsLiveData().value)
            assertEquals(Result.Error(exception), viewModel.getAnimalsLiveData().value)
        }
    }

    @After
    fun tearDown() {
        viewModel.getAnimalsLiveData().removeObserver(animalResponseObserver)
    }
}