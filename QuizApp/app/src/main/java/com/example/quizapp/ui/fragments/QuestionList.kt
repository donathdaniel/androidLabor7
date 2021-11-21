package com.example.quizapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.OnQuestionClickListener
import com.example.quizapp.R
import com.example.quizapp.RecyclerViewAdapter
import com.example.quizapp.databinding.FragmentQuestionListBinding
import com.example.quizapp.shared.MyViewModel
import com.example.quizapp.shared.Question
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged


class QuestionList : Fragment(), OnQuestionClickListener {

    val sharedView : MyViewModel by activityViewModels()
    lateinit var binding: FragmentQuestionListBinding
    private lateinit var mQuestionListAdapter: RecyclerViewAdapter

//    private val mOnQuestionClickListener = object : OnQuestionClickListener {
//
//        override fun onDelete(model: Question) {
//            // just remove the item from list
//            mQuestionListAdapter.removeProduct(model)
//        }
//        override fun onDetails(model: Question) {
//            val fragmentTransaction = parentFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragment_container,QuestionDetail(model))
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        mQuestionListAdapter = RecyclerViewAdapter(this)

        recycler_view.adapter = mQuestionListAdapter
        mQuestionListAdapter.setData(sharedView.questions)



        var textField = binding.textField

        sharedView.myQuizQuestions.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            sharedView.mapToQuestions()
            mQuestionListAdapter.setData(sharedView.questions)
        })

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, sharedView.categories)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (textField.editText as? AutoCompleteTextView)?.doOnTextChanged { text, start, before, count ->

            val id = sharedView.categories.find { it.name == text.toString() }?.id
            sharedView.getQuestionsForQuiz(20,"multiple", id!!)

        }


    }

    override fun onDelete(model: Question) {
        // just remove the item from list
           mQuestionListAdapter.removeProduct(model)
    }

    override fun onDetails(model: Question) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,QuestionDetail(model))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}

