package com.jellypump.creativefood.extensions

import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Flowable<T>.runInBackground(): Flowable<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.runInBackground(): Single<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun Completable.runInBackground(): Completable =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun <T> Single<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable())