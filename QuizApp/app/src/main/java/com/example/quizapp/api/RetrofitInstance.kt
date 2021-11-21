package com.example.quizapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://opentdb.com/"

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : QuestionApi by lazy{
        retrofit.create(QuestionApi::class.java)
    }
}