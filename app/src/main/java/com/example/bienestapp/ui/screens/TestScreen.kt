package com.example.bienestapp.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bienestapp.viewmodel.ChatIAViewModel
import com.example.bienestapp.viewmodel.ResultadoTestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    navController: NavController,
    emocion: String,
    viewModel: ResultadoTestViewModel = viewModel(),
    chatIAViewModel: ChatIAViewModel
) {
    val context = LocalContext.current
    var estres by remember { mutableStateOf(5f) }
    var ansiedad by remember { mutableStateOf(5f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Diario", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF121212)
                )
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black, Color(0xFF1C1C1C))
                    )
                )
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Test basado en tu emoción: $emocion",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("¿Qué tan estresado estás hoy?", fontSize = 16.sp, color = Color.LightGray)
            Slider(
                value = estres,
                onValueChange = { estres = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF009688),
                    activeTrackColor = Color(0xFF009688)
                )
            )
            Text("Nivel de estrés: ${estres.toInt()}", color = Color.White)

            Spacer(modifier = Modifier.height(24.dp))

            Text("¿Qué nivel de ansiedad tienes hoy?", fontSize = 16.sp, color = Color.LightGray)
            Slider(
                value = ansiedad,
                onValueChange = { ansiedad = it },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF00796B),
                    activeTrackColor = Color(0xFF00796B)
                )
            )
            Text("Nivel de ansiedad: ${ansiedad.toInt()}", color = Color.White)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val prefs = context.getSharedPreferences("datos_usuario", Context.MODE_PRIVATE)
                    val usuarioId = prefs.getLong("usuarioId", -1)

                    viewModel.enviarResultado(
                        estres = estres.toInt(),
                        ansiedad = ansiedad.toInt(),
                        usuarioId = usuarioId
                    )

                    val mensajeIA =
                        "Hoy siento $emocion. Tengo un nivel de estrés de ${estres.toInt()} y ansiedad de ${ansiedad.toInt()}. ¿Qué me recomiendas?"
                    chatIAViewModel.setMensajeInicial(mensajeIA)

                    navController.navigate("chat")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688),
                    contentColor = Color.White
                )
            ) {
                Text("Enviar respuestas", fontSize = 16.sp)
            }
        }
    }
}
