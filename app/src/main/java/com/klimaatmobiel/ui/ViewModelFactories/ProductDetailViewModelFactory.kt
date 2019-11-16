package com.klimaatmobiel.ui.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel

class ProductDetailViewModelFactory(private val repository: KlimaatmobielRepository, private val projectId: Long, private val productId: Long) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(repository, projectId, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}