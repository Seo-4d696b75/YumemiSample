package com.example.yumemisampleapp

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * @author Seo-4d696b75
 * @version 2020/12/04.
 */
private val service : APIService by lazy {

    val okHttpClient = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    retrofit.create(APIService::class.java)

}

fun getAPIService() = service


interface APIService{
    @GET("repos/googlesamples/android-architecture-components/contributors")
    suspend fun getContributors() : List<JsonObject>
}
