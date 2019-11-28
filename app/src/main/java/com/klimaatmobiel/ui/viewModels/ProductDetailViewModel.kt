package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class ProductDetailViewModel(private val repository: KlimaatmobielRepository, private val projectId: Long, private val productId: Long) : ViewModel() {

    private var _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            // Get the cached product from the database
            _product.value = repository.getProduct(projectId, productId)
        }
    }
}