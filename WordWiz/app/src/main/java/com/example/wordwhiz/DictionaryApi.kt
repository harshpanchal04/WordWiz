package com.example.wordwhiz

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryApi {
    @GET("en/{word}")
    suspend fun getMeaning(@Path("word")word:String): Response<List<WordResult>>
}