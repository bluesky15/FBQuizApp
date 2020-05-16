package com.lkb.fbquizapp.view.quiz

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lkb.fbquizapp.model.Repository
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject

class QuizViewModel : ViewModel() {
    private val repository: Repository by inject(Repository::class.java)
    fun callQuizApi(): Observable<QuizModelList.QuizModel> {
        var quizData: Observable<QuizModelList.QuizModel>? = null
        var localData = repository.getLocalData()
        localData?.let { it ->
            quizData = if (it.isEmpty()) {
                val data = repository.getQuiz()
                data.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { e -> repository.saveQuizData(Gson().toJson(e)) }
                data
            } else {
                Observable.just(Gson().fromJson(it, QuizModelList.QuizModel::class.java))
            }
        }
        return quizData!!
    }

    fun saveUserData(currentUser: User): Completable? {
        return repository.insertUser(currentUser)
    }
}