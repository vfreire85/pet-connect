package com.senacgrupovinteecinco.pet_connect.model



data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val user: UserResponse // Renomeie para UserResponse
)