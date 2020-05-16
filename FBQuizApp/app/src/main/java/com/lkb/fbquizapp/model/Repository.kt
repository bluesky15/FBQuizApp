package com.lkb.fbquizapp.model

import android.content.SharedPreferences
import com.lkb.fbquizapp.QUIZ_LIST
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import com.lkb.fbquizapp.model.persistance.UserDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

class Repository(private val userDao: UserDao, private val sharedPref: SharedPreferences) {

    fun insertUser(user: User): Completable? {
        return userDao.insertUser(user)
    }

    fun getQuiz(): Observable<QuizModelList.QuizModel> {
        return QuizApiService.create().getQuiz()
    }

    fun getTopFiveUser(): Maybe<List<User>> {
        return userDao.getTopFileUser()
    }

    fun getLocalData(): String? {
        return sharedPref.getString(QUIZ_LIST, "")
    }

    fun saveQuizData(data: String) {
        sharedPref.edit()
            .putString(QUIZ_LIST, data)
    }
}