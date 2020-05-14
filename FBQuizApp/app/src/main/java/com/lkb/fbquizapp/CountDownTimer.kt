package com.lkb.fbquizapp

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class CountDownTimer(
    private val startValue: Long,
    private val timeUnit: TimeUnit
) {
    private var disposable: Disposable? = null
     abstract fun onTick(tickValue: Long)
     abstract fun onFinish()
    fun start() : Disposable? {
        Observable.zip(
            Observable.range(0, startValue.toInt()),
            Observable.interval(1, timeUnit),
            BiFunction { integer: Int, aLong: Long? ->
                val l = startValue - integer
                l
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(aLong: Long) {
                    onTick(aLong)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    onFinish()
                }
            })
        return disposable
    }

    fun cancel() {
        if (disposable != null) disposable!!.dispose()
    }

}