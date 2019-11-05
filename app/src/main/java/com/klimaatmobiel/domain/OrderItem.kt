package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderItem(val orderItemId : Long, var amount : Int, val product : Product?, val productId : Long, val orderId : Long) : Parcelable {


}