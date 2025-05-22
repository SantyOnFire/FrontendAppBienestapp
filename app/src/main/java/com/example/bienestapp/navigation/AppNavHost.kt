package com.example.bienestapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bienestapp.ui.screens.*
import com.example.bienestapp.viewmodel.ChatIAViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    chatIAViewModel: ChatIAViewModel
) {
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { InicioScreen(navController) }
        composable("menu") { MenuPrincipalScreen(navController) }
        composable("emociones") { SeleccionEmocionScreen(navController) }
        composable("test/{emocion}") { backStackEntry ->
            val emocion = backStackEntry.arguments?.getString("emocion") ?: ""
            TestScreen(navController, emocion, chatIAViewModel = chatIAViewModel)
        }
        composable("actividades") { ActividadesScreen(navController) }
        composable("confirmacion") { ConfirmacionScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }

        // ✅ ASISTENTE IA
        composable("chat") {
            ChatScreen(viewModel = chatIAViewModel)
        }
    }
}
