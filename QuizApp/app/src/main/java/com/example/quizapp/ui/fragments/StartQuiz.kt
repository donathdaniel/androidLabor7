package com.example.quizapp.ui.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.QuestionViewModelFactory
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentStartQuizBinding
import com.example.quizapp.repository.Repository
import com.example.quizapp.shared.MyViewModel
import com.example.quizapp.viewModel.QuestionViewModel

class StartQuiz : Fragment() {

    val sharedView : MyViewModel by activityViewModels()
    val REQUEST_SELECT_CONTACT = 1
    lateinit var contactUri: Uri
    lateinit var binding: FragmentStartQuizBinding
    lateinit var viewModel : QuestionViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }

        val repository = Repository()
        val viewModelFactory = QuestionViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(QuestionViewModel::class.java)


        binding.getStartedButton.setOnClickListener {
            if(binding.editTextTextPersonName.text.isEmpty()) {
                Toast.makeText(this.context, "You did not give a name!", Toast.LENGTH_SHORT).show()
            }
            else{
                sharedView.playerName = binding.editTextTextPersonName.text.toString()

                //////////////
                viewModel.getQuestionsForQuiz(10)
                viewModel.myQuizQuestions.observe(viewLifecycleOwner, Observer { response ->
                    if(response.isSuccessful){
                        println("nice")
                    }
                    else{
                        println("not nice")
                    }
                })

                //////////////

                sharedView.startQuiz()
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                if(sharedView.typeOfNewxtQuestion() == 1) {
                    fragmentTransaction.replace(R.id.fragment_container,CurrentQuizRadiobutton())
                }
                else{
                    fragmentTransaction.replace(R.id.fragment_container,CurrentQuizCheckbox())
                }
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK){
            contactUri = data!!.data!!
            getContactName()
        }
    }

    @SuppressLint("Range")
    private fun getContactName() {
        val cursor = requireActivity().contentResolver.query(contactUri,null,null,null,null)

        if(cursor!!.moveToFirst()){
            val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            val editTextTextPersonName = view?.findViewById<EditText>(R.id.editTextTextPersonName)
            editTextTextPersonName?.setText(contactName)

        }

        cursor.close()
    }

}
