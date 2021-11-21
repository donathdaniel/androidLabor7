package com.example.quizapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.example.quizapp.R
import com.example.quizapp.shared.MyViewModel
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.quizapp.databinding.FragmentCurrentQuizCheckboxBinding
import com.example.quizapp.shared.Question


class CurrentQuizCheckbox : Fragment() {

    val sharedView: MyViewModel by activityViewModels()
    lateinit var binding: FragmentCurrentQuizCheckboxBinding
    lateinit var listOfCheckbox: List<CheckBox>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentQuizCheckboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //back button
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val dialogClickListener =
                        DialogInterface.OnClickListener { dialog, which ->
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                val fragmentTransaction = parentFragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.fragment_container, StartQuiz())
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                            }
                        }

                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("Are you sure you want to quit?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show()

                }
            })

        val question = sharedView.getQuestion()

        binding.questionText.setText(question.text)

        val answer1 = binding.answer1
        val answer2 = binding.answer2
        val answer3 = binding.answer3
        val answer4 = binding.answer4
        listOfCheckbox = listOf(answer1, answer2, answer3, answer4)
        listOfCheckbox.forEachIndexed { index, item ->
            item.text = question.answers?.get(index)?.first
        }


        binding.nextButton.setOnClickListener {
            if (oneAnswerChecked()) {
                val number: MutableList<Int> = mutableListOf()
                listOfCheckbox.forEachIndexed { index, item ->
                    if (item.isChecked)
                        number.add(index)
                }
                sharedView.calculateResult(question, number)

                val fragmentTransaction = parentFragmentManager.beginTransaction()
                if (sharedView.endOfQuiz()) {

                    fragmentTransaction.replace(R.id.fragment_container, StartQuiz())
                } else {
                    if (sharedView.typeOfNewxtQuestion() == 1) {
                        fragmentTransaction.replace(
                            R.id.fragment_container,
                            CurrentQuizRadiobutton()
                        )
                    } else {
                        fragmentTransaction.replace(R.id.fragment_container, CurrentQuizCheckbox())
                    }
                }
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }


    private fun oneAnswerChecked(): Boolean {
        listOfCheckbox.forEach { item ->
            if (item.isChecked)
                return true
        }
        return false
    }

}