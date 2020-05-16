package com.lkb.fbquizapp.view.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import kotlin.random.Random


class QuizActivity : BaseActivity() {
    private var enteredAnswer = ""
    private var correctAnswer = ""
    private var counter = 0
    private var currentUser: User? = null
    private lateinit var viewModel: QuizViewModel
    private lateinit var disposable: Disposable
    private lateinit var quizLists: QuizModelList.QuizModel
    private val correctAnswerPoint = 20
    private val incorrectAnswerPoint = -10
    private var timerDisposable: Disposable? = null
    private var totalPoints = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)
        val intent = intent
        viewModel = get()
        try {
            currentUser = User(
                intent.getStringExtra(NAME),
                intent.getStringExtra(AGE).toInt(),
                intent.getStringExtra(GENDER),
                0
            )
        } catch (e: NumberFormatException) {
            Toast.makeText(this, VALID_INPUT, Toast.LENGTH_SHORT).show()
        }

        disposable = viewModel.callQuizApi()
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
                tvSubmit.text = SEE_TOP_RESULTS
                tvTimer.text = 0.toString()
                timerDisposable?.dispose()
                currentUser?.let {
                    it.score = totalPoints
                    viewModel.saveUserData(currentUser!!)
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

    }

    private fun loadQuiz() {
        val length = quizLists.size
        val index = Random.nextInt(0, length)
        val quiz = quizLists[index]
        tvQuestion.text = quiz.question.toUpperCase()
        radio_one.text = quiz.options[0].toUpperCase()
        radio_two.text = quiz.options[1].toUpperCase()
        radio_three.text = quiz.options[2].toUpperCase()
        radio_four.text = quiz.options[3].toUpperCase()
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
        enteredAnswer = when (radioId) {
            R.id.radio_one -> "A"
            R.id.radio_two -> "B"
            R.id.radio_three -> "C"
            R.id.radio_four -> "D"
            else -> ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        timerDisposable?.dispose()
    }
}
