package com.example.bienestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bienestapp.navigation.AppNavHost
import com.example.bienestapp.theme.BienestAppTheme
import com.example.bienestapp.viewmodel.ChatIAViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BienestAppTheme {
                val chatIAViewModel: ChatIAViewModel = viewModel()
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(chatIAViewModel = chatIAViewModel)
                }
            }
        }
    }
}
