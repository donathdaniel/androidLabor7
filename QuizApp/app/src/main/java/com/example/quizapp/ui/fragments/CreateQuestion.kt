package com.example.quizapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentCreateQuestionBinding
import com.example.quizapp.shared.MyViewModel
import com.example.quizapp.shared.Question


class CreateQuestion : Fragment() {
    val sharedView : MyViewModel by activityViewModels()
    lateinit var binding: FragmentCreateQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateQuestionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTexts = mutableListOf(binding.question, binding.answer1, binding.answer2, binding.answer3, binding.answer4)
        binding.addQuestion.setOnClickListener {
            var counter = 0
            for(e in editTexts){
                if(!e.text.isEmpty()){
                    counter++
                }
            }
            if(counter!=5){
                Toast.makeText(this.context, "You did not gave a question and four answers!", Toast.LENGTH_SHORT).show()
            }
            else{
                val q = Question(mutableListOf(Pair( binding.answer1.text.toString(),true),Pair( binding.answer2.text.toString(),false),Pair( binding.answer3.text.toString(),false),Pair( binding.answer4.text.toString(),false)),binding.question.text.toString())
                sharedView.questions.add(q)
                Toast.makeText(this.context, "Your question was saved!", Toast.LENGTH_SHORT).show()

                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container,Home())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }
}