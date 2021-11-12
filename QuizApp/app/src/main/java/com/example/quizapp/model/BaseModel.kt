package com.example.quizapp.model

import com.google.gson.annotations.SerializedName

data class BaseModel(
    @SerializedName("response_code")
    val responseCode : Int,
    val results : List<Results>
)

data class Results(
    val category : String,
    val type : String,
    val difficulty : String,
    val question : String,
    val correct_answer : String,
    val incorrect_answers : List<String>

)