package com.klimaatmobiel.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Group(val groupId: Long, val groupName: String, val projectId: Double, val uniqueGroupCode: String) : Parcelable {

}