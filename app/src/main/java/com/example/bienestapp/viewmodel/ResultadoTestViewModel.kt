package com.example.bienestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.ResultadoTest
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultadoTestViewModel : ViewModel() {

    private val _finalizado = MutableStateFlow(false)
    val finalizado: StateFlow<Boolean> = _finalizado

    fun enviarResultado(estres: Int, ansiedad: Int, usuarioId: Long = 1L) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val resultado = ResultadoTest(
            usuarioId = usuarioId,
            fecha = fechaActual,
            nivelEstres = estres,
            nivelAnsiedad = ansiedad
        )

        viewModelScope.launch {
            try {
                RetrofitInstance.api.registrarResultado(resultado)
                println("✅ Resultado del test guardado correctamente")
                _finalizado.value = true
            } catch (e: Exception) {
                println("❌ Error al guardar el resultado: ${e.message}")
            }
        }
    }
}
