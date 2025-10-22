package com.example.splashmaniaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.splashmaniaapp.ui.screen.SplashManiaApp
import com.example.splashmaniaapp.ui.theme.SplashManiaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashManiaAppTheme {
                SplashManiaApp()
            }
        }
    }
}