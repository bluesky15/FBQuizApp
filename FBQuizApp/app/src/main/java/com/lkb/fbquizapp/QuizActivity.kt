package com.lkb.fbquizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quiz_activity.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class QuizActivity : AppCompatActivity() {
    var enteredAnswer = ""
    var correctAnswer = ""
    var counter = 0
    lateinit var disposable: Disposable
    lateinit var quizLists: QuizModelList.QuizModel
    private val correctAnswerPoint = 20
    private val incorrectAnswerPoint = -10
    private var timerDisposable: Disposable? = null
    var totalPoints = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)
        val quizViewModel: QuizViewModel = ViewModelProvider(this, QuizViewModelFactory())
            .get(QuizViewModel::class.java)

        disposable = quizViewModel.callQuizApi()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                // textView.text = it[0].toString();
                quizLists = it
                loadQuiz()
            }

        tvSubmit.setOnClickListener {
            counter += 1
            if (counter < 5) {
                if (tvTimer.text.toString().toInt() == 0 || enteredAnswer != correctAnswer) {
                    totalPoints += incorrectAnswerPoint
                } else {
                    totalPoints += tvTimer.text.toString().toInt() + correctAnswerPoint
                }
                timerDisposable?.dispose()
                tvTimer.text = 0.toString()
                tvTotalPoint.text = totalPoints.toString()

                loadQuiz()
            } else {
                tvSubmit.text = "See Top Results"
                timerDisposable?.dispose()
                startActivity(Intent(this, TopResultsActivity::class.java))
            }

        }

    }

    private fun loadQuiz() {
        val length = quizLists.size
        val index = Random.nextInt(0, length)
        val quiz = quizLists[index]
        tvQuestion.text = quiz.question
        radio_one.text = quiz.options[0]
        radio_two.text = quiz.options[1]
        radio_three.text = quiz.options[2]
        radio_four.text = quiz.options[3]
        correctAnswer = quiz.answer
        startTimer()
    }

    private fun startTimer() {
        timerDisposable = object : CountDownTimer(10L, TimeUnit.SECONDS) {
            override fun onTick(tickValue: Long) {
                Log.d("CountDown", "Remaining: $tickValue")
                tvTimer.text = tickValue.toString()
            }

            override fun onFinish() {
                Log.d("CountDown", "The End!! ")
                tvTimer.text = 0.toString()
            }
        }.start()
    }

    fun checkButton(v: View?) {
        val radioId: Int = radioGroup.checkedRadioButtonId
        // val radioButton = findViewById<RadioButton>(radioId);
        enteredAnswer = when (radioId) {
            R.id.radio_one -> "A"
            R.id.radio_two -> "B"
            R.id.radio_three -> "C"
            R.id.radio_four -> "D"
            else -> ""
        }
    }
}
