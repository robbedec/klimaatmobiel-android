package com.klimaatmobiel.data.network

import com.squareup.moshi.Json

data class GroupProperty (
    @Json(name = "groupId") val id: Double,
    @Json(name = "groupName") val name: String,
    val projectId: Double,
    val uniqueGroupCode: String
)
