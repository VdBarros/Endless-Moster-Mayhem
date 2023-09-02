package com.vinibarros.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinibarros.domain.model.Model

@Database(entities = [Model::class], version = 1, exportSchema = false)
abstract class TemplateRoomDatabase : RoomDatabase() {

    abstract fun templateDAO(): TemplateDAO
}