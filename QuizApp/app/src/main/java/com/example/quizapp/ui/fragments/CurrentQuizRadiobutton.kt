package com.example.quizapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.quizapp.R
import com.example.quizapp.shared.MyViewModel
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import android.content.DialogInterface
import com.example.quizapp.databinding.FragmentCurrentQuizRadiobuttonBinding


class CurrentQuizRadiobutton : Fragment() {

    val sharedView : MyViewModel by activityViewModels()
    lateinit var binding: FragmentCurrentQuizRadiobuttonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentQuizRadiobuttonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //back button
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialogClickListener =
                    DialogInterface.OnClickListener { _, which ->
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.fragment_container,StartQuiz())
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }

                    }

                val builder = AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to quit?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()

            }
        })

        val question = sharedView.getQuestion()

        binding.questionText.setText(question.text)

        val radioGroup = binding.radioGroup
        for (i in 0 until radioGroup.childCount) {
            (radioGroup.getChildAt(i) as RadioButton).text = question.answers?.get(i)?.first
        }


        binding.nextButton.setOnClickListener {

            if(oneAnswerChecked()){
                val number : MutableList<Int> = mutableListOf()
                for (i in 0 until radioGroup.childCount) {
                    if((radioGroup.getChildAt(i) as RadioButton).isChecked)
                        number.add(i)
                }
                sharedView.calculateResult(question, number)

                val fragmentTransaction = parentFragmentManager.beginTransaction()
                if(sharedView.endOfQuiz()) {
                    fragmentTransaction.replace(R.id.fragment_container,ResultQuiz())
                }
                else {
                    if(sharedView.typeOfNewxtQuestion() == 1) {
                        fragmentTransaction.replace(R.id.fragment_container,CurrentQuizRadiobutton())
                    }
                    else{
                        fragmentTransaction.replace(R.id.fragment_container,CurrentQuizCheckbox())
                    }
                }
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

    }


    private fun oneAnswerChecked() : Boolean{
        val radioGroup = binding.radioGroup
        for (i in 0 until radioGroup.childCount) {
            if((radioGroup.getChildAt(i) as RadioButton).isChecked)
                return true
        }
        return false
    }

}