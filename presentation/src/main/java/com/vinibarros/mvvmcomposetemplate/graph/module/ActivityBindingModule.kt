package com.vinibarros.mvvmcomposetemplate.graph.module

import com.vinibarros.mvvmcomposetemplate.graph.scope.ActivityScope
import com.vinibarros.mvvmcomposetemplate.TemplateMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [TemplateMainActivityModule::class])
    fun contributeTemplate(): TemplateMainActivity

}