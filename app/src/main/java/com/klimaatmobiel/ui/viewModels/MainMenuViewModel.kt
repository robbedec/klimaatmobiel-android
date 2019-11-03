package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class MainMenuViewModel : ViewModel() {

    val groupCode = MutableLiveData<String>()

    private val _navigateToWebshop = MutableLiveData<Group>()
    val navigateToWebshop: LiveData<Group> get() = _navigateToWebshop

    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)





    fun onClickNavigateToWebshop(){

        // check for empty groupCode

        coroutineScope.launch {

            //var getGroupDeferred = KlimaatmobielApi.retrofitService.getFullGroup(groupCode.value!!) // "1abcde"
            var getGroupDeferred = KlimaatmobielApi.retrofitService.getFullGroup("212345") // "212345"
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val group = getGroupDeferred.await()
                _navigateToWebshop.value = group

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e.message)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    fun onWebshopNavigated() {
        _navigateToWebshop.value = null
    }
}