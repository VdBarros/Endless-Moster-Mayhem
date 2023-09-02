package com.vinibarros.domain.interactors

import com.vinibarros.domain.boundary.ModelsRepository
import com.vinibarros.domain.model.Model
import io.reactivex.Single
import javax.inject.Inject

class DeleteModel @Inject constructor(private val modelsRepository: ModelsRepository) {
    suspend fun execute(model: Model): Single<Model?> = try {
        modelsRepository.deleteModel(model)
        Single.just(model)
    } catch (t: Throwable) {
        Single.error(t)
    }
}
