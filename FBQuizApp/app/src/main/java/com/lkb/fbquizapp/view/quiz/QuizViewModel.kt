package com.lkb.fbquizapp.view.quiz

import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lkb.fbquizapp.R
import com.lkb.fbquizapp.model.Repository
import com.lkb.fbquizapp.model.persistance.QuizModelList
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.inject
import kotlin.random.Random

class QuizViewModel : ViewModel() {
    var currentUser: User? = null
    val data: MutableLiveData<QuizModelList.Quiz> by lazy {
        MutableLiveData<QuizModelList.Quiz>()
    }
    private val success: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    lateinit var quizData: QuizModelList.QuizModel
    private val repository: Repository by inject(Repository::class.java)
    fun callQuizApi(): LiveData<Boolean> {
        var localData = repository.getLocalData()
        localData?.let { it ->
            if (it.isEmpty()) {
                repository.getQuiz().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ e ->
                        repository.saveQuizData(Gson().toJson(e))
                        quizData = e
                        success.value = true
                    }) { success.value = false }
            } else {
                quizData = Gson().fromJson(it, QuizModelList.QuizModel::class.java)
                success.value = true
            }
        }
        return success
    }

    fun saveUserData(): Completable? {
        return currentUser?.let { repository.insertUser(it) }
    }

    fun mapOptionToString(@IdRes id: Int): String {
        return when (id) {
            R.id.radio_one -> "A"
            R.id.radio_two -> "B"
            R.id.radio_three -> "C"
            R.id.radio_four -> "D"
            else -> ""
        }
    }

    fun updateScore(score: Int) {
        currentUser?.let { it.score = score }
    }

    fun nextQuestion() {
        val length = quizData.size
        val index = Random.nextInt(0, length)
        data.postValue(quizData[index])
    }
}