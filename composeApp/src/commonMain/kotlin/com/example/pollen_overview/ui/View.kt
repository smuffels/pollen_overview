package com.example.pollen_overview.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pollen_overview.model.Region

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun View(uiState: PollenUiState, onRegionSelected: (Region) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRegion by remember { mutableStateOf(Region.ZH) }

    Column(
        modifier = Modifier.fillMaxSize().safeContentPadding()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedRegion.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Kanton") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Region.entries.forEach { region ->
                    DropdownMenuItem(
                        text = { Text(region.name) },
                        onClick = {
                            selectedRegion = region
                            expanded = false
                            onRegionSelected(region)
                        }
                    )
                }
            }
        }

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