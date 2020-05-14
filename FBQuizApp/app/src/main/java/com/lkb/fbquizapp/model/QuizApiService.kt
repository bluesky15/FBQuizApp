package com.lkb.fbquizapp.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface QuizApiService {
    @GET("app_test_assignment_quiz.json")
    fun getQuiz(
    ): Observable<QuizModelList.QuizModel>


    companion object {
        fun create(): QuizApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://storage.googleapis.com/sodimac-8590a.appspot.com/App%20Test%20Assignment/")
                .build()

            return retrofit.create(QuizApiService::class.java)
        }
    }
}