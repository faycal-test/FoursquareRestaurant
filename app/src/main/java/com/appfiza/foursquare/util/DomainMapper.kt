package com.appfiza.foursquare.util

interface DomainMapper<E, D> {
    /**
     *  Map a DTO Object to a Domain Object
     *  @param [entity] represents the DTO object
     */
    fun mapToDomain(entity: E): D
}