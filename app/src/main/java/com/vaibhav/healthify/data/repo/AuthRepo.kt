package com.vaibhav.healthify.data.repo

import com.vaibhav.healthify.data.models.local.User
import com.vaibhav.healthify.data.models.mapper.UserMapper
import com.vaibhav.healthify.util.Resource
import com.vaibhav.healthify.util.getSleepQuantity
import com.vaibhav.healthify.util.getWaterQuantity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val preferencesRepo: PreferencesRepo,
    private val userMapper: UserMapper
) {

    suspend fun getCurrentUser() = preferencesRepo.getUserData()

    fun getUserDataFlow() = preferencesRepo.getUserDataFlow()

    suspend fun isUserOnBoardingComplete() = preferencesRepo.getOnBoardingCompleted() != null

    suspend fun isUserDataEntryCompleted() = preferencesRepo.getUserCompletedDataEntry() != null

    suspend fun saveUserOnBoardingCompleted() = preferencesRepo.saveOnBoardingCompleted()

    suspend fun saveUserDataEntryCompleted() = preferencesRepo.saveUserDataCompleted()

    suspend fun logoutUser() = withContext(Dispatchers.IO) {
        removeUserFromPreferences()
        removeUserDataCompleted()
    }

    suspend fun saveUserName(username: String) = withContext(Dispatchers.IO) {
        val user = getCurrentUser()
        return@withContext user?.let {
            it.username = username
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    suspend fun saveUserAge(age: Int) = withContext(Dispatchers.IO) {
        val user = getCurrentUser()
        return@withContext user?.let {
            it.age = age
            it.sleepLimit = age.getSleepQuantity()
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    suspend fun saveUserWeight(weight: Int) = withContext(Dispatchers.IO) {
        val user = getCurrentUser()
        return@withContext user?.let {
            it.weight = weight
            it.waterLimit = weight.getWaterQuantity()
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    suspend fun updateUserWaterLimit(limit: Int) = withContext(Dispatchers.IO) {
        return@withContext getCurrentUser()?.let {
            it.waterLimit = limit
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    suspend fun updateUserSleepLimit(limit: Int) = withContext(Dispatchers.IO) {
        return@withContext getCurrentUser()?.let {
            it.sleepLimit = limit
            Timber.d("Editing sleep Limit in REPO")
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    suspend fun increaseUserExp(increment: Int) = withContext(Dispatchers.IO) {
        return@withContext getCurrentUser()?.let {
            it.exp += increment
            saveUserIntoPreferences(it)
            Resource.Success(true)
        } ?: Resource.Error("User is not logged in")
    }

    private suspend fun saveUserIntoPreferences(user: User) {
        preferencesRepo.saveUserData(user)
    }

    private suspend fun removeUserFromPreferences() {
        preferencesRepo.removeUserData()
    }

    private suspend fun removeUserDataCompleted() {
        preferencesRepo.removeUserData()
    }

    private suspend fun removeUserOnBoarding() {
        preferencesRepo.removeOnBoarding()
    }
}
