package com.lkb.fbquizapp.view

import androidx.lifecycle.ViewModel
import com.lkb.fbquizapp.model.QuizApiService
import com.lkb.fbquizapp.model.QuizModelList
import io.reactivex.Observable

class QuizViewModel : ViewModel() {
    private val quizApiService by lazy {
        QuizApiService.create()
    }

    fun callQuizApi(): Observable<QuizModelList.QuizModel> {
        return quizApiService.getQuiz()
    }
}