package com.vaibhav.healthify.data.remote.auth

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.vaibhav.healthify.data.models.remote.UserDTO
import com.vaibhav.healthify.util.Resource
import com.vaibhav.healthify.util.USER_COLLECTION
import com.vaibhav.healthify.util.USER_DOES_NOT_EXIST
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore
) : AuthDataSource {

    companion object {
        private const val AGE_FIELD_NAME = "age"
        private const val WEIGHT_FIELD_NAME = "weight"
        private const val EXP_FIELD_NAME = "exp"
        private const val USERNAME_FIELD_NAME = "username"
        private const val SLEEP_LIMIT_FIELD_NAME = "sleepLimit"
        private const val WATER_LIMIT_FIELD_NAME = "waterLimit"
    }

    override suspend fun getUserData(email: String): Resource<UserDTO> =
        try {
            val user = fireStore.collection(USER_COLLECTION).document(email).get().await()
                .toObject(UserDTO::class.java)
            user?.let {
                Resource.Success(user)
            } ?: Resource.Error(message = USER_DOES_NOT_EXIST)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    override suspend fun saveUserData(user: UserDTO): Resource<UserDTO> =
        try {
            fireStore.collection(USER_COLLECTION).document(user.email)
                .set(user).await()
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    override suspend fun saveUserName(username: String, email: String): Resource<Unit> =
        try {
            fireStore.collection(USER_COLLECTION).document(email)
                .update(USERNAME_FIELD_NAME, username)
                .await()
            Resource.Success()
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    override suspend fun saveUserAgeAndSleepLimit(
        age: Int,
        limit: Int,
        email: String
    ): Resource<Unit> =
        try {
            fireStore.collection(USER_COLLECTION).document(email)
                .update(AGE_FIELD_NAME, age, SLEEP_LIMIT_FIELD_NAME, limit).await()
            Resource.Success()
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    override suspend fun saveUserWeightAndWaterQuantity(
        weight: Int,
        quantity: Int,
        email: String
    ): Resource<Unit> =
        try {
            fireStore.collection(USER_COLLECTION).document(email)
                .update(WEIGHT_FIELD_NAME, weight, WATER_LIMIT_FIELD_NAME, quantity).await()
            Resource.Success()
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    override suspend fun increaseUserExp(inc: Int, email: String): Resource<Unit> = try {
        fireStore.collection(USER_COLLECTION).document(email)
            .update(EXP_FIELD_NAME, FieldValue.increment(inc.toLong())).await()
        Resource.Success()
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    override suspend fun fetchAllUsers() = try {
        val users =
            fireStore.collection(USER_COLLECTION).get().await().toObjects(UserDTO::class.java)
        Resource.Success(users)
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    override suspend fun updateUserSleepLimit(limit: Int, email: String): Resource<Unit> = try {
        fireStore.collection(USER_COLLECTION).document(email).update(SLEEP_LIMIT_FIELD_NAME, limit)
            .await()
        Resource.Success()
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }

    override suspend fun updateUserWaterLimit(limit: Int, email: String): Resource<Unit> = try {
        fireStore.collection(USER_COLLECTION).document(email).update(WATER_LIMIT_FIELD_NAME, limit)
            .await()
        Resource.Success()
    } catch (e: Exception) {
        Resource.Error(e.message.toString())
    }
}
