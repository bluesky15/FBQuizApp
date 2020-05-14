package com.lkb.fbquizapp.view.quiz

import androidx.lifecycle.ViewModel
import com.lkb.fbquizapp.model.QuizApiService
import com.lkb.fbquizapp.model.persistance.AppDatabase
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuizViewModel(private val db: AppDatabase) : ViewModel() {
    private val quizApiService by lazy {
        QuizApiService.create()
    }

    fun callQuizApi(): Observable<QuizModelList.QuizModel> {
        return quizApiService.getQuiz()
    }

    fun saveUserData(currentUser: User): Completable? {
        return db?.userDao()?.insertUser(currentUser)
    }
}