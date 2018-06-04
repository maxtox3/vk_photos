package gusev.max.presentation.mapper


interface Mapper<VIEW_MODEL, ENTITY> {

    fun mapToViewModel(entity: ENTITY): VIEW_MODEL

    fun mapToEntity(model: VIEW_MODEL): ENTITY
}