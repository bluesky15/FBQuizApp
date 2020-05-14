package com.lkb.fbquizapp.view.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModelFactory::class.java)) {
            return ResultViewModelFactory() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}