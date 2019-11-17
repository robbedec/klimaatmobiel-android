package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class ProductDetailViewModel(private val repository: KlimaatmobielRepository, private val projectId: Long, private val productId: Long) : ViewModel() {

    private var _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    private var _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        Timber.i("$projectId and $productId")
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            val getProductDeferred = repository.getProduct(projectId, productId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val productRes = getProductDeferred.await()
                _product.value = productRes
                _status.value = KlimaatMobielApiStatus.DONE

            } catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }
}