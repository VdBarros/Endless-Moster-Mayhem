package com.vinibarros.data.repository

import com.vinibarros.data.local.TemplateDAO
import com.vinibarros.domain.boundary.ModelsRepository
import com.vinibarros.domain.model.Model
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultModelsRepository @Inject constructor(private val templateDAO: TemplateDAO) : ModelsRepository {

    override suspend fun getModels() : List<Model>? = templateDAO.getModels()

    override suspend fun insertModel(model: Model): Model? {
        val newModelId = templateDAO.insertModel(model)
        return getModelById(newModelId)
    }

    override suspend fun deleteModel(model: Model) = templateDAO.deleteModel(model)

    override suspend fun getModelById(id: Long): Model? = templateDAO.getModelById(id)
}
