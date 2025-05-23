package com.senacgrupovinteecinco.pet_connect.ui.auth

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("user_token", null)

        return if (token != null) {
            chain.proceed(request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build())
        } else {
            chain.proceed(request)
        }
    }
}