package com.lkb.fbquizapp.view.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lkb.fbquizapp.model.persistance.AppDatabase
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val context: Context) : ViewModel() {
//    fun requestQuizData(): Single<Boolean> {
//        return try {
//            Completable.fromAction {
//                val db =
//                    AppDatabase.getInstance(context)
//                db?.userDao()?.insertAll(user)
//            }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//            Single.just(true)
//        } catch (e: Exception) {
//            e.stackTrace
//            Single.just(false)
//        }
//    }

}