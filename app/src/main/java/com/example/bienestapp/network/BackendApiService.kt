package com.example.bienestapp.network

import com.example.bienestapp.data.DeepSeekRequest
import com.example.bienestapp.data.DeepSeekResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApiService {
    @POST("api/deepseek") // Endpoint de tu backend
    fun consultarDeepSeek(@Body request: DeepSeekRequest): Call<DeepSeekResponse>
}