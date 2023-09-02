package com.vinibarros.domain.interactors

import com.vinibarros.domain.boundary.ModelsRepository
import com.vinibarros.domain.model.Model
import io.reactivex.Single
import javax.inject.Inject

class GetModel @Inject constructor(private val modelsRepository: ModelsRepository) {
    suspend fun execute(): Single<List<Model>> = try {
        Single.just(modelsRepository.getModels().orEmpty())
    } catch (t: Throwable) {
        Single.error(t)
    }
}
