package com.lkb.fbquizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.quiz_activity.*

class QuizActivity : AppCompatActivity() {
    lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)
        val quizViewModel: QuizViewModel = ViewModelProvider(this, QuizViewModelFactory())
            .get(QuizViewModel::class.java)

        disposable = quizViewModel.callQuizApi()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                textView.text = it[0].toString();
            }
    }
}
