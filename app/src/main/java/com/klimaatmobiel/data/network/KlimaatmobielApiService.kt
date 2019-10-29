package com.klimaatmobiel.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://10.0.2.2:5001/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KlimaatmobielApiService {
    // API CALLS HERE
    @GET("Group/project")
    fun getProject(@Query("groupCode") type: String):
            Call<GroupProperty>
}

object KlimaatmobielApi {
    val retrofitService: KlimaatmobielApiService by lazy {
        retrofit.create(KlimaatmobielApiService::class.java)
    }
}
