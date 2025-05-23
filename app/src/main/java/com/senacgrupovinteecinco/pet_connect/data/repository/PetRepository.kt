package com.senacgrupovinteecinco.pet_connect.data.repository

import com.senacgrupovinteecinco.pet_connect.data.api.RetrofitClient
import com.senacgrupovinteecinco.pet_connect.model.LoginRequest
import com.senacgrupovinteecinco.pet_connect.model.LoginResponse
import com.senacgrupovinteecinco.pet_connect.model.Pet

class PetRepository {
    private val apiService = RetrofitClient.instance

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPets(): Result<List<Pet>> {
        return try {
            val response = apiService.getPets()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch pets"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}