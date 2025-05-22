package com.example.bienestapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bienestapp.data.LoginRequest
import com.example.bienestapp.data.Usuario
import com.example.bienestapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow("")
    val estado: StateFlow<String> = _estado

    fun login(email: String, password: String, onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        val request = LoginRequest(email, password)
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.loginEstudiante(request)
                if (response.isSuccessful && response.body() != null) {
                    val usuario = response.body()!!
                    _estado.value = "exito"
                    // ✅ Guardar ID en SharedPreferences
                    val prefs = getApplication<Application>().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
                    prefs.edit().putLong("usuarioId", usuario.id).apply()
                    onSuccess(usuario.id)
                } else {
                    _estado.value = "error"
                    onError("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _estado.value = "error"
                onError("Error: ${e.message}")
            }
        }
    }
}
