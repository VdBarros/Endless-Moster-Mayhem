package com.vinibarros.data.local

import androidx.room.*
import com.vinibarros.domain.model.Model

@Dao
interface TemplateDAO {

    @Query("SELECT * FROM model ORDER BY id ASC")
    fun getModels(): List<Model>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(model: Model): Long

    @Delete
    fun deleteModel(model: Model)

    @Query("SELECT * FROM model WHERE id = :modelId")
    fun getModelById(modelId: Long): Model?
}