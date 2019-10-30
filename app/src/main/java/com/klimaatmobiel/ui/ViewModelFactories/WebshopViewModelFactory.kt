package com.klimaatmobiel.ui.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class WebshopViewModelFactory(private val groupCode: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebshopViewModel::class.java)) {
            return WebshopViewModel(groupCode) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}