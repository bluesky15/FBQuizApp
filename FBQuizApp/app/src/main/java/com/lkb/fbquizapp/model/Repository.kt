package com.lkb.fbquizapp.model

import com.lkb.fbquizapp.model.persistance.AppDatabase

class Repository(private val db: AppDatabase) {
    fun getQuizService(): QuizApiService {
        return QuizApiService.create()
    }

    fun getDataBase(): AppDatabase {
        return db
    }
}