package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainMenuViewModel : ViewModel() {

    /*
     * Navigation trigger
     */
    private val _navigateToWebshop = MutableLiveData<Boolean>()
    val navigateToWebshop
        get() = _navigateToWebshop

    /*
     * Reset to null to prevent bugs when configuration changes happen
     */
    fun onWebshopNavigated() {
        //_navigateToWebshop.value = false
    }
}