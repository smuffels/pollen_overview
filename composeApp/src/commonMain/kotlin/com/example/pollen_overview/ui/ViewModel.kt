package com.example.pollen_overview.ui

import com.example.pollen_overview.repository.Repository
import com.example.pollen_overview.model.PollenData
import com.example.pollen_overview.model.Region
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository,
    private val scope: CoroutineScope
)
{
    private val _uiState = MutableStateFlow<PollenUiState>(PollenUiState.Loading)
    val uiState: StateFlow<PollenUiState> = _uiState.asStateFlow()


    fun loadPollen(region: Region) {
        scope.launch {
            _uiState.value = PollenUiState.Loading
            try {
                val data = repository.getLatestPollen(region)
                _uiState.value = PollenUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = PollenUiState.Error(e.message ?: "Unbekannter Fehler")
            }
        }
    }

}
sealed class PollenUiState {
    object Loading : PollenUiState()
    data class Success(val data: List<PollenData>) : PollenUiState()
    data class Error(val message: String) : PollenUiState()
}