package com.vaibhav.healthify.data.remote.auth

import com.vaibhav.healthify.data.models.remote.UserDTO
import com.vaibhav.healthify.util.*
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val internetChecker: InternetChecker
) {
    companion object {
        private const val AGE_FIELD_NAME = "age"
        private const val WEIGHT_FIELD_NAME = "weight"
        private const val EXP_FIELD_NAME = "exp"
        private const val USERNAME_FIELD_NAME = "username"
        private const val SLEEP_LIMIT_FIELD_NAME = "sleepLimit"
        private const val WATER_LIMIT_FIELD_NAME = "waterLimit"
    }

    suspend fun getUserData(email: String): Resource<UserDTO> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(UserDTO())
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun saveUserData(user: UserDTO): Resource<UserDTO> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(user)
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun saveUserName(username: String, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun saveUserAgeAndSleepLimit(age: Int, limit: Int, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun saveUserWeightAndWaterQuantity(weight: Int, quantity: Int, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun increaseUserExp(inc: Int, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun fetchAllUsers(): Resource<List<UserDTO>> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success(emptyList())
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun updateUserSleepLimit(limit: Int, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }

    suspend fun updateUserWaterLimit(limit: Int, email: String): Resource<Unit> {
        return if (internetChecker.hasInternetConnection()) {
            Resource.Success()
        } else {
            Resource.Error(
                errorType = ERROR_TYPE.NO_INTERNET,
                message = NO_INTERNET_MESSAGE
            )
        }
    }
}
