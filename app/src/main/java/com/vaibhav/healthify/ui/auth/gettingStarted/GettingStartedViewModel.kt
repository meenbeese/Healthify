package com.vaibhav.healthify.ui.auth.gettingStarted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.healthify.data.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GettingStartedScreenState(
    val isLoading: Boolean = false,
    val isButtonEnabled: Boolean = true
)

sealed class GettingStartedScreenEvents {
    data class ShowToast(val message: String) : GettingStartedScreenEvents()
    object NavigateToUserDetailsScreen : GettingStartedScreenEvents()
    object NavigateToHomeScreen : GettingStartedScreenEvents()
    object Logout : GettingStartedScreenEvents()
}

@HiltViewModel
class GettingStartedViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    companion object {
        private const val FAILED_TO_LOGIN = "Failed to log you in. Please try again"
        private const val LOGIN_SUCCESS = "User logged in successfully"
    }

    private val _uiState = MutableStateFlow(GettingStartedScreenState())
    val uiState: StateFlow<GettingStartedScreenState> = _uiState

    private val _events = MutableSharedFlow<GettingStartedScreenEvents>()
    val events: SharedFlow<GettingStartedScreenEvents> = _events

    fun startLoading() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isLoading = true, isButtonEnabled = false))
    }

    private fun stopLoading() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isLoading = false, isButtonEnabled = true))
    }

    fun sendError(message: String) = viewModelScope.launch {
        stopLoading()
        _events.emit(GettingStartedScreenEvents.ShowToast(message))
    }

    fun logoutFailed() = viewModelScope.launch {
        authRepo.logoutUser()
        _events.emit(GettingStartedScreenEvents.ShowToast(FAILED_TO_LOGIN))
    }

    fun logoutComplete() = viewModelScope.launch {
        authRepo.logoutUser()
        _events.emit(GettingStartedScreenEvents.ShowToast(FAILED_TO_LOGIN))
    }
}
