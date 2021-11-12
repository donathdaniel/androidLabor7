package com.example.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.repository.Repository
import com.example.quizapp.viewModel.QuestionViewModel

class QuestionViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuestionViewModel(repository) as T
    }
}