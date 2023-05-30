package com.example.pop_flake.ui.settings

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pop_flake.R
import com.example.pop_flake.data.pojo.ComplaintModel
import com.example.pop_flake.databinding.BottomSheetComplaintsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplaintsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetComplaintsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
        binding = BottomSheetComplaintsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSubmit.setOnClickListener {
                validate()
            }
        }
    }

    private fun validate() {
        binding.apply {
            when {
                etTitle.toString().trim().isEmpty() -> {
                    showError("fill title")
                }
                etDesc.toString().trim().isEmpty() -> {
                    showError("fill description")
                }
                else -> {
                    viewModel.addComplaintItem(
                        ComplaintModel(
                            title = etTitle.text.toString().trim(),
                            description = etDesc.text.toString().trim()
                        )
                    )
                    dismiss()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            window?.setBackgroundDrawableResource(R.drawable.transparent)
            behavior.isFitToContents = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    private fun showError(message : String){
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }
}