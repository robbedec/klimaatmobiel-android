package com.klimaatmobiel.domain

import com.klimaatmobiel.data.database.ProductsDatabase
import com.klimaatmobiel.data.database.asDomainModel
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KlimaatmobielRepository(private val apiService: KlimaatmobielApiService, private val database: ProductsDatabase) {

    /**
     * Retrieve information about a group and the project that is linked to it.
     *
     * @param groupCode a unique value that links a group to a project.
     * @return a group object that contains information about itself and the project its registered in.
     */
    suspend fun getFullGroup(groupCode: String): Group {
        return apiService.getFullGroupAsync(groupCode).await()
    }

    /**
     * Add a new product to the order of the current [Group].
     *
     * @param orderItem the new item that is added to the [Order].
     * @param orderId the unique identifier of the modified [Order].
     * @return the updated order or an error message from the back-end.
     */
    suspend fun addProductToOrder(orderItem: OrderItem, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.addProductToOrderAsync(orderItem, orderId).await()
    }

    /**
     * Update the amount of a [Product] on a single [Order].
     *
     * @param orderItem the [OrderItem] with the new amount.
     * @param orderId the unique identifier of the updated [Order].
     * @return the updated order or an error message from the back-end.
     */
    suspend fun updateOrderItem(orderItem: OrderItem, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.updateOrderItemAsync(orderItem, orderId).await()
    }

    /**
     * Remove a [Product] from the [Order] of the current [Group].
     *
     * @param orderItemId the unique identifier of the product that will be removed from the [Order].
     * @param orderId the unique identifier of the [Order] that will update.
     * @return the updated order or an error message from the back-end.
     */
    suspend fun removeOrderItemFromOrder(orderItemId: Long, orderId: Long): RemoveOrAddedOrderItemDTO {
        return apiService.removeOrderItemFromOrderAsync(orderItemId, orderId).await()
    }

    /**
     * Retrieve a cached [Product] from the database.
     *
     * @projectId the unique identifier of the [Project] that contains the [Product].
     * @productId the unique identifier of the [Product] that will be retrieved.
     * @return the [Product] with corresponding id's.
     */
    suspend fun getProduct(projectId: Long, productId: Long) : Product {
        return withContext(Dispatchers.IO) {
            database.productDao.getProduct(projectId, productId).asDomainModel()
        }
    }

    /**
     * Update the database with (new or existing) products when the project is retrieved from the API.
     *
     * @param products a [List] of [Product]
     */
    suspend fun refreshProducts(products: List<Product>) {
        withContext(Dispatchers.IO) {
            database.productDao.insertAll(products.asDatabaseModel())
        }
    }
}