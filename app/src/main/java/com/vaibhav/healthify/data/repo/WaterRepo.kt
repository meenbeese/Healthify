package com.vaibhav.healthify.data.repo

import com.vaibhav.healthify.data.local.dataSource.RoomWaterDataSource
import com.vaibhav.healthify.data.models.local.Water
import com.vaibhav.healthify.data.models.mapper.WaterMapper
import com.vaibhav.healthify.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class WaterRepo @Inject constructor(
    private val waterDataSource: RoomWaterDataSource,
    private val authRepo: AuthRepo,
    private val waterMapper: WaterMapper
) {

    fun getTodaysWaterLogs() = waterDataSource.getAllWaterLogsAfterTime(getTodaysTime())
        .flowOn(Dispatchers.IO)

    fun getAllWaterLogsOfLastWeek() = waterDataSource.getAllWaterLogsAfterTime(getTimeOfLastWeek())
        .flowOn(Dispatchers.IO)

    suspend fun insertIntoWaterLog(water: Water): Resource<Unit> = withContext(Dispatchers.IO) {
        return@withContext authRepo.getCurrentUser()?.let {
            insertWaterIntoDb(listOf(water))
            Resource.Success<Unit>()
        } ?: Resource.Error(USER_DOES_NOT_EXIST)
    }

    fun getFOTD(): String {
        val day = getTodayDayNo()
        return waterFOTD[day]
    }

    private suspend fun insertWaterIntoDb(water: List<Water>) {
        waterDataSource.insertWater(water)
    }

    private suspend fun dumpNewWaterLogsDataIntoDb(water: List<Water>) {
        deleteAllWaterLogs()
        insertWaterIntoDb(water)
    }

    private suspend fun deleteAllWaterLogs() = waterDataSource.deleteAllWaterLogs()
}
