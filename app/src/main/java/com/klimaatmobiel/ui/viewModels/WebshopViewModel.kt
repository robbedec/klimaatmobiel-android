package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.Product
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



class WebshopViewModel(group : Group) : ViewModel() {


    private val _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status


    private val _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    init {
        _group.value = group // de groep met het project end de order is hier beschikbaar
        _products.value = group.project.products
    }




}