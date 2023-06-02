package com.example.pop_flake.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pop_flake.data.local.DataStoreManager
import com.example.pop_flake.data.local.DataStoreManagerInterface
import com.example.pop_flake.data.pojo.ComplaintModel
import com.example.pop_flake.data.pojo.SettingsState
import com.example.pop_flake.utils.Constants.DARK_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStoreManager: DataStoreManagerInterface): ViewModel() {


     val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> get() = _state


    fun getDarkModeOption() {
        viewModelScope.launch {
            dataStoreManager.getInt(DARK_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM).onEach {
                _state.value = state.value.copy(darkModeOption = it)
            }.launchIn(viewModelScope)
        }
    }


    fun addComplaintItem(complaintModel: ComplaintModel){
        val currentList = _state.value.list.toMutableList()
        currentList.add(complaintModel)
        _state.value = _state.value.copy(list = currentList)
    }

    fun setDarkModeOption(option:Int){
        viewModelScope.launch {
            dataStoreManager.setInt(DARK_MODE_KEY,option)
        }
    }
}