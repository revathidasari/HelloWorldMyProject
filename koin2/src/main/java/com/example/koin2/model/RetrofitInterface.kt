package com.example.koin2.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitInterface {

    companion object {
        val BASE_URL: String = "https://simplifiedcoding.net/demos/"

        fun startAPICall() : RetrofitInterface{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(RetrofitInterface::class.java)
        }

    }

    @GET("marvel")
    fun getListOfData() : Call<List<POJO>>
}