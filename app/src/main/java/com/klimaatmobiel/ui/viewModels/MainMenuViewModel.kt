package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class MainMenuViewModel(private val repository: KlimaatmobielRepository) : ViewModel() {

    // Decides which project / group gets fetched from the API
    val groupCode = MutableLiveData<String>()

    // Triggers the navigation to the webshop
    private val _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    // Current status from the API
    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status


    init {
        // For testing purposes
        groupCode.value = "212345"
    }

    /**
     * Try to fetch the group (with project) based on the groupCode and navigate to the webshop (on success)
     * or show an error message on screen.
     */
    fun onClickNavigateToWebshop(){
        viewModelScope.launch {

            // Fetch the group from the API and try to receive the value from the coroutine job
            // Update the API status accordingly

            try {
                var group = repository.getFullGroup(groupCode.value ?: "")
                _status.value = KlimaatMobielApiStatus.LOADING

                // Filter list by categoryName
                group.project.products.toMutableList().sortBy { it.category!!.categoryName }

                // Trigger the navigation to the webshop
                _navigateToWebshop.value = group

                // Cache the new products from the project
                repository.refreshProducts(group.project.products)

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}