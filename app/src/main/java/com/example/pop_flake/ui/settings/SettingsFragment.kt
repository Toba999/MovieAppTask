package com.example.pop_flake.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.SettingsState
import com.example.pop_flake.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var  viewModel: SettingsViewModel
    private lateinit var adapter : ComplaintsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]

        adapter = ComplaintsAdapter()
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDarkModeOption()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }
        initView()

    }

    private fun initView() {
        binding.apply {

            rvComplaints.adapter = adapter

            btnSendComplaint.setOnClickListener {
                ComplaintsBottomSheet().show(childFragmentManager, "tag")
            }

            rgDarkMode.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbFollow -> {
                        viewModel.setDarkModeOption(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    R.id.rbDark -> {
                        viewModel.setDarkModeOption(AppCompatDelegate.MODE_NIGHT_YES)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                    R.id.rbLight -> {
                        viewModel.setDarkModeOption(AppCompatDelegate.MODE_NIGHT_NO)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }

    private fun handleState(state: SettingsState) {
        handleDarkModeState(state.darkModeOption)
        adapter.submitList(state.list)
    }

    fun handleDarkModeState(darkModeOption: Int) {
        binding.apply {
            when (darkModeOption) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                    rbFollow.isChecked = true
                }
                AppCompatDelegate.MODE_NIGHT_NO -> {
                    rbLight.isChecked = true
                }
                AppCompatDelegate.MODE_NIGHT_YES -> {
                    rbDark.isChecked = true
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}