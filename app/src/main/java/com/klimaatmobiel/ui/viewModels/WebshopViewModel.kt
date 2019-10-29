package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klimaatmobiel.data.network.GroupProperty
import com.klimaatmobiel.data.network.KlimaatmobielApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

enum class KlimaatmobielApiStatus { LOADING, ERROR, DONE }

class WebshopViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<KlimaatmobielApiStatus>()
    val status: LiveData<KlimaatmobielApiStatus> // The external immutable LiveData for the request status
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _property = MutableLiveData<GroupProperty>()
    val property: LiveData<GroupProperty> // The external LiveData interface to the property is immutable, so only this class can modify
        get() = _property

    init {
        getGroupWithProject()
    }

    private fun getGroupWithProject() {
        KlimaatmobielApi.retrofitService.getProject("1abcde").enqueue( object: Callback<GroupProperty> {
            override fun onResponse(call: Call<GroupProperty>, response: Response<GroupProperty>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Timber.i("Success: ${response.body()?.name} Mars properties retrieved")
            }

            override fun onFailure(call: Call<GroupProperty>, t: Throwable) {
                Timber.i("Api call failed")
                Timber.i("message %s", t.message)
            }
        })
    }
}