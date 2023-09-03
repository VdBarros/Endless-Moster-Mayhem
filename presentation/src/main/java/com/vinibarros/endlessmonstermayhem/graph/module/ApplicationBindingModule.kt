package com.vinibarros.endlessmonstermayhem.graph.module

import com.vinibarros.endlessmonstermayhem.util.provider.DefaultSchedulerProvider
import com.vinibarros.data.repository.DefaultModelsRepository
import com.vinibarros.domain.boundary.ModelsRepository
import com.vinibarros.domain.util.provider.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationBindingModule {

    @Binds
    abstract fun bindModelsRepository(
        impl: DefaultModelsRepository

    ): ModelsRepository

    @Binds
    abstract fun bindSchedulerProvider(
        impl: DefaultSchedulerProvider
    ): SchedulerProvider

}