package com.lkb.fbquizapp.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkb.fbquizapp.model.persistance.AppDatabase

class ResultViewModelFactory(private val db:AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}