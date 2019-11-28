package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.*
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber



class WebshopViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {


    private var _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    var posToRefreshInOrderPreviewListItem: Int = -1

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    private var _filteredList = MutableLiveData<List<Product>>()
    val filteredList: LiveData<List<Product>> get() = _filteredList
    private var filterString = ""
    private var filterCategoryName = ""

    private val _navigateToWebshop = MutableLiveData<List<Long>>()
    val navigateToWebshop: LiveData<List<Long>> get() = _navigateToWebshop

    val testScore = 7.0



    init {
        _group.value = group // de groep met het project end de order is hier beschikbaar
        _filteredList.value = group.project.products
    }

    /**
     * Reset after navigation to prevent bugs on configuration changes.
     */
    fun onDetailNavigated() {
        _navigateToWebshop.value = null
    }

    /**
     * Add a product to the order of the current group.
     * Uses a coroutine job to check if the operation succeeded (it returns a DTO with the item that is added or an error).
     * Updates the LiveData to show updated values in the fragment or trigger an error message on screen.
     *
     * @param product The product that you want to add to the order
     */
    private fun addProductToOrder(product: Product){
        viewModelScope.launch {
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = repository.addProductToOrder(OrderItem(0,1,null,product.productId, 0),_group.value!!.order.orderId)

                if(orderItemRes.removedOrAddedOrderItem.amount > 1){ // the orderitem is already in the orderitem

                    _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                        .amount = orderItemRes.removedOrAddedOrderItem.amount

                    posToRefreshInOrderPreviewListItem = _group.value!!.order.orderItems
                        .indexOf(_group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId))

                } else {
                    posToRefreshInOrderPreviewListItem = -1
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
                Timber.i(e)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    /**
     * Sets the text filter.
     *
     * @param c [CharSequence] to filter the products with.
     */
    fun filterListString(c: CharSequence) {
        filterString = c.toString().toLowerCase()
        filterList()
    }

    /**
     * Sets the category filter.
     *
     * @param s The string value of the category you want to filter on.
     */
    fun filterListCategoryName(s: String) {
        filterCategoryName = s
        filterList()
    }

    /**
     * Filter the products in the webshop based on the current value of [filterListString] and [filterListCategoryName].
     */
    private fun filterList() {
        val afterStringFilter = _group.value!!.project.products.filter { product ->
            product.productName.toLowerCase().contains(filterString)
        }
        if (filterCategoryName.isNotEmpty()) {
            _filteredList.value = afterStringFilter.filter { product ->
                product.category!!.categoryName == filterCategoryName
            }
        } else {
            _filteredList.value = afterStringFilter
        }
    }

    /**
     * Handle updates that are triggered in the [ShoppingCartFragment].
     * Completely removes the item if the amount equals 0.
     *
     * @param oi The [OrderItem] that changes.
     * @param add Indicates if an item should be added or subtracted.
     */
    fun changeOrderItemAmount(oi: OrderItem, add: Boolean){
        if(add){
            oi.amount++
            updateOrderItem(oi)
        } else {
            oi.amount--
            if(oi.amount < 1) {
                removeOrderItem(oi)
            } else {
                updateOrderItem(oi)
            }
        }
    }

    /**
     * Sends the updated [OrderItem] to the back-end and update the [LiveData] (on success) or
     * set the API status to trigger an error message on screen.
     *
     * @param oi The [OrderItem] that should be posted to the back-end.
     */
    private fun updateOrderItem(oi: OrderItem){
        viewModelScope.launch {
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = repository.updateOrderItem(oi, oi.orderItemId)

                _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                    .amount = orderItemRes.removedOrAddedOrderItem.amount
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                posToRefreshInOrderPreviewListItem = _group.value!!.order.orderItems
                    .indexOf(_group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId))

                _group.value = _group.value // trigger live data change, moet wss niet?

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

    /**
     * Remove an [OrderItem] from the [Order] and update the [LiveData] (on success) or
     * set the API status to trigger an error message on screen.
     *
     * @param oi The [OrderItem] that should be removed.
     */
    fun removeOrderItem(oi : OrderItem){
        viewModelScope.launch {
            // Fetch the group from the API and try to receive the value from the coroutine job
            // Update the API status accordingly
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = repository.removeOrderItemFromOrder(oi.orderItemId, group.value!!.order.orderId)

                _group.value!!.order.orderItems.remove( _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!)
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                posToRefreshInOrderPreviewListItem = -1

                _group.value = _group.value // trigger live data change, moet wss niet?

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    /**
     * Handle the click on a [Product] in the webshop.
     *
     * @param product The product that is clicked.
     * @param action The action that should be executed (0 = add to order, 1 = navigate to product details).
     */
    fun onProductClicked(product: Product, action: Int) {
        when(action) {
            0 -> addProductToOrder(product)
            1 -> _navigateToWebshop.value = listOf(product.projectId, product.productId)
        }
    }

    /**
     * Reset after error was shown on screen to prevent bugs on configuration changes.
     */
    fun onErrorShown() {
        _status.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}