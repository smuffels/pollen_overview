package com.example.pollen_overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pollen_overview.api.Granularity
import com.example.pollen_overview.api.PollenApi
import com.example.pollen_overview.api.Station
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

import pollen_overview.composeapp.generated.resources.Res
import pollen_overview.composeapp.generated.resources.compose_multiplatform


@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    var csvResult by remember { mutableStateOf("Loading...") }
    LaunchedEffect(Unit) {
        try {
            val client = HttpClient { expectSuccess = true
            install(HttpTimeout)
            {requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000}
            }
            val api = PollenApi(client)
            val csv = api.getData(Station.LAUSANNE_VD, Granularity.HOURLY)
            csvResult = csv.take(300)
        } catch (e: Exception) {
            csvResult = "ERROR: ${e::class.simpleName}: ${e.message}"
        }
    }
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = "grrr hasel"
                Text(csvResult)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                }
            }
        }
    }
}