package com.vinibarros.mvvmcomposetemplate.util.provider

import com.vinibarros.domain.util.provider.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}