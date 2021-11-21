package com.example.quizapp.api

import com.example.quizapp.model.BaseModel
import com.example.quizapp.model.Categories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApi {

    @GET("api.php")
    suspend fun getQuestionsForQuiz(
        @Query("amount") amount : Int,
        @Query("type") type : String,
        @Query("category") category : Int
    ) : Response<BaseModel>


    @GET("api_category.php")
    suspend fun getCategories(
    ) : Response<Categories>

}