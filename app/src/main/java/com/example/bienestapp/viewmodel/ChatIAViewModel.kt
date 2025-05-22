package com.example.bienestapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bienestapp.data.DeepSeekRequest
import com.example.bienestapp.data.DeepSeekResponse
import com.example.bienestapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatIAViewModel : ViewModel() {

    enum class ModeloIA {
        LLAMA,
        GROQ
    }

    private val _respuestaIA = MutableStateFlow("")
    val respuestaIA: StateFlow<String> = _respuestaIA

    private val _historial = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val historial: StateFlow<List<Pair<String, String>>> = _historial

    private val _mensajeInicial = MutableStateFlow<String?>(null)
    val mensajeInicial: StateFlow<String?> = _mensajeInicial

    private val _isCargando = MutableStateFlow(false)
    val isCargando: StateFlow<Boolean> = _isCargando

    fun setMensajeInicial(mensaje: String) {
        _mensajeInicial.value = mensaje
    }

    fun enviarMensaje(mensaje: String, modelo: ModeloIA = ModeloIA.LLAMA) {
        _isCargando.value = true
        val request = DeepSeekRequest(mensaje)

        val llamada: Call<DeepSeekResponse> = when (modelo) {
            ModeloIA.LLAMA -> RetrofitClient.api.consultarLlama(request)
            ModeloIA.GROQ -> RetrofitClient.api.consultarGroq(request)
        }

        llamada.enqueue(object : Callback<DeepSeekResponse> {
            override fun onResponse(call: Call<DeepSeekResponse>, response: Response<DeepSeekResponse>) {
                _isCargando.value = false
                if (response.isSuccessful) {
                    val respuesta = response.body()?.response ?: "Sin respuesta"
                    _respuestaIA.value = respuesta
                    _historial.value = _historial.value + (mensaje to respuesta)
                } else {
                    _respuestaIA.value = "❌ Error ${response.code()}"
                }
            }

            override fun onFailure(call: Call<DeepSeekResponse>, t: Throwable) {
                _isCargando.value = false
                _respuestaIA.value = "❌ Error: ${t.message}"
            }
        })
    }
}
