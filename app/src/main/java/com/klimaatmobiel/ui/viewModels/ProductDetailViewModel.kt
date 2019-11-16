package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.ViewModel
import com.klimaatmobiel.domain.KlimaatmobielRepository
import timber.log.Timber

class ProductDetailViewModel(private val repository: KlimaatmobielRepository, private val projectId: Long, private val productId: Long) : ViewModel() {


    init {
        Timber.i("$projectId and $productId")
    }
}