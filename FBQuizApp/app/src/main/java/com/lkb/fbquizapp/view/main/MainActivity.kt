package com.lkb.fbquizapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.lkb.fbquizapp.*
import com.lkb.fbquizapp.view.quiz.QuizActivity
import com.lkb.fbquizapp.view.result.ResultActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private val languages = arrayOf("Male", "Female")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.select_dialog_singlechoice,
            languages
        )
        userGender.threshold = 1
        userGender.setAdapter(adapter)
        ivAllTimeTopResults.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        button.setOnClickListener {
            createIntent()?.let { i -> startActivity(i) }
        }
    }

    private fun createIntent(): Intent? {
        var intent: Intent? = null
        val validGenderInput =
            arrayOf("M", "F", "m", "f", "male", "female", "MALE", "FEMALE", "Male", "Female")
        try {
            if (validGenderInput.contains(userGender.text.toString())
                && userAge.text.toString().toInt() != 0
            ) {
                intent = Intent(this, QuizActivity::class.java)
                intent.putExtra(NAME, userName.text.toString())
                intent.putExtra(AGE, userAge.text.toString())
                intent.putExtra(GENDER, userGender.text.toString())
            } else {
                Toast.makeText(this, VALID_INPUT, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, VALID_INPUT, Toast.LENGTH_SHORT).show()
        }
        return intent
    }
}