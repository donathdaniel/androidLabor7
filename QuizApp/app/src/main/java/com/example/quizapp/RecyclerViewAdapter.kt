package com.example.quizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.shared.Question
import com.example.quizapp.ui.fragments.StartQuiz

class RecyclerViewAdapter(private val questionList : MutableList<Question>, private val mOnProductClickListener: OnQuestionClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {

    inner class RecyclerViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val question : TextView = itemView.findViewById(R.id.question)
        val numberOfQuestion : TextView = itemView.findViewById(R.id.numberOfQuestion)
        val correctAnswer : TextView = itemView.findViewById(R.id.correctAnswer)

        val detailsButton : Button = itemView.findViewById(R.id.detailsButton)
        val deleteButton : Button = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_example_item,parent,false)
        val holder = RecyclerViewViewHolder(itemView)

        // to see details
        holder.detailsButton.setOnClickListener {
            val position = holder.adapterPosition
            val model = questionList[position]
            mOnProductClickListener.onDetails(model)
        }

        // to delete the item in recycler view
        holder.deleteButton.setOnClickListener {
            val position = holder.adapterPosition
            val model = questionList[position]
            mOnProductClickListener.onDelete(model)
        }
        return holder
    }


    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val currentItem = questionList[position]

        holder.question.text = currentItem.text

        var numberOfCorrectAnswers = 0
        var textOfTheCorrectAnswer = ""
        for(q in currentItem.answers){
            if(q.second) {
                numberOfCorrectAnswers++
                textOfTheCorrectAnswer = q.first
            }
        }

        if(numberOfCorrectAnswers == 1)
            holder.numberOfQuestion.text = "Single answer"
        else
            holder.numberOfQuestion.text = "Multiple answers"

        holder.correctAnswer.text = textOfTheCorrectAnswer
    }

    override fun getItemCount(): Int = questionList.size

    fun removeProduct(model: Question) {
        val position = questionList.indexOf(model)
        questionList.remove(model)
        notifyItemRemoved(position)
    }
}

interface OnQuestionClickListener {

    /**
     * when the user clicks on delete icon this method will be invoked to remove item at position.
     */
    fun onDelete(model: Question)
    /**
     * when the user clicks on details icon this method will be invoked to show details about the item at position.
     */
    fun onDetails(model: Question)
}