package com.example.quizapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.BaseModel
import com.example.quizapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class QuestionViewModel(private val repository: Repository) : ViewModel() {

    var myQuizQuestions : MutableLiveData<Response<BaseModel>> = MutableLiveData()

    fun getQuestionsForQuiz(amount: Int){
        viewModelScope.launch {
            val response = repository.getQuestionsForQuiz(amount)
            myQuizQuestions.value = response
        }
    }
}