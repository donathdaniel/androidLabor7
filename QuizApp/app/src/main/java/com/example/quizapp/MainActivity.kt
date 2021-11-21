package com.example.quizapp

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.quizapp.model.QuestionCategory
import com.example.quizapp.shared.MyViewModel
import com.example.quizapp.ui.fragments.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    val sharedView : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //navigation drawer
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            drawerLayout.open()
        }

        sharedView.myQuizQuestions.observe(this, androidx.lifecycle.Observer { response ->
            sharedView.mapToQuestions()
        })

        sharedView.getCategories()
        sharedView.mQuizCategories.observe(this, androidx.lifecycle.Observer { response ->
            sharedView.categories = response.body()?.triviaCategories as MutableList<QuestionCategory>
        })

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        //navigation
        navigationView.setNavigationItemSelectedListener{ menuItem ->
            menuItem.isChecked = true
            drawerLayout.close()
            when (menuItem.itemId) {
                R.id.navigationItem_home -> {
                    /*supportFragmentManager.commit {
                        replace(R.id.fragment_container, Home())
                        setReorderingAllowed(true)
                        addToBackStack(null)
                    }*/
                    replaceFragment(Home(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }

                R.id.navigationItem_quizTime -> {
                    replaceFragment(StartQuiz(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }

                R.id.navigationItem_profile -> {
                    replaceFragment(Profile(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }

                R.id.navigationItem_listOfQuestions -> {
                    replaceFragment(QuestionList(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }

                R.id.navigationItem_newQuestion -> {
                    replaceFragment(CreateQuestion(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }

                else -> {
                    replaceFragment(Home(),R.id.fragment_container)
                    return@setNavigationItemSelectedListener true
                }
            }
        }
    }

    fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack:Boolean = false, withAnimation:Boolean = false){
        val transaction = supportFragmentManager.beginTransaction()
/*        when(withAnimation){
            true -> {
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            }
        }*/
        transaction.replace(containerId, fragment)
        when(addToBackStack){
            true -> {
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }

}