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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber



class WebshopViewModel(groupCode : String) : ViewModel() {


    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status


    private val _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    init {
        getGroupWithProject(groupCode)
    }

    private fun getGroupWithProject(groupCode : String) {

        coroutineScope.launch {

            var getGroupDeferred = KlimaatmobielApi.retrofitService.getFullGroup(groupCode)

            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val group = getGroupDeferred.await()
                val a = group;
                _group.value = group


                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }

    }
}