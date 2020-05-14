package com.lkb.fbquizapp.view.result

import androidx.lifecycle.ViewModel
import com.lkb.fbquizapp.model.persistance.AppDatabase
import com.lkb.fbquizapp.model.persistance.User
import io.reactivex.Maybe

class ResultViewModel(private val db: AppDatabase) : ViewModel() {

    fun getTopResults(): Maybe<List<User>>? {
        return db?.userDao()?.getTopFileUser()
    }

}