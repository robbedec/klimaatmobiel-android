package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order(val orderId : Long, val time : String, val submitted : Boolean,
            val approved : Boolean, val totalOrderPrice: Double, val groupId : Long, val orderItems : List<OrderItem>) : Parcelable {
}