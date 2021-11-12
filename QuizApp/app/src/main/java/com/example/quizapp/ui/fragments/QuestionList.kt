package com.example.quizapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.OnQuestionClickListener
import com.example.quizapp.R
import com.example.quizapp.RecyclerViewAdapter
import com.example.quizapp.databinding.FragmentQuestionListBinding
import com.example.quizapp.shared.MyViewModel
import com.example.quizapp.shared.Question


class QuestionList : Fragment() {

    val sharedView : MyViewModel by activityViewModels()
    lateinit var binding: FragmentQuestionListBinding
    private lateinit var mQuestionListAdapter: RecyclerViewAdapter

    private val mOnQuestionClickListener = object : OnQuestionClickListener {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionList = sharedView.questions

        val recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.setHasFixedSize(true)

        mQuestionListAdapter = RecyclerViewAdapter(questionList,mOnQuestionClickListener)
        recycler_view.adapter = mQuestionListAdapter
    }

}

