package com.lkb.fbquizapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lkb.fbquizapp.BaseActivity
import com.lkb.fbquizapp.R
import com.lkb.fbquizapp.model.persistance.User
import com.lkb.fbquizapp.view.quiz.QuizActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainViewModel: MainViewModel = ViewModelProvider(this,
            MainViewModelFactory(this)
        )
            .get(MainViewModel::class.java)

        button.setOnClickListener {
            try {
                val name = userName.text.toString()
                var userGender = userSex.text.toString()
                val age = userAge.text.toString().toInt()
                val user = User(
                    name,
                    age,
                    userGender,
                    0
                )
                mainViewModel.addUser(user)
                    .subscribe { e ->
                        if (e) {
                            startActivity(Intent(this, QuizActivity::class.java))
                        }
                    }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please Provide Valid Input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}