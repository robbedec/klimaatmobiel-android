package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(val categoryId : Long, val categoryName : String, val categoryDescr : String) : Parcelable {
}