package com.tamastudy.tama.mapper

interface EntityMapper<D, E> {
    fun toEntity(dto: D): E
    fun toEntities(dtos: List<D>) : List<E>

    fun toDto(entity: E): D
    fun toDtos(entities: List<E>) : List<D>
}