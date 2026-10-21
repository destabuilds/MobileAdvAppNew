package com.example.mobileadvappnew.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileadvappnew.data.local.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getUserSession(): LiveData<Pair<String, String>> {
        return pref.getUserSession().asLiveData()
    }

    fun saveUserSession(token: String, name: String) {
        viewModelScope.launch {
            pref.saveUserSession(token, name)
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            pref.clearUserSession()
        }
    }
}