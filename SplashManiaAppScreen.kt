package com.example.splashmaniaapp.ui.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.splashmaniaapp.ui.navigation.SplashManiaAppNavHost
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splashmaniaapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashManiaAppBar(
    title: String,
    canGoBack: Boolean,
    goBack: () -> Unit = {},
    actions: @Composable() (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if(canGoBack) {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions
    )
}

@Composable
fun SplashManiaApp(navController: NavHostController = rememberNavController()) {
    SplashManiaAppNavHost(navController = navController)
}