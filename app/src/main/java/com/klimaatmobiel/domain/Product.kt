package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product(val productId : Long, val productName : String, val description : String,
              val productImage : String?, val projectId : Long, val price : Double,
              val categoryId : Long, val category : Category?) : Parcelable {


}