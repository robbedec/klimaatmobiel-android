package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.Order
import com.klimaatmobiel.domain.OrderItem
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


    private var _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status


    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    init {
        _group.value = group // de groep met het project end de order is hier beschikbaar

    }





    fun addProductToOrder(product: Product){


        coroutineScope.launch {


            var addProductToOrderDeferred = KlimaatmobielApi.retrofitService.
                addProductToOrder(OrderItem(0,1,null,product.productId, 0),_group.value!!.order!!.orderId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = addProductToOrderDeferred.await()

                if(orderItemRes.removedOrAddedOrderItem.amount > 1){ // the orderitem is already in the orderitem

                    _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                        .amount = orderItemRes.removedOrAddedOrderItem.amount

                } else {
                    _group.value!!.order.orderItems.add(orderItemRes.removedOrAddedOrderItem)
                }

                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                _group.value = _group.value // trigger live data change, moet wss niet?

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


    fun changeOrderItemAmount(oi: OrderItem, add: Boolean){
        if(add){
            oi.amount++
            updateOrderItem(oi);
        } else {
            oi.amount--
            if(oi.amount < 1) {
                removeOrderItem(oi)
            } else {
                updateOrderItem(oi)
            }
        }
    }


    private fun updateOrderItem(oi: OrderItem){

        coroutineScope.launch {

            var updateOrderItemDeferred = KlimaatmobielApi.retrofitService.updateOrderItem(oi, oi.orderItemId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = updateOrderItemDeferred.await()


                _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                    .amount = orderItemRes.removedOrAddedOrderItem.amount
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                _group.value = _group.value // trigger live data change, moet wss niet?

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

    fun removeOrderItem(oi : OrderItem){

        coroutineScope.launch {
            var removeOrderItemDeferred = KlimaatmobielApi.retrofitService.
                removeOrderItemFromOrder(oi.orderItemId, group.value!!.order.orderId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = removeOrderItemDeferred.await()

                _group.value!!.order.orderItems.remove( _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!)
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                _group.value = _group.value // trigger live data change, moet wss niet?

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


}