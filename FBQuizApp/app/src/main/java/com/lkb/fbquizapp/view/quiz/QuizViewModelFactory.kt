package com.lkb.fbquizapp.view.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkb.fbquizapp.model.persistance.AppDatabase

class QuizViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}