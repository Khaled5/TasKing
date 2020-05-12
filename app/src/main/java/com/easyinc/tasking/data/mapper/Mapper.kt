package com.easyinc.tasking.data.mapper

interface Mapper<I,O> {
    fun mapTo(input: I): O

    fun mapFrom(input: O): I
}