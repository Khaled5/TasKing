package com.easyinc.tasking.data.mapper

import com.easyinc.tasking.cache.entity.Day
import com.easyinc.tasking.data.model.DayModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapDay @Inject constructor() : Mapper<Day,DayModel> {
    override fun mapTo(input: Day): DayModel {
        return DayModel(input.date,"0%",input.id)
    }

    override fun mapFrom(input: DayModel): Day {
        return Day(input.date,input.id)
    }
}