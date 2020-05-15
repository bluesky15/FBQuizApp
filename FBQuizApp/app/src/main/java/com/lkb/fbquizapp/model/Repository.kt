package com.lkb.fbquizapp.model

import android.content.SharedPreferences
import com.lkb.fbquizapp.model.persistance.AppDatabase

class Repository(private val db: AppDatabase, private val sharedPref: SharedPreferences) {
    fun getQuizService(): QuizApiService {
        return QuizApiService.create()
    }

    fun getDataBase(): AppDatabase {
        return db
    }

    fun getSharedPreference(): SharedPreferences {
        return sharedPref
    }
}