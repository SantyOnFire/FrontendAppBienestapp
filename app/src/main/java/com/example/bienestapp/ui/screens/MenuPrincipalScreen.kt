package com.example.bienestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuPrincipalScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Black, Color(0xFF1C1C1C))
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Bienvenido a BienestApp",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Text(
                text = "Selecciona una acción para continuar",
                fontSize = 16.sp,
                color = Color.LightGray
            )

            // ✅ Botón: Registrar Emoción
            Button(
                onClick = { navController.navigate("emociones") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009688),
                    contentColor = Color.White
                )
            ) {
                Text("Registrar Emoción")
            }


            Button(
                onClick = { navController.navigate("test/Neutral") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF26A69A),
                    contentColor = Color.White
                )
            ) {
                Text("Realizar Test")
            }


            Button(
                onClick = { navController.navigate("actividades") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF80CBC4),
                    contentColor = Color.White
                )
            ) {
                Text("Ver Actividades")
            }


            OutlinedButton(
                onClick = { navController.navigate("chat") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF80CBC4)
                )
            ) {
                Text("Asistente IA 🤖")
            }
        }
    }
}
