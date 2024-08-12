package com.vaibhav.healthify.ui.auth.gettingStarted

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vaibhav.healthify.R
import com.vaibhav.healthify.databinding.FragmentGettingStartedBinding
import com.vaibhav.healthify.ui.homeScreen.MainActivity
import com.vaibhav.healthify.ui.userDetailsInput.UserDetailsActivity
import com.vaibhav.healthify.util.showToast
import com.vaibhav.healthify.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GettingStartedFragment : Fragment(R.layout.fragment_getting_started) {

    private val binding by viewBinding(FragmentGettingStartedBinding::bind)
    private val viewModel by viewModels<GettingStartedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            viewModel.startLoading()
        }
        collectUiState()
        collectUiEvents()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                binding.btnLogin.isEnabled = it.isButtonEnabled
                binding.loadingLayout.loadingLayout.isVisible = it.isLoading
            }
        }
    }

    private fun collectUiEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when (it) {
                    is GettingStartedScreenEvents.ShowToast -> requireContext().showToast(it.message)
                    GettingStartedScreenEvents.Logout -> viewModel.logoutComplete()
                    GettingStartedScreenEvents.NavigateToUserDetailsScreen -> navigateToUserDetailsScreen()
                    GettingStartedScreenEvents.NavigateToHomeScreen -> navigateToHomeScreen()
                }
            }
        }
    }

    private fun navigateToHomeScreen() {
        Intent(requireContext(), MainActivity::class.java).also { intent ->
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun navigateToUserDetailsScreen() {
        Intent(requireContext(), UserDetailsActivity::class.java).also { intent ->
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
