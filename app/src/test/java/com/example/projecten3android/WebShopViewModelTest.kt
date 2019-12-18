package com.example.projecten3android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.klimaatmobiel.domain.*
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WebShopViewModelTest {

    private lateinit var viewModel: WebshopViewModel

    private val repo = mockk<KlimaatmobielRepository>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = WebshopViewModel(Group(1, "gname", 1.0, Project(1, "", "", "", 1.0, 1, false, 1, mockk(), products = listOf()),  Order(1, "", false, false, 5.0, 5.0, 1, mutableListOf()), "123"), repo)
    }

    @After
    fun destroy() {
        clearMocks(repo)
    }

    @Test
    fun webshopViewModel_AddProductToOrder_Exception() {
        coEvery { repo.addProductToOrder(any(), any()) } throws Exception()

        runBlockingTest {
            viewModel.onProductClicked(mockk(), 0)
        }

        assertNotNull(viewModel.status.getValueForTest()!!)
        assertEquals(KlimaatMobielApiStatus.ERROR, viewModel.status.getValueForTest()!!)
    }


    @Test
    fun webshopViewModel_ShowProductDetails() {
        coEvery { repo.getProduct(any(), any()) } returns Product(1, "hout", "beschrijving", null, 1, 10.0, 1, mockk())
        runBlockingTest {
            viewModel.onProductClicked(Product(1, "hout", "beschrijving", null, 1, 10.0, 1, mockk()), 1)
        }

        assertNotNull(viewModel.navigateToWebshop.getValueForTest())
    }
}