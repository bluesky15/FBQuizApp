package com.lkb.fbquizapp.view.quiz

import androidx.lifecycle.ViewModel
import com.lkb.fbquizapp.model.Repository
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Completable
import io.reactivex.Observable
import org.koin.java.KoinJavaComponent.inject

class QuizViewModel : ViewModel() {
    private val repository: Repository by inject(Repository::class.java)
    fun callQuizApi(): Observable<QuizModelList.QuizModel> {
        return repository.getQuizService().getQuiz()
    }

    fun saveUserData(currentUser: User): Completable? {
        return repository.getDataBase().userDao()?.insertUser(currentUser)
    }
}