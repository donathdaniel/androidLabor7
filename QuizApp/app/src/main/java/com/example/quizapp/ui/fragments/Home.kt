package com.example.quizapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentHomeBinding


class Home : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var testSkills : Button
    lateinit var readQuestions : Button
    lateinit var createQuestion : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        testSkills = binding.tesSkills
        readQuestions  = binding.readQuestions
        createQuestion = binding.createQuestion
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testSkills.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container,StartQuiz()).addToBackStack(null).commit()
        }

        readQuestions.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container,QuestionList()).addToBackStack(null).commit()
        }

        createQuestion.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container,CreateQuestion()).addToBackStack(null).commit()
        }
    }
}