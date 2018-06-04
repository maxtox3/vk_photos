package gusev.max.data.mapper

import gusev.max.data.entity.BaseEntity
import gusev.max.domain.model.BaseDomainModel


interface Mapper<E : BaseEntity, D : BaseDomainModel> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}