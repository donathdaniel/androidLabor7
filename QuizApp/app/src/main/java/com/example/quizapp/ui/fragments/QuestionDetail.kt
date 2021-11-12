package com.example.quizapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.quizapp.databinding.FragmentQuestionDetailBinding
import com.example.quizapp.shared.Question


class QuestionDetail(val model : Question) : Fragment() {

    lateinit var binding : FragmentQuestionDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.question.text = model.text

        val answersList = mutableListOf<TextView>(binding.answer1,binding.answer2,binding.answer3,binding.answer4)

        var numberOfCorrectAnswers = 0
        for(q in model.answers.withIndex()){
            if(q.value.second) {
                numberOfCorrectAnswers++
                answersList[q.index].setTextColor(Color.parseColor("#008080"))
            }
            answersList[q.index].text = q.value.first
        }

        if(numberOfCorrectAnswers == 1)
            binding.numberOfQuestion.text = "Single answer"
        else
            binding.numberOfQuestion.text = "Multiple answers"


    }

}