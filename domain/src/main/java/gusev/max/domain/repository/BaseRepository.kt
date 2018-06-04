package gusev.max.domain.repository

import gusev.max.domain.model.BaseDomainModel
import io.reactivex.Completable
import io.reactivex.Flowable


interface BaseRepository<MODEL : BaseDomainModel> {

    fun clearModels(): Completable

    fun saveModels(models: List<MODEL>): Completable

    fun getModels(): Flowable<List<MODEL>>

    fun getModelById(id: Long): Flowable<MODEL>
}