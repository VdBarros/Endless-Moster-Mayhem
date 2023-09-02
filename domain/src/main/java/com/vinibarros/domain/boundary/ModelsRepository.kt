package com.vinibarros.domain.boundary

import com.vinibarros.domain.model.Model

interface ModelsRepository {
    suspend fun getModels(): List<Model>?
    suspend fun insertModel(model: Model): Model?
    suspend fun deleteModel(model: Model)
    suspend fun getModelById(id: Long): Model?
}