package com.klimaatmobiel.ui.ViewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class WebshopViewModelFactory(private val group: Group) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebshopViewModel::class.java)) {
            return WebshopViewModel(group) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}