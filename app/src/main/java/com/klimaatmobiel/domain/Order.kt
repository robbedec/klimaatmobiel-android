package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order(val orderId : Long, val time : String, val submitted : Boolean,
            val approved : Boolean, var totalOrderPrice: Double, var avgScore: Double, val groupId : Long, var orderItems : MutableList<OrderItem>) : Parcelable {


}