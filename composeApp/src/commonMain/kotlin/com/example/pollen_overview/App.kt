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
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import com.example.pollen_overview.model.Region
import com.example.pollen_overview.repository.Repository
import com.example.pollen_overview.ui.View
import com.example.pollen_overview.ui.ViewModel
import io.ktor.client.plugins.HttpTimeout

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        val client = HttpClient { install(HttpTimeout) }
        val api = PollenApi(client)
        val repository = Repository(api, useFake = true)
        //val repository = Repository(api, useFake = false)
        ViewModel(repository, scope)
    }

    LaunchedEffect(Unit) {
        viewModel.loadPollen(Region.ZH)
    }

    val uiState by viewModel.uiState.collectAsState()

    View(
        uiState = uiState,
        onRegionSelected = { region -> viewModel.loadPollen(region) }
    )
}