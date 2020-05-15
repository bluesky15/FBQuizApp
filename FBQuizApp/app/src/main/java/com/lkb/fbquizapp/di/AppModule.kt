package com.lkb.fbquizapp.di

import android.content.Context
import androidx.room.Room
import com.lkb.fbquizapp.DATABASE_NAME
import com.lkb.fbquizapp.SHARED_PREF_KEY
import com.lkb.fbquizapp.model.Repository
import com.lkb.fbquizapp.model.persistance.AppDatabase
import com.lkb.fbquizapp.view.quiz.QuizViewModel
import com.lkb.fbquizapp.view.result.ResultViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    single { androidContext().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE) }
    single { Repository(get(), get()) }
    viewModel { QuizViewModel() }
    viewModel { ResultViewModel() }
}
