package com.lkb.fbquizapp

import androidx.lifecycle.ViewModel
import io.reactivex.Observable

class QuizViewModel : ViewModel() {
    private val quizApiService by lazy {
        QuizApiService.create()
    }

    fun callQuizApi(): Observable<QuizModelList.QuizModel> {
        return quizApiService.getQuiz()
    }
}