package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(val categoryId : Long, val categoryName : String, val categoryDescr : String) : Parcelable {
}