package com.learningandroid.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.learningandroid.amphibians.AmphibiansApplication
import com.learningandroid.amphibians.data.AmphibiansRepository
import com.learningandroid.amphibians.network.AmphibianData
import kotlinx.coroutines.launch
import okio.IOException

sealed interface AmphibianUiState {
  data class Success(val amphibians: List<AmphibianData>) : AmphibianUiState
  data object Error : AmphibianUiState
  data object Loading : AmphibianUiState
}

class AmphibianViewModel(private val amphibiansRepository: AmphibiansRepository) :
  ViewModel() {
  var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)

  init {
    getAmphibiansData()
  }

  fun getAmphibiansData() {
    viewModelScope.launch {
      amphibianUiState = try {
        AmphibianUiState.Success(
          amphibiansRepository.getAmphibiansData()
        )
      } catch (e: IOException) {
        AmphibianUiState.Error
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as AmphibiansApplication)
        val amphibiansRepository = application.container.amphibiansRepository
        AmphibianViewModel(amphibiansRepository = amphibiansRepository)
      }
    }
  }
}