package com.lkb.fbquizapp.view.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.lkb.fbquizapp.*
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import com.lkb.fbquizapp.util.CountDownTimer
import com.lkb.fbquizapp.view.result.ResultActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quiz_activity.*
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit


class QuizActivity : BaseActivity() {
    private var enteredAnswer = ""
    private var correctAnswer = ""
    private var counter = 0
    private var isDataAvailable = false
    private lateinit var viewModel: QuizViewModel
    private val correctAnswerPoint = 20
    private val incorrectAnswerPoint = -10
    private var timerDisposable: Disposable? = null
    private var totalPoints = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)
        val intent = intent
        viewModel = get()
        viewModel.currentUser = User(
            intent.getStringExtra(NAME),
            intent.getStringExtra(AGE).toInt(),
            intent.getStringExtra(GENDER),
            0
        )
        viewModel.data.observe(this, Observer<QuizModelList.Quiz> {
            tvQuestion.text = it.question.toUpperCase()
            radio_one.text = it.options[0].toUpperCase()
            radio_two.text = it.options[1].toUpperCase()
            radio_three.text = it.options[2].toUpperCase()
            radio_four.text = it.options[3].toUpperCase()
            correctAnswer = it.answer
            startTimer()
        })
        viewModel.callQuizApi().observe(this, Observer { success ->
                if (success) {
                    isDataAvailable = true
                    viewModel.nextQuestion()
                } else {
                    isDataAvailable = false
                    Toast.makeText(this, "Error loading Data", Toast.LENGTH_SHORT).show()
                }
            })

        tvSubmit.setOnClickListener {
            counter += 1
            if (counter < 5) {
                totalPoints += if (tvTimer.text.toString().toInt() == 0 || enteredAnswer != correctAnswer) {
                    incorrectAnswerPoint
                } else {
                    tvTimer.text.toString().toInt() + correctAnswerPoint
                }
                timerDisposable?.dispose()
                tvTimer.text = 0.toString()
                tvTotalPoint.text = totalPoints.toString()
                viewModel.nextQuestion()
            } else {
                tvSubmit.text = SEE_TOP_RESULTS
                tvTimer.text = 0.toString()
                timerDisposable?.dispose()
                viewModel.updateScore(totalPoints)
                viewModel.saveUserData()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        startActivity(
                            Intent(
                                this,
                                ResultActivity::class.java
                            )
                        )
                    }) { throwable -> Log.d(localClassName, "" + throwable) }
            }

        }

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
        enteredAnswer = viewModel.mapOptionToString(radioGroup.checkedRadioButtonId)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerDisposable?.dispose()
    }
}
