package com.example.projecten3android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel

    private val repo = mockk<KlimaatmobielRepository>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        coEvery { repo.getProduct(any(), any()) } returns mockk()
        viewModel = ProductDetailViewModel(repo, 1, 1)
    }

    @After
    fun destroy() {
        clearMocks(repo)
    }

    @Test
    fun productViewModel_SetsProduct() {
        assertNotNull(viewModel.product.getValueForTest())
    }
}