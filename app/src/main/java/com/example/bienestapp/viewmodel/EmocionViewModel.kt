package com.example.bienestapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.model.Emocion
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EmocionViewModel : ViewModel() {

    private val _estado = MutableStateFlow("")
    val estado: StateFlow<String> = _estado

    fun registrarEmocion(nombre: String, usuarioId: Long = 5L) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val emocion = Emocion(
            usuarioId = usuarioId,
            fecha = fechaActual,
            emocion = nombre,
            comentario = ""
        )

        Log.d("EmocionViewModel", "Registrando emoción: $emocion")

        viewModelScope.launch {
            try {
                RetrofitInstance.api.registrarEmocion(emocion)
                Log.d("EmocionViewModel", "✅ Emoción registrada")
                _estado.value = "registrada"
            } catch (e: Exception) {
                Log.e("EmocionViewModel", "❌ Error al registrar emoción", e)
                _estado.value = "error"
            }
        }
    }
}
