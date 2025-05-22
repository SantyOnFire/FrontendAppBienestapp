package com.example.bienestapp.data.network

import com.example.bienestapp.data.*
import com.example.bienestapp.data.model.Emocion
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ✅ Emociones y resultados
    @POST("/api/emociones")
    suspend fun registrarEmocion(@Body emocion: Emocion)

    @POST("/api/resultados")
    suspend fun registrarResultado(@Body resultado: ResultadoTest)

    @POST("/api/recomendaciones")
    suspend fun registrarRecomendacion(@Body recomendacion: Recomendacion)

    // ✅ Usuarios
    @GET("/api/usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>

    @POST("/api/usuarios")
    suspend fun registrarUsuario(@Body usuario: UsuarioRegistro)

    @POST("/api/usuarios/login")
    suspend fun loginEstudiante(@Body request: LoginRequest): Response<Usuario>

    // ✅ Actividades sugeridas
    @GET("/api/actividades/usuario/{usuarioId}")
    suspend fun listarActividadesPorUsuario(@Path("usuarioId") usuarioId: Long): List<ActividadSugerida>

    // ✅ IA (Llama y Groq)
    @POST("/api/llama")
    fun consultarLlama(@Body request: DeepSeekRequest): Call<DeepSeekResponse>

    @POST("/api/groq")
    fun consultarGroq(@Body request: DeepSeekRequest): Call<DeepSeekResponse>
}
