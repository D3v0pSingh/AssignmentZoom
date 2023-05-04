package com.example.assignment.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val BASE_URL = "https://api.github.com/"
interface getRepository {

    @GET("repos/{OWNER}/{REPO}")
    fun getApiData(@Path("OWNER")owner:String, @Path("REPO")repo:String) : Call<ApiData>
}

object service{
    val instance: getRepository
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        instance = retrofit.create(getRepository::class.java)
    }
}