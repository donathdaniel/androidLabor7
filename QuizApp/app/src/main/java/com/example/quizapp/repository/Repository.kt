package com.example.quizapp.repository

import com.example.quizapp.api.RetrofitInstance
import com.example.quizapp.model.BaseModel
import retrofit2.Response

class Repository {

    suspend fun getQuestionsForQuiz(amount: Int) : Response<BaseModel>{
        return RetrofitInstance.api.getQuestionsForQuiz(amount)
    }
}