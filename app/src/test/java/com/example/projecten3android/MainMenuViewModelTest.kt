package com.example.projecten3android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainMenuViewModelTest {

    private lateinit var viewModel: MainMenuViewModel

    private val repo = mockk<KlimaatmobielRepository>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MainMenuViewModel(repo)
    }

    @Test
    fun mainMenuViewModel_workingAPI_responseGetsUpdated() {
        // Arrange
        coEvery { repo.getFullGroup("244222") } returns mockk()

        runBlockingTest {
            // Act
            viewModel = MainMenuViewModel(repo)
            viewModel.onClickNavigateToWebshop()
        }
        // Assert
        assertNotNull(viewModel.status.getValueForTest())
    }

    @Test
    fun mainMenuViewModel_ApiError_responseIndicatesFailure() {
        // Arrange
        coEvery { repo.getFullGroup("2332324") } throws Exception()

        runBlockingTest {
            // Act
            viewModel = MainMenuViewModel(repo)
            viewModel.onClickNavigateToWebshop()
        }

        assertNotNull(viewModel.status.getValueForTest())
        assertEquals(KlimaatMobielApiStatus.ERROR, viewModel.status.getValueForTest()!!)
    }

    @After
    fun destroy() {
        clearMocks(repo)
    }
}