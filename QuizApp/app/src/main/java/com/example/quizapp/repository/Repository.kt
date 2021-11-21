package com.example.quizapp.repository

import com.example.quizapp.api.RetrofitInstance
import com.example.quizapp.model.BaseModel
import com.example.quizapp.model.Categories
import retrofit2.Response

class Repository {

    suspend fun getQuestionsForQuiz(amount: Int, type: String, category: Int) : Response<BaseModel>{
        return RetrofitInstance.api.getQuestionsForQuiz(amount,type,category)
    }

    suspend fun getCategories() : Response<Categories>{
        return RetrofitInstance.api.getCategories()
    }
}