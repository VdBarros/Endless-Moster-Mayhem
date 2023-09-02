package com.vinibarros.mvvmcomposetemplate.util.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    val disposables = CompositeDisposable()


    @CallSuper
    override fun onCleared() {
        disposables.clear()
    }
}