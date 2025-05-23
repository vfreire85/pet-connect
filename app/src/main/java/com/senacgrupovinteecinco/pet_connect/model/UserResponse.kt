package com.senacgrupovinteecinco.pet_connect.model

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)
