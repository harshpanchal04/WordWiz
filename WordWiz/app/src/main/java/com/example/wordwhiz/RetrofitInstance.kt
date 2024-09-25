package com.example.wordwhiz

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL="https://api.dictionaryapi.dev/api/v2/entries/"


object RetrofitInstance{
    private fun getInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi: DictionaryApi= getInstance().create(DictionaryApi::class.java)
}