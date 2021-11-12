package com.example.quizapp.api

import com.example.quizapp.model.BaseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("api.php")
    suspend fun getQuestionsForQuiz(
        @Query("amount") amount : Int
    ) : Response<BaseModel>
}