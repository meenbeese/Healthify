package com.vaibhav.healthify.data.remote.water

import com.vaibhav.healthify.data.models.remote.WaterDTO
import com.vaibhav.healthify.util.*
import javax.inject.Inject

class FirestoreWaterDataSource @Inject constructor(
    private val internetChecker: InternetChecker
) {
    suspend fun getAllWaterLogs(email: String): Resource<List<WaterDTO>> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(emptyList())
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun addWater(email: String, waterDTO: WaterDTO): Resource<WaterDTO> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(waterDTO)
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }
}
