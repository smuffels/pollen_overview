package com.example.pollen_overview.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun View(uiState: PollenUiState) {
    Column(
        modifier = Modifier.fillMaxSize().safeContentPadding()
    ) {
        when (val state = uiState) {
            is PollenUiState.Loading -> CircularProgressIndicator()
            is PollenUiState.Error   -> Text("Fehler: ${state.message}")
            is PollenUiState.Success -> PollenList(state)
        }
    }
}

@Composable
private fun PollenList(state: PollenUiState.Success) {
    state.data.forEach { pollen ->
        Text("${pollen.plantName}: ${pollen.level.displayName}")
    }
}