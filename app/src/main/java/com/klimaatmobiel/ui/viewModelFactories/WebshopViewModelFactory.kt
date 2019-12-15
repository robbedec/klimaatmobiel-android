package com.klimaatmobiel.ui.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class WebshopViewModelFactory(private val group: Group, private val repository: KlimaatmobielRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebshopViewModel::class.java)) {
            return WebshopViewModel(group, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}