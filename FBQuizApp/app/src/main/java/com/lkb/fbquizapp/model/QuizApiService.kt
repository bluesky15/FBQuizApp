package com.lkb.fbquizapp.model

import com.lkb.fbquizapp.BASE_URL
import com.lkb.fbquizapp.PATH_PARAM
import com.lkb.fbquizapp.model.persistance.QuizModelList
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuizApiService {
    @GET(PATH_PARAM)
    fun getQuiz(): Observable<QuizModelList.QuizModel>

    companion object {
        fun create(): QuizApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(QuizApiService::class.java)
        }
    }
}