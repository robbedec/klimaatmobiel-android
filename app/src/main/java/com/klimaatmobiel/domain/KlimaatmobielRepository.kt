package com.klimaatmobiel.domain

import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import kotlinx.coroutines.Deferred

class KlimaatmobielRepository(private val apiService: KlimaatmobielApiService) {

    fun getFullGroup(groupCode: String): Deferred<Group> {
        return apiService.getFullGroup(groupCode)
    }

    fun getProduct(projectId: Long, productId: Long) : Deferred<Product> {
        return apiService.getProduct(projectId, productId)
    }

    fun addProductToOrder(orderItem: OrderItem, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.addProductToOrder(orderItem, orderId)
    }

    fun updateOrderItem(orderItem: OrderItem, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.updateOrderItem(orderItem, orderId)
    }

    fun removeOrderItemFromOrder(orderItemId: Long, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.removeOrderItemFromOrder(orderItemId, orderId)
    }
}