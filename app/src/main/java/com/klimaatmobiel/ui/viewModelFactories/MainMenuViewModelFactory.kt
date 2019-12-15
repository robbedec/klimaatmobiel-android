package com.klimaatmobiel.ui.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel

class MainMenuViewModelFactory(private val repository: KlimaatmobielRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return MainMenuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}