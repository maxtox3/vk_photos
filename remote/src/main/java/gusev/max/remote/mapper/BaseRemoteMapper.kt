package gusev.max.remote.mapper

import gusev.max.data.entity.BaseEntity
import gusev.max.remote.model.BaseRemoteModel


interface BaseRemoteMapper<in M : BaseRemoteModel, out E : BaseEntity> {

    fun mapFromRemote(type: M): E

}