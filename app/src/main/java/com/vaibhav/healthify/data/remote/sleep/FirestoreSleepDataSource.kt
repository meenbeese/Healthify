package com.vaibhav.healthify.data.remote.sleep

import com.vaibhav.healthify.data.models.remote.SleepDTO
import com.vaibhav.healthify.util.*
import javax.inject.Inject

class FirestoreSleepDataSource @Inject constructor(
    private val internetChecker: InternetChecker
) {
    suspend fun getAllSleepLogs(email: String): Resource<List<SleepDTO>> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(emptyList())
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun addSleep(email: String, sleepDTO: SleepDTO): Resource<SleepDTO> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(sleepDTO)
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }
}
