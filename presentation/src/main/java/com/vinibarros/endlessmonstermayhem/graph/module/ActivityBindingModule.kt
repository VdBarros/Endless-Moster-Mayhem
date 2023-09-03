package com.vinibarros.endlessmonstermayhem.graph.module

import com.vinibarros.endlessmonstermayhem.graph.scope.ActivityScope
import com.vinibarros.endlessmonstermayhem.game.view.EndlessMonsterMayhemMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [TemplateMainActivityModule::class])
    fun contributeTemplate(): EndlessMonsterMayhemMainActivity

}