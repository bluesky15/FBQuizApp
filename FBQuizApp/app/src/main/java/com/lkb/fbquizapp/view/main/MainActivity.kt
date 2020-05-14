package com.lkb.fbquizapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.lkb.fbquizapp.BaseActivity
import com.lkb.fbquizapp.R
import com.lkb.fbquizapp.view.quiz.QuizActivity
import com.lkb.fbquizapp.view.result.ResultActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ivAllTimeTopResults.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        button.setOnClickListener {
            try {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("name", userName.text.toString())
                intent.putExtra("age", userAge.text.toString())
                intent.putExtra("sex", userSex.text.toString())
                startActivity(intent)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please Provide Valid Input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}