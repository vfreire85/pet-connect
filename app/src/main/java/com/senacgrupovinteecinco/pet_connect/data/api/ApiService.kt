package com.senacgrupovinteecinco.pet_connect.data.api


import com.senacgrupovinteecinco.pet_connect.model.LoginRequest
import com.senacgrupovinteecinco.pet_connect.model.LoginResponse
import com.senacgrupovinteecinco.pet_connect.model.Pet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET


interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("pets")
    suspend fun getPets(): Response<List<Pet>>

    @POST("pets")
    suspend fun <ApiResponse : Any?> registerPet(@Body pet: Pet): Response<ApiResponse>
}

annotation class POST(val value: String)

