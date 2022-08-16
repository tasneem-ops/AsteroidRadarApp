package com.example.asteroid_radar_app.network


import com.example.asteroid_radar_app.Constants
import com.example.asteroid_radar_app.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PicApiService{
    @GET ("planetary/apod?api_key=${Constants.API_KEY}")
    suspend fun getProperties(): PictureOfDay
}

object PicApi {
    val retrofitService : PicApiService by lazy {
        retrofit.create(PicApiService::class.java)
    }
}