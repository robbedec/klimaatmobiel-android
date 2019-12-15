package com.klimaatmobiel.domain.DTOs

import android.os.Parcel
import android.os.Parcelable
import com.klimaatmobiel.domain.Order
import com.klimaatmobiel.domain.OrderItem
import kotlinx.android.parcel.Parcelize


@Parcelize
class RemoveOrAddedOrderItemDTO(val totalOrderPrice : Double, val avgScore: Double, val removedOrAddedOrderItem : OrderItem): Parcelable {
}