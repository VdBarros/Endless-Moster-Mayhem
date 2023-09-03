package com.vinibarros.endlessmonstermayhem.graph.component

import android.content.Context
import com.vinibarros.endlessmonstermayhem.BaseApplication
import com.vinibarros.endlessmonstermayhem.graph.module.ActivityBindingModule
import com.vinibarros.endlessmonstermayhem.graph.module.ApplicationProviderModule
import com.vinibarros.endlessmonstermayhem.graph.module.ApplicationBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationBindingModule::class,
        ApplicationProviderModule::class,
        ActivityBindingModule::class
    ]
)

interface AppComponent {

    fun inject(baseApplication: BaseApplication): BaseApplication

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}