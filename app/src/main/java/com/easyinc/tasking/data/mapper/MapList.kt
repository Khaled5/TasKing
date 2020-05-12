package com.easyinc.tasking.data.mapper

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapList<I,O> @Inject constructor(private val mapper: Mapper<I, O>): ListMapper<I,O> {

    override fun mapTo(input: List<I>): List<O> {
        return input.map { mapper.mapTo(it) }
    }

    override fun mapFrom(input: List<O>): List<I> {
        return input.map { mapper.mapFrom(it) }
    }


}