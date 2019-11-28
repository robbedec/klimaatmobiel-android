package com.klimaatmobiel.domain

import com.klimaatmobiel.data.database.ProductsDatabase
import com.klimaatmobiel.data.database.asDomainModel
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KlimaatmobielRepository(private val apiService: KlimaatmobielApiService, private val database: ProductsDatabase) {

    suspend fun getFullGroup(groupCode: String): Group {
        return apiService.getFullGroupAsync(groupCode).await()
    }

    suspend fun addProductToOrder(orderItem: OrderItem, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.addProductToOrderAsync(orderItem, orderId).await()
    }

    suspend fun updateOrderItem(orderItem: OrderItem, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.updateOrderItemAsync(orderItem, orderId).await()
    }

    suspend fun removeOrderItemFromOrder(orderItemId: Long, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.removeOrderItemFromOrderAsync(orderItemId, orderId).await()
    }

    suspend fun getProduct(projectId: Long, productId: Long) : Product {
        return withContext(Dispatchers.IO) {
            database.productDao.getProduct(projectId, productId).asDomainModel()
        }
    }

    suspend fun refreshProducts(products: List<Product>) {
        withContext(Dispatchers.IO) {
            database.productDao.insertAll(products.asDatabaseModel())
        }
    }
}