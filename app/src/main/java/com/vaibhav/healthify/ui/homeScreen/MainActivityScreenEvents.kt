package com.vaibhav.healthify.ui.homeScreen

sealed class MainActivityScreenEvents {
    data class ShowToast(val message: String) : MainActivityScreenEvents()
}
