package com.vinibarros.mvvmcomposetemplate.graph.module

import android.content.Context
import androidx.room.Room
import com.vinibarros.data.local.TemplateDAO
import com.vinibarros.data.local.TemplateRoomDatabase
import com.vinibarros.data.repository.DefaultModelsRepository
import com.vinibarros.mvvmcomposetemplate.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationProviderModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideTemplateDao(templateRoomDatabase: TemplateRoomDatabase): TemplateDAO {
        return templateRoomDatabase.templateDAO()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideTemplateDatabase(context: Context): TemplateRoomDatabase {
        return Room.databaseBuilder(context, TemplateRoomDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideModelsRepository(templateDAO: TemplateDAO): DefaultModelsRepository {
        return DefaultModelsRepository(templateDAO)
    }
}